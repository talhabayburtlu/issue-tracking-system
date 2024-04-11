package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    Set<Project> getProjectsOfUser(Long userId);

    Project getProjectByName(String name);

    Project getProjectByKeycloakId(String keycloakId);

    Set<Project> getProjectsByNameIn(List<String> names);

}
