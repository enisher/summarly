package org.summarly.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.summarly.common.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.summarly.lib.SummarizationService;
import org.summarly.model.ArticleModel;

@RestController
@RequestMapping(value = "summarly-api")
public class SummarlyApiController {

    private static final Logger log = LoggerFactory.getLogger(SummarlyApiController.class);

    @Autowired
    private SummarizationService summarizationService;

    @RequestMapping(value = "/summarizeText", method = RequestMethod.POST)
    public String summarizeText(@RequestParam String articleText) {
        log.info("Received article text: " + articleText);
        return summarize(articleText);
    }
    
    @RequestMapping(value = "/summarizeURL", method = RequestMethod.POST)
    public String summarizeURL(@RequestParam String articleURL) {
        log.info("Received article URL: " + articleURL);
        TextReader reader = new TextReader();
		String articleText = reader.readTextFromURL(articleURL);
        return summarize(articleText);
    }
    
    private String summarize(String originalText){
    	
    	// TODO: Summarize received text
    	
    	return originalText;
    }
}
