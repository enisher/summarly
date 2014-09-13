package org.summarly.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.summarly.lib.common.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.summarly.lib.SummarizationService;

import java.util.List;

@Controller
public class SummarlyApiController {

    private static final Logger log = LoggerFactory.getLogger(SummarlyApiController.class);

    @Autowired
    private SummarizationService summarizationService;

    @RequestMapping(value = "/summary", method = RequestMethod.POST)
    public ModelAndView summarizeText(@RequestParam(required = true) String text) {
        log.info("Received article text: " + text);

        ModelAndView modelAndView = new ModelAndView("summary");
        modelAndView.addObject("body", summarize(text));

        return modelAndView;
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ModelAndView summarizeURL(@RequestParam(required = true) String url) {
        log.info("Received article url: " + url);

        TextReader reader = new TextReader();
        String articleText = reader.readTextFromURL(url);

        ModelAndView modelAndView = new ModelAndView("summary");
        modelAndView.addObject("body", summarize(articleText));

        return modelAndView;
    }

    private String summarize(String originalText) {
        List<String> summary = summarizationService.summarise(originalText, 0.5);
        StringBuilder builder = new StringBuilder();
        summary.stream().forEach(s -> builder.append(s).append(" "));

        return builder.toString();
    }
}
