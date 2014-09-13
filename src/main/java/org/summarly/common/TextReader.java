package org.summarly.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

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
    
    public String readTextFromURL(String url) {
        String res = null;
        try {
            res =  ArticleExtractor.getInstance().getText(new URL(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BoilerpipeProcessingException ex) {
        	throw new RuntimeException(ex);
		}
        return res;
    }
}
