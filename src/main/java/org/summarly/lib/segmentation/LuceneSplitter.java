package org.summarly.lib.segmentation;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.summarly.lib.common.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class LuceneSplitter implements TextSplitter {

    public List<String> splitSentence(String text) throws IOException {
        final Analyzer analyzer;

        analyzer = new RussianAnalyzer(Version.LUCENE_4_9);

        return extractNormalizedTokens(text, analyzer);

    }

    private List<String> extractNormalizedTokens(String text, Analyzer analyzer) throws IOException {
        try (TokenStream tokenStream = analyzer.tokenStream(null, text)) {
            tokenStream.reset();
            ArrayList<String> words = new ArrayList<>();
            while (tokenStream.incrementToken()) {
                words.add(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
            return words;
        }
    }

    @Override
    public Text split(String text, String name) {

        return null;
    }
}
