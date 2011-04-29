package org.jboss.chirpr.roo.web;

import org.jboss.chirpr.roo.domain.Chirp;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "chirps", formBackingObject = Chirp.class)
@RequestMapping("/chirps")
@Controller
public class ChirpController {
}
