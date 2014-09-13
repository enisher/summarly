package org.summarly.lib.common;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
