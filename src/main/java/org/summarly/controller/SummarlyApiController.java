package org.summarly.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.summarly.lib.common.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.summarly.lib.SummarizationService;

import java.util.List;

@RestController
@RequestMapping(value = "/summarly-api/summarize")
public class SummarlyApiController {

    private static final Logger log = LoggerFactory.getLogger(SummarlyApiController.class);

    @Autowired
    private SummarizationService summarizationService;

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public String summarizeText(@RequestParam(required = true) String text) {
        log.info("Received article text: " + text);

        return summarize(text);
    }

    @RequestMapping(value = "/url", method = RequestMethod.POST)
    public String summarizeURL(@RequestParam(required = true) String url) {
        log.info("Received article url: " + url);

        TextReader reader = new TextReader();
        String articleText = reader.readTextFromURL(url);

        return summarize(articleText);
    }

    private String summarize(String originalText) {
        List<String> summary = summarizationService.summarise(originalText, 0.5);
        StringBuilder builder = new StringBuilder();
        for (String s : summary) {
            builder.append(s).append(" ");
        }
        return builder.toString();
    }
}
