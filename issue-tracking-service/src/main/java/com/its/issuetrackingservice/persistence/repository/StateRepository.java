package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {

    @Query("SELECT s FROM State s WHERE s.project.id = :projectId AND s.isInitial")
    Optional<State> getProjectInitialState(Long projectId);

}
