package org.summarly.lib.segmentation;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 * Created by enisher on 9/13/14.
 */
public class LuceneSplitter {

    public void split(String text) throws IOException {
        RussianAnalyzer analyzer = new RussianAnalyzer(Version.LUCENE_4_9);

        try (TokenStream tokenStream = analyzer.tokenStream(null, text)) {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                System.out.println(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
        }

    }
}
