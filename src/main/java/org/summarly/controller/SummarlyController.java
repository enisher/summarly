package org.summarly.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.summarly.ArticleModel;

@RestController
public class SummarlyController {

    Logger log = LoggerFactory.getLogger(SummarlyController.class);



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Use /summarize url with articleText parameter to summarize";
    }

    @RequestMapping(value = "/summarize", method = RequestMethod.POST)
    public String summarize(ArticleModel articleModel) {
        log.info("Received article text: " + articleModel.getArticleText());

        // TODO: Summarize received text

        return articleModel.getArticleText();
    }
}
