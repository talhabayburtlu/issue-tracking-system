package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import com.its.issuetrackingservice.infrastructure.persistence.entity.State;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Issue createIssue(IssueRequest issueRequest, Long projectId) {
        Issue issue = issueMapper.toEntity(issueRequest);
        issue.setSpentTime(0L);

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

    public Issue updateIssue(IssueRequest issueRequest, Long issueId) {
        Issue issue = getIssueById(issueId);
        Issue newIssue = issueMapper.patchEntity(issueRequest, issue);

        // Configure participants
        Set<Long> participationIdsToRemove = participantService.getParticipantIdsToRemove(issueRequest);
        participantService.deleteParticipants(participationIdsToRemove);

        Set<Participation> participationSet = participantService.buildParticipants(issueRequest, newIssue, Boolean.TRUE);
        issue.setParticipants(participationSet);

        // Check state transition
        stateService.checkStateTransitionAllowed(issue.getState(), newIssue.getState());

        return upsertIssue(issue);
    }

    public Issue upsertIssue(Issue issue) {
        // Set auditable fields.
        issue.setAuditableFields(userContext);

        return issueRepository.save(issue);
    }

    public List<Issue> getSprintIssues(Long projectId, Long sprintId) {
        sprintService.checkSprintExists(sprintId);
        projectService.checkProjectExists(projectId);

        return issueRepository.getAllByProjectIdAndSprintIdOrderByCreatedDateAsc(projectId, sprintId);
    }

    public List<Issue> getBacklogIssues(Long projectId) {
        projectService.checkProjectExists(projectId);

        return issueRepository.getAllByProjectIdAndSprintIsNullOrderByCreatedDateAsc(projectId);
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

    public void validateAccessToProject(Long projectId) {
        userContext.applyAccessToProject(projectId);
    }

    private String generateIssueCode(Issue issue, Long projectId) {
        Long latestNumber = issueRepository.getLatestIssueNumberInProject(projectId);
        Long newNumber = latestNumber + 1;

        return String.format("%s-%d", issue.getProject().getAbbreviation(), newNumber);
    }

}
