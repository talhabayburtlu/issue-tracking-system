package com.its.issuetrackingservice.domain.common.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalAuthenticationService {

	/*public AuthenticatedUser getAuthenticatedUser() {
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
	}*/
}
