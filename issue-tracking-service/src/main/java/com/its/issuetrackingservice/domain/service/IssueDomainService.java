package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.api.model.UserContext;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.persistence.entity.*;
import com.its.issuetrackingservice.persistence.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueDomainService {
    private final UserContext userContext;
    private final ProjectDomainService projectDomainService;
    private final SprintDomainService sprintDomainService;
    private final StateDomainService stateDomainService;
    private final IssueRepository issueRepository;

    public Issue createIssue(Issue issue) {
        validateAccessToProjectOfIssue(issue);

        User creatorUser = userContext.getUser();
        issue.setAuditableFields(userContext);
        issue.setCreatorUser(creatorUser);

        Long projectId = issue.getProject().getId();
        setInitialStateByProject(issue, projectId);
        generateAndSetIssueAbbreviationAndNumber(issue, projectId);

        return issueRepository.save(issue);
    }

    public Issue patchIssue(Issue existingIssue, Issue patchedIssue) {
        userContext.applyAccessToProject(patchedIssue.getProject());
        validateAuthenticatedUserIsIssueCreator(patchedIssue);

        if (!Objects.equals(existingIssue.getState().getId(), patchedIssue.getState().getId())) {
            stateDomainService.checkStateTransitionAllowed(existingIssue.getState(), patchedIssue.getState());
        }

        patchedIssue.setAuditableFields(userContext);

        return issueRepository.save(patchedIssue);
    }

    public Issue getIssueDetail(Long issueId) {
        Issue issue = getIssueById(issueId);

        Project project = projectDomainService.getProjectById(issue.getProject().getId());
        userContext.applyAccessToProject(project);

        return issue;
    }

    public List<Issue> getSprintIssues(Long sprintId) {
        Sprint sprint = sprintDomainService.getSprintById(sprintId);
        Project project = sprint.getProject();
        userContext.applyAccessToProject(project);

        return issueRepository.getAllBySprintId(sprintId);
    }

    public void validateAuthenticatedUserIsIssueCreator(Issue issue) {
        User user = userContext.getUser();

        if (!Objects.equals(issue.getCreatorUser().getId(), user.getId())) {
            throw new WrongUsageException(I18nExceptionKeys.ISSUE_CREATOR_MUST_MATCH_AUTH_USER);
        }
    }

    public Issue getIssueById(Long issueId) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ISSUE_NOT_FOUND, String.format("issue id=%d", issueId));
        }

        return issue.get();
    }

    public void validateAccessToProjectOfIssue(Issue issue) {
        userContext.applyAccessToProject(issue.getProject().getId());
    }

    private void generateAndSetIssueAbbreviationAndNumber(Issue issue, Long projectId) {
        Long latestNumber = issueRepository.getLatestIssueNumberInProject(projectId);
        Long newNumber = latestNumber + 1;

        issue.setNumber(newNumber);
        issue.setAbbreviation(String.format("%s-%d" , issue.getProject().getAbbreviation(), newNumber));
    }

    private void setInitialStateByProject(Issue issue, Long projectId) {
        State initialState = stateDomainService.getProjectInitialState(projectId);
        issue.setState(initialState);
    }


}
