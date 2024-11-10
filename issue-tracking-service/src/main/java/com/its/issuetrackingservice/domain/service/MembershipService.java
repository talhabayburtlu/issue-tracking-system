package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Membership;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final UserContext userContext;

    @Cacheable(value = "projectsOfMember", key = "{#userId}")
    public Set<Project> getProjectsOfMember(Long userId) {
        return membershipRepository.getProjectsOfMember(userId);
    }

    @CacheEvict(value = "projectsOfMember", key = "{#membership.user.id}")
    public Membership upsertMembership(Membership membership, boolean setAuditableFields) {
        if (setAuditableFields) {
            membership.setAuditableFields(userContext);
        }

        return membershipRepository.save(membership);
    }

    @CacheEvict(value = "projectsOfMember", key = "{#membership.user.id}")
    public void deleteMembership(Membership membership) {
        membershipRepository.delete(membership);
    }

    public Optional<Membership> getMembershipByProjectAndUserId(Long userId, Long projectId) {
        return membershipRepository.getMembershipByUserIdAndProjectId(userId, projectId);
    }

}
