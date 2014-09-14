package org.summarly.lib.common;


import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import de.l3s.boilerpipe.sax.ImageExtractor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Anton Chernetskij
 */
public class TextReader {

    public String readText(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(path);
            Scanner scanner = new Scanner(fis, "UTF-8");
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public String readText(URL url) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8"));
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public Article readTextFromURL(String url) {
        Article article = new Article();
        String json;

        try {
            //json = ArticleExtractor.getInstance().getText(new URL(url));
            //article = parseJSON(json);

            final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(url));
            final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
            article.setTitle(doc.getTitle());
            article.setText(ArticleExtractor.INSTANCE.getText(doc));

            List<Image> images = ImageExtractor.INSTANCE.process(new URL(url), CommonExtractors.KEEP_EVERYTHING_EXTRACTOR);
            Collections.sort(images);
            String image = null;
            if (!images.isEmpty()) {
                String imgSrc = images.get(0).getSrc();
                if (!imgSrc.startsWith("http:")) {
                    imgSrc = "http:" + imgSrc;
                }
                article.setKDPVimage(new URL(imgSrc));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return article;
    }

}