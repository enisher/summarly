package org.summarly.common;

import java.io.IOException;
import java.net.URL;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;


public class TextReader {

    public String readTextFromURL(String url) {
        String res;

        try {
            res = ArticleExtractor.getInstance().getText(new URL(url));
        } catch (IOException | BoilerpipeProcessingException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
