package com.its.issuetrackingservice.persistence.user.repository;

import com.its.issuetrackingservice.persistence.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User getUserByUsername(String username);
}
