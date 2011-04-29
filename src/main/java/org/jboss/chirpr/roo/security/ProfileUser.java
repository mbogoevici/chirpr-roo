package org.jboss.chirpr.roo.security;

import java.util.Collections;

import org.jboss.chirpr.roo.domain.Profile;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;


public class ProfileUser extends User{

	private Profile profile;
	
	public ProfileUser(Profile profile) {
		super(profile.getUsername(), "n/a", true, true, true,
				true, Collections.singleton(new GrantedAuthorityImpl("ROLE_USER")));
		this.profile = profile;
	}

	public Profile getProfile() {
		return profile;
	}
}