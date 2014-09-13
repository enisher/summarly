package org.summarly.lib;

import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Text;
import org.summarly.lib.segmentation.StanfordNLPSplitter;
import org.summarly.lib.segmentation.TextSplitter;
import org.summarly.lib.summarizing.Filter;
import org.summarly.lib.summarizing.LexRankRanker;
import org.summarly.lib.summarizing.RankModifier;
import org.summarly.lib.summarizing.Ranker;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Anton Chernetskij
 */
public class LexRankSummarizationService implements SummarizationService {

    private static final Logger LOGGER = Logger.getLogger(LexRankSummarizationService.class.getName());
    private Ranker ranker;
    private TextSplitter splitter;
    private Filter filter;
    private List<RankModifier> rankModifiers;

    public LexRankSummarizationService() {
        splitter = new StanfordNLPSplitter();
        ranker = new LexRankRanker();
        filter = new Filter();
        rankModifiers = Arrays.<RankModifier>asList(text -> text);
    }

    public List<String> summarise(String s, double ratio) {
        long start = System.currentTimeMillis();
        Text text = splitter.split(s, "");
        if (text.numSentences() < 6) {
            throw new RuntimeException("The text is too small to apply extractive summary");
        }

        List<RankedSentence> rankedText = ranker.rank(text);
        rankedText = modifyRank(rankedText);

        long finish = System.currentTimeMillis();
        LOGGER.info(String.format(
                "Processed text of %d sentences in %d ms", text.numSentences(), (finish - start)));

        return filter.filter(rankedText, ratio)
                .stream().map(p -> p.getSentence().getSentence())
                .collect(Collectors.<String>toList());
    }

    private List<RankedSentence> modifyRank(List<RankedSentence> text){
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
        return splitter;
    }

    public void setSplitter(TextSplitter splitter) {
        this.splitter = splitter;
    }
}
