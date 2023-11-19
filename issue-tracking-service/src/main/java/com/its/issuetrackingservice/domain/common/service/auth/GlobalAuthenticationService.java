package com.its.issuetrackingservice.domain.common.service.auth;

import com.its.issuetrackingservice.persistence.common.entity.AuthenticatedUser;
import com.its.issuetrackingservice.persistence.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GlobalAuthenticationService {

	public AuthenticatedUser getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}

		if (! (authentication.getPrincipal() instanceof AuthenticatedUser)) {
			return null;
		}

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
		return authenticatedUser;
	}

	public AuthenticatedUser createUserAuthentication(User user) {

		AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
				.username(user.getUsername())
				.build();

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticatedUser,
				null,
				Collections.emptyList()
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authenticatedUser;
	}
}
