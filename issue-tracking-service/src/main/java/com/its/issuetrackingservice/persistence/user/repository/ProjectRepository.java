package com.its.issuetrackingservice.persistence.user.repository;

import com.its.issuetrackingservice.persistence.user.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    Set<Project> getProjectsOfUser(Long userId);

}
