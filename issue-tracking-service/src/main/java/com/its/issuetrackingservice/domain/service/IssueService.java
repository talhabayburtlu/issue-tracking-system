package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import com.its.issuetrackingservice.infrastructure.persistence.entity.State;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {
    private final UserContext userContext;
    private final ProjectService projectService;
    private final ParticipantService participantService;
    private final SprintService sprintService;
    private final StateService stateService;
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    public Issue createDraftIssue(IssueRequest issueRequest, Long projectId) {
        Issue issue = issueMapper.toEntity(issueRequest);
        issue.setSpentTime(0L);
        issue.setProject(projectService.getProjectById(projectId));
        issue.setIsDraft(Boolean.TRUE);

        // Configure participants
        Set<Participation> participationSet = participantService.buildParticipants(issueRequest, issue, Boolean.FALSE);
        issue.setParticipants(participationSet);

        // Configure initial state
        State initialState = stateService.getProjectInitialState(projectId);
        issue.setState(initialState);

        // Configure issue code
        String issueCode = generateIssueCode(issue, projectId);
        issue.setCode(issueCode);

        return upsertIssue(issue);
    }

    public Issue updateIssue(IssueRequest issueRequest, Issue oldIssue) {
        Issue newIssue = issueMapper.cloneEntity(oldIssue);
        issueMapper.patchEntity(issueRequest, newIssue);

        // Configure participants
        Set<Participation> issueParticipants = newIssue.getParticipants();
        Set<Long> participantsToRemove = participantService.getParticipantIdsToRemove(issueRequest);
        issueParticipants = issueParticipants.stream().filter(participation -> !participantsToRemove.contains(participation.getId())).collect(Collectors.toSet());
        issueParticipants.addAll(participantService.buildParticipants(issueRequest, newIssue, Boolean.TRUE));
        newIssue.setParticipants(issueParticipants);

        // Check state transition
        if (!Objects.equals(oldIssue.getState(), newIssue.getState())) {
            stateService.checkStateTransitionAllowed(oldIssue.getState(), newIssue.getState());
        }

        newIssue = upsertIssue(newIssue);
        participantService.removeAllParticipants(participantsToRemove);
        return newIssue;
    }

    public Issue upsertIssue(Issue issue) {
        // Set auditable fields.
        issue.setAuditableFields(userContext);
        issueRepository.flush();
        return issueRepository.save(issue);
    }

    public void publishDraftIssue(Issue issue) {
        if (Objects.isNull(issue)) {
            throw new DataNotFoundException(I18nExceptionKeys.ISSUE_NOT_FOUND);
        }

        if (Boolean.FALSE.equals(issue.getIsDraft())) {
            throw new WrongUsageException(I18nExceptionKeys.ALREADY_PUBLISHED_ISSUE_CAN_NOT_PUBLISHED);
        }

        issue.setIsDraft(Boolean.FALSE);
        upsertIssue(issue);
    }

    public List<Issue> getSprintIssues(Long projectId, Long sprintId) {
        sprintService.checkSprintExists(sprintId);
        projectService.checkProjectExists(projectId);

        return issueRepository.getAllByProjectIdAndSprintIdOrderByCreatedDateAsc(projectId, sprintId);
    }

    public Page<Issue> getBacklogIssues(Long projectId, Pageable pageable) {
        projectService.checkProjectExists(projectId);

        return issueRepository.getAllByProjectIdAndSprintIsNullOrderByCreatedDateAsc(projectId, pageable);
    }

    public Issue getIssueById(Long issueId) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ISSUE_NOT_FOUND, String.format("issue id=%d", issueId));
        }

        Issue issue = optionalIssue.get();
        userContext.applyAccessToProject(issue.getProject().getId());
        return issue;
    }

    private String generateIssueCode(Issue issue, Long projectId) {
        Long latestNumber = issueRepository.getLatestIssueNumberInProject(projectId);
        Long newNumber = latestNumber + 1;

        return String.format("%s-%d", issue.getProject().getAbbreviation(), newNumber);
    }

}
