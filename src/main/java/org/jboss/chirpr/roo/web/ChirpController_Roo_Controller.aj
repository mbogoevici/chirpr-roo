// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.jboss.chirpr.roo.web;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.jboss.chirpr.roo.domain.Chirp;
import org.jboss.chirpr.roo.domain.Profile;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ChirpController_Roo_Controller {
    
        
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ChirpController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("chirp", Chirp.findChirp(id));
        uiModel.addAttribute("itemId", id);
        return "chirps/show";
    }
    
      
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ChirpController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("chirp", Chirp.findChirp(id));
        addDateTimeFormatPatterns(uiModel);
        return "chirps/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ChirpController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Chirp.findChirp(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/chirps";
    }
    
    @ModelAttribute("chirps")
    public Collection<Chirp> ChirpController.populateChirps() {
        return Chirp.findAllChirps();
    }
    
    @ModelAttribute("profiles")
    public java.util.Collection<Profile> ChirpController.populateProfiles() {
        return Profile.findAllProfiles();
    }
    
    void ChirpController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("chirp_postedon_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    String ChirpController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
