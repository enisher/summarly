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
import org.summarly.lib.UnsupportedLanguageException;
import org.summarly.lib.common.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.summarly.lib.SummarizationService;

import java.util.Arrays;
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
        modelAndView.addObject("paragraphs", summarize(text));

        return modelAndView;
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ModelAndView summarizeURL(@RequestParam(required = true) String url) {
        log.info("Received article url: " + url);

        TextReader reader = new TextReader();
        String articleText = reader.readTextFromURL(url);

        ModelAndView modelAndView = new ModelAndView("summary");
        modelAndView.addObject("paragraphs", summarize(articleText));

        return modelAndView;
    }

    private List<String> summarize(String originalText) {

        try {
            return summarizationService.summarise(originalText, 0.5);
        } catch (UnsupportedLanguageException e) {
            return Arrays.asList("Sorry article language [" + e.getLanguage() + "] is not supported yet. :(");
        }
    }
}
