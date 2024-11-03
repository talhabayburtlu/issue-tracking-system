package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {

    @Query("SELECT s FROM State s WHERE s.project.id = :projectId AND s.isInitial")
    Optional<State> getProjectInitialState(Long projectId);

    boolean existsByNextStateIdIn(List<Long> nextStateIds);

}
