package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.api.model.UserContext;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.persistence.entity.User;
import com.its.issuetrackingservice.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDomainService {
	private final UserRepository userRepository;
	private final UserContext userContext;

	public User getUserByUsername(String username, boolean isRequired) {
		User user = userRepository.getUserByUsername(username);
		if (Objects.isNull(user) && isRequired) {
			throw new DataNotFoundException(I18nExceptionKeys.USER_NOT_FOUND);
		}
		return user;
	}

	public User getUserByKeycloakId(String keycloakId) {
		User user = userRepository.getUserByKeycloakId(keycloakId);
		if (Objects.isNull(user)) {
			throw new DataNotFoundException(I18nExceptionKeys.USER_NOT_FOUND);
		}
		return user;
	}

	public User getUserById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new DataNotFoundException(I18nExceptionKeys.USER_NOT_FOUND, String.format("user id=%d", userId));
		}

		return user.get();
	}

	public User upsertUser(User user, boolean isSystemUser) {
		user.setAuditableFields(!isSystemUser ? userContext: null);
		return userRepository.save(user);
	}

	public void changeActiveStateOfUser(String username, boolean isActive) {
		User user = getUserByKeycloakId(username);
		user.setIsActive(isActive);

		userRepository.save(user);
	}
}
