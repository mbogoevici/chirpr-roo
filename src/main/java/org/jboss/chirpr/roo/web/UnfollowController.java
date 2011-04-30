package org.jboss.chirpr.roo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.chirpr.roo.domain.Profile;
import org.jboss.chirpr.roo.security.ProfileUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/unfollow")
@Controller
public class UnfollowController {

	@RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam(value = "id", required = true) String id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	OpenIDAuthenticationToken token = (OpenIDAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		ProfileUser user = (ProfileUser) token.getPrincipal();
    	user.getProfile().unfollow(Profile.findProfilesByUsername(id).getSingleResult());
    	Profile mergedProfile = user.getProfile().merge();
    	OpenIDAuthenticationToken newToken = new OpenIDAuthenticationToken(new ProfileUser(mergedProfile), token.getAuthorities(), token.getIdentityUrl(), token.getAttributes());
    	SecurityContextHolder.getContext().setAuthentication(newToken);
    	return "redirect:/profiles";
    }
}
