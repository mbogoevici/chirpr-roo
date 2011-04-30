package org.jboss.chirpr.roo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.chirpr.roo.domain.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Utility class for auto-registering a profile after
 * a successful OpenID login
 *  
 * @author Marius Bogoevici
 *
 */
public class AutoRegisterAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

	@Autowired PlatformTransactionManager transactionManager;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if (exception instanceof UsernameNotFoundException) {
			Profile profile = new Profile();
			OpenIDAuthenticationToken openIDAuthenticationToken = (OpenIDAuthenticationToken) exception.getAuthentication();
			profile.setIdentityUrl(openIDAuthenticationToken.getIdentityUrl());
			for (OpenIDAttribute attribute: openIDAuthenticationToken.getAttributes()) {
				if ("email".equals(attribute.getName()) && attribute.getValues() != null && !attribute.getValues().isEmpty()) {
					profile.setUsername(attribute.getValues().get(0));
				}
			}
			profile.persist();
			openIDAuthenticationToken.setDetails(new ProfileUser(profile));
			openIDAuthenticationToken.setAuthenticated(true);			
		    SecurityContextHolder.getContext().setAuthentication(openIDAuthenticationToken);
			getRedirectStrategy().sendRedirect(request, response, ((DefaultSavedRequest)request.getSession().getAttribute(WebAttributes.SAVED_REQUEST)).getRedirectUrl());
		}
		else
		super.onAuthenticationFailure(request, response, exception);
	}

}