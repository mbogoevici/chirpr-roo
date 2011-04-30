package org.jboss.chirpr.roo.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.jboss.chirpr.roo.domain.Chirp;
import org.jboss.chirpr.roo.domain.Profile;
import org.jboss.chirpr.roo.security.CurrentProfile;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "chirps", formBackingObject = Chirp.class)
@RequestMapping("/chirps/**")
@Controller
public class ChirpController {
	
	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, 
    		@RequestParam(value = "author", required = false) String author,
    		Model uiModel) {
        if (author != null) {
        	uiModel.addAttribute("chirps", Chirp.findChirpsByAuthor(Profile.findProfilesByUsername(author).getSingleResult()).getResultList());
        	uiModel.addAttribute("hideMessageBox", true);
        }
        else if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("chirps", Chirp.findChirpEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Chirp.countChirps() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("chirps", Chirp.findAllChirps());
        }
        addDateTimeFormatPatterns(uiModel);
        return "chirps/list";
    }

	@RequestMapping(method = RequestMethod.POST)
    public String create(@RequestParam (value = "text", required=true) String text, HttpServletRequest httpServletRequest) {
        Chirp chirp = new Chirp();
        chirp.setAuthor(CurrentProfile.get());
        chirp.setPostedOn(new Date());
        chirp.setText(text);
        chirp.persist();
        return "redirect:/chirps";
    }
}
