package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import com.its.issuetrackingservice.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserContext userContext;

	@Setter(onMethod_ = {@Autowired, @Lazy})
	private UserService selfReference;

	public User getUserByUsername(String username, boolean isRequired) {
		User user = userRepository.getUserByUsername(username);
		if (Objects.isNull(user) && isRequired) {
			throw new DataNotFoundException(I18nExceptionKeys.USER_NOT_FOUND);
		}
		return user;
	}

	public User getUserByKeycloakId(String keycloakId, boolean isRequired) {
		User user = userRepository.getUserByKeycloakId(keycloakId);
		if (Objects.isNull(user) && isRequired) {
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

	@CachePut(value = "userByKeycloakId", key = "{#user.keycloakId}")
	@CacheEvict(value = "projectsOfMember", key = "{#user.id}")
	public User upsertUser(User user, boolean isSystemUser) {
		user.setAuditableFields(!isSystemUser ? userContext: null);
		return userRepository.save(user);
	}

	public void changeActiveStateOfUser(String keycloakId, boolean isActive, boolean isSystemUser) {
		User user = selfReference.getUserByKeycloakId(keycloakId, false);
		if (Objects.nonNull(user)) {
			user.setIsActive(isActive);
			selfReference.upsertUser(user, isSystemUser);
		}
	}
}
