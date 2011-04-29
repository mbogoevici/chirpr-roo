package org.jboss.chirpr.roo.security;

import org.jboss.chirpr.roo.domain.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AutoRegisterUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
	
		Profile profile = null;
		try {
			profile = Profile.findProfilesByIdentityUrl(username).getSingleResult();
		} catch (DataAccessException e) {
			// ignore DAE, just assume that the profile hasn't been found.
		}
		if (profile == null)
		{
			throw new UsernameNotFoundException(username);
		}
		return new ProfileUser(profile);
	}
	
}