package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User getUserByUsername(String username);
	User getUserByKeycloakId(String keycloakId);
}
