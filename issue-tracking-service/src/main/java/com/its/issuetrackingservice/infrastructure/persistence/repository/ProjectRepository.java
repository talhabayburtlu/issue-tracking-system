package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project getProjectByName(String name);

    Project getProjectByKeycloakId(String keycloakId);

    Set<Project> getProjectsByNameIn(List<String> names);

}
