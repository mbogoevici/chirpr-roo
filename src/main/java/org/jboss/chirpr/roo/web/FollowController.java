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

@RequestMapping("/follow")
@Controller
public class FollowController {

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam(value = "id", required = true) String id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	OpenIDAuthenticationToken token = (OpenIDAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
    	Object principal = token.getPrincipal();
		ProfileUser user = (ProfileUser) principal;
    	user.getProfile().follow(Profile.findProfilesByUsername(id).getSingleResult());
    	Profile mergedProfile = user.getProfile().merge();
    	OpenIDAuthenticationToken newToken = new OpenIDAuthenticationToken(new ProfileUser(mergedProfile), token.getAuthorities(), token.getIdentityUrl(), token.getAttributes());
    	SecurityContextHolder.getContext().setAuthentication(newToken);
    	return "redirect:/profiles";
    }
}
