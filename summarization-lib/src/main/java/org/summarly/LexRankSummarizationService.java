package org.summarly;

import org.summarly.common.RankedSentence;
import org.summarly.common.Text;
import org.summarly.segmentation.StanfordNLPSplitter;
import org.summarly.segmentation.TextSplitter;
import org.summarly.summarizing.LexRankRanker;
import org.summarly.summarizing.Ranker;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Anton Chernetskij
 */
public class LexRankSummarizationService implements SummarizationService {

    private static final Logger LOGGER = Logger.getLogger(LexRankSummarizationService.class.getName());
    private Ranker ranker;
    private TextSplitter splitter;

    public LexRankSummarizationService() {
        splitter = new StanfordNLPSplitter();
        ranker = new LexRankRanker();
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

    public List<String> summarise(String s, double ratio) {
        long start = System.currentTimeMillis();
        Text text = splitter.split(s, "");
        if (text.numSentences() < 6) {
            throw new RuntimeException("The text is too small to apply extractive summary");
        }

        List<RankedSentence> summary = ranker.rank(text);
        long finish = System.currentTimeMillis();
        LOGGER.info(String.format(
                "Processed text of %d sentences in %d ms", text.numSentences(), (finish - start)));

        return null;
    }
}
