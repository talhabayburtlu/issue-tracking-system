package com.its.issuetrackingservice.domain.user.service;

import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.common.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.common.exception.I18nException;
import com.its.issuetrackingservice.persistence.user.entity.User;
import com.its.issuetrackingservice.persistence.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDomainService {
	private final UserRepository userRepository;

	public User getUserByUsername(String username) {
		User user = userRepository.getUserByUsername(username);
		if (Objects.isNull(user)) {
			throw new DataNotFoundException(I18nExceptionKeys.USER_NOT_FOUND);
		}
		return user;
	}
}
