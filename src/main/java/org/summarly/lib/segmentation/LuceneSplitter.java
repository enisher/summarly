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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            List<String> words = new ArrayList<>();
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

        final List<Sentence> sentences = splitTextOnSentences(text).stream()
                .filter(sentence -> Pattern.compile("\\w").matcher(sentence).find())
                .map(sentence -> {
                    Sentence aSentence = new Sentence(sentence);
                    aSentence.setWords(extractNormalizedTokens(sentence, new RussianAnalyzer(Version.LUCENE_4_9)));
                    return aSentence;
                }).collect(Collectors.toList());

        Text theText = new Text(text);
        theText.setSentences(sentences);

        return theText;
    }
}
