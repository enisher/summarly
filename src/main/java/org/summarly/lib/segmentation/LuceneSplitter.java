package org.summarly.lib.segmentation;

import com.ibm.icu.text.BreakIterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.summarly.lib.common.Sentence;
import org.summarly.lib.common.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 */
public class LuceneSplitter implements TextSplitter {

    public List<String> splitTextOnSentences(String text) {
        ArrayList<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
             sentences.add(text.substring(start, end));
        }
        return sentences;
    }

    public List<String> splitSentence(String text) throws IOException {
        final Analyzer analyzer;

        analyzer = new RussianAnalyzer(Version.LUCENE_4_9);

        return extractNormalizedTokens(text, analyzer);

    }

    private List<String> extractNormalizedTokens(String text, Analyzer analyzer) {
        try (TokenStream tokenStream = analyzer.tokenStream(null, text)) {
            tokenStream.reset();
            ArrayList<String> words = new ArrayList<>();
            while (tokenStream.incrementToken()) {
                words.add(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Text split(String text, String name) {
        String sentence = text; //TODO separate sentences

        ArrayList<Sentence> sentences = new ArrayList<>();
        Sentence aSentence = new Sentence(sentence);
        aSentence.setWords(extractNormalizedTokens(text, new RussianAnalyzer(Version.LUCENE_4_9)));
        sentences.add(aSentence);

        Text theText = new Text(text);
        theText.setSentences(sentences);

        return theText;
    }
}
