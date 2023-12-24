package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
