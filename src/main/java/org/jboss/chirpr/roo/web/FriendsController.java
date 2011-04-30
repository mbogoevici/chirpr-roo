package org.jboss.chirpr.roo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.chirpr.roo.domain.Profile;
import org.jboss.chirpr.roo.security.CurrentProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FriendsController {

    @RequestMapping(value="/friends", method = RequestMethod.GET)
    public String friends(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	Profile profile = CurrentProfile.get();
    	modelMap.addAttribute("profiles", profile.getFriends());
    	return "profiles/list";
    }
    
    @RequestMapping(value="/followers", method = RequestMethod.GET)
    public String followers(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	Profile profile = CurrentProfile.get();
    	modelMap.addAttribute("profiles", profile.getFollowers());
    	return "profiles/list";
    }
}
