package org.summarly.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Text;
import org.summarly.lib.segmentation.LuceneSplitter;
import org.summarly.lib.segmentation.StanfordNLPSplitter;
import org.summarly.lib.segmentation.TextSplitter;
import org.summarly.lib.summarizing.Filter;
import org.summarly.lib.summarizing.LexRankRanker;
import org.summarly.lib.summarizing.PreFilter;
import org.summarly.lib.summarizing.modifiers.RankModifier;
import org.summarly.lib.summarizing.Ranker;
import org.summarly.lib.common.Sentence;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.tika.language.LanguageIdentifier;

/**
 * Created by Anton Chernetskij
 */
public class LexRankSummarizationService implements SummarizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LexRankSummarizationService.class);

    private Ranker ranker;
    private TextSplitter enSplitter;
    private TextSplitter ruSplitter;
    private Filter filter;
    private List<RankModifier> rankModifiers;

    public LexRankSummarizationService() {
        enSplitter = new StanfordNLPSplitter();
        ruSplitter = new LuceneSplitter();
        ranker = new LexRankRanker();
        filter = new Filter();
        rankModifiers = Arrays.<RankModifier>asList(text -> text);
    }

    public List<String> summarise(String s, double ratio) throws UnsupportedLanguageException {
        long start = System.currentTimeMillis();
        Text text;

        PreFilter preFilter = new PreFilter();
        s = preFilter.filterBrackets(s);
        text = splitText(s);

        if (text.numSentences() < 6) {
            throw new RuntimeException("The text is too small to apply extractive summary");
        }

        List<RankedSentence> rankedText = ranker.rank(text);
        rankedText = modifyRank(rankedText);

        long finish = System.currentTimeMillis();
        LOGGER.info(String.format(
                "Processed text of %d sentences in %d ms", text.numSentences(), (finish - start)));

        List<Sentence> summary = filter.filter(rankedText, ratio)
                .stream().map(RankedSentence::getSentence)
                .collect(Collectors.<Sentence>toList());

        List<String> paragraphs = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        int currentParagraph = 0;
        for (Sentence sentence : summary) {
            builder.append(sentence.getText());
            if (sentence.getParagraphNum() != currentParagraph) {
                currentParagraph = sentence.getParagraphNum();
                paragraphs.add(builder.toString());
                builder.setLength(0);
            } else {
                builder.append(" ");
            }
        }

        return paragraphs;
    }

    private Text splitText(String s) throws UnsupportedLanguageException {
        Text text;LanguageIdentifier languageIdentifier = new  LanguageIdentifier(s);
        String lang = languageIdentifier.getLanguage();

        switch (lang) {
            case "en":
                text = enSplitter.split(s, "");
                break;
            case "ru":
                text = ruSplitter.split(s, "");
                break;
            default:
                throw new UnsupportedLanguageException(lang);
        }
        return text;
    }

    private List<RankedSentence> modifyRank(List<RankedSentence> text) {
        for (RankModifier modifier : rankModifiers) {
            text = modifier.modify(text);
        }
        return text;
    }

    public Ranker getRanker() {
        return ranker;
    }

    public void setRanker(Ranker ranker) {
        this.ranker = ranker;
    }

    public TextSplitter getSplitter() {
        return enSplitter;
    }

    public void setSplitter(TextSplitter splitter) {
        this.enSplitter = splitter;
    }
}
