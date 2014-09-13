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
public class ApplicationPipeline {

    private static final Logger LOGGER = Logger.getLogger(ApplicationPipeline.class.getName());
    private Ranker ranker;
    private TextSplitter splitter;

    public ApplicationPipeline() {
        splitter = new StanfordNLPSplitter();
        ranker = new LexRankRanker();
    }

    public void process(String s, String name) {
        LOGGER.info("Starting \"" + name + "\" processing.");
        long start = System.currentTimeMillis();
        Text text = splitter.split(s, name);
        if (text.numSentences() < 6) {
            throw new RuntimeException("The text is too small to apply extractive summary");
        }

        List<RankedSentence> summary = ranker.rank(text);
        long finish = System.currentTimeMillis();
        LOGGER.info(String.format(
                "Processed text %s of %d sentences in %d ms", name, text.numSentences(), (finish - start)));
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
