/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * To redirect homepage to SwaggerUI.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * Service endpoint to display UI.
     * 
     * @return home page link
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String getHomePage() {
        return "redirect:swagger-ui.html";

    }
}
