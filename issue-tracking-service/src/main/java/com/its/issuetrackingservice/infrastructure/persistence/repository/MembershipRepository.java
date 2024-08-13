package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Membership;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("SELECT m.project FROM Membership m WHERE m.user.id = :userId")
    Set<Project> getProjectsOfMember(Long userId);

}
