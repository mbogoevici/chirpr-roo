package org.jboss.chirpr.roo.security;

import org.jboss.chirpr.roo.domain.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationToken;

public class CurrentProfile {

	public static Profile get() {
		OpenIDAuthenticationToken token = (OpenIDAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		if (token == null)
			return null;
    	Object principal = token.getPrincipal();
    	if (principal == null)
    		return null;
		ProfileUser user = (ProfileUser) principal;
		return user.getProfile();
	}
	
}
