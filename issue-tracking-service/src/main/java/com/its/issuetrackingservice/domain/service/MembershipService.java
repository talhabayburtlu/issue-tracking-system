package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Membership;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final UserContext userContext;

    // TODO: Cache this service
    public Set<Project> getProjectsOfMember(Long userId) {
        return membershipRepository.getProjectsOfMember(userId);
    }

    public Membership upsertMembership(Membership membership) {
        membership.setAuditableFields(userContext);
        return membershipRepository.save(membership);
    }

}
