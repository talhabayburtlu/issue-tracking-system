package com.its.issuetrackingservice.domain.issue.service;

import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.common.dto.UserContext;
import com.its.issuetrackingservice.domain.common.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.common.exception.WrongUsageException;
import com.its.issuetrackingservice.domain.user.service.UserDomainService;
import com.its.issuetrackingservice.persistence.issue.entity.Issue;
import com.its.issuetrackingservice.persistence.issue.repository.IssueRepository;
import com.its.issuetrackingservice.persistence.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueDomainService {
    private final UserContext userContext;
    private final UserDomainService userDomainService;
    private final IssueRepository issueRepository;

    public Issue createIssue(Issue issue) {
        User creatorUser = userContext.getUser();

        issue.setCreatorUser(creatorUser);

        return issueRepository.save(issue);
    }

    public void deleteIssue(Long issueId) {
        Issue issue = getIssueById(issueId);

        validateAuthenticatedUserIsIssueCreator(issue);

        issueRepository.delete(issue);
    }

    public Issue getIssueById(Long issueId) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ISSUE_NOT_FOUND, String.format("issue id=%d", issueId));
        }

        return issue.get();
    }

    public void validateAuthenticatedUserIsIssueCreator(Issue issue) {
        User user = userDomainService.getUserByUsername(null);

        if (!Objects.equals(issue.getCreatorUser().getId(), user.getId())) {
            throw new WrongUsageException(I18nExceptionKeys.ISSUE_CREATOR_MUST_MATCH_AUTH_USER);
        }
    }
}
