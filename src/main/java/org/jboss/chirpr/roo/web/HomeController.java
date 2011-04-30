package org.jboss.chirpr.roo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.chirpr.roo.domain.Chirp;
import org.jboss.chirpr.roo.domain.Profile;
import org.jboss.chirpr.roo.security.CurrentProfile;
import org.jboss.chirpr.roo.security.ProfileUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/home")
@Controller
public class HomeController {

    @RequestMapping(method=RequestMethod.GET)
    public String get(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	Profile profile = CurrentProfile.get();

    	modelMap.addAttribute("chirps", Chirp.findChirpsByAuthorInList(profile.getFriends()).getResultList());
    	return "chirps/list";
    }

    
}
