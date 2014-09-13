package org.summarly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SummarlyController {

    @RequestMapping({"/index", "/", ""})
    public String test() {
        return "index";
    }
}
