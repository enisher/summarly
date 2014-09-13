package org.summarly.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.summarly.common.TextReader;

@RestController
public class SummarlyController {

    Logger log = LoggerFactory.getLogger(SummarlyController.class);



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Use /summarize url with articleText parameter to summarize";
    }

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
