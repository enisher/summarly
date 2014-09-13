package org.summarly;

import org.junit.Test;
import org.summarly.lib.LexRankSummarizationService;
import org.summarly.lib.common.TextReader;

import java.util.List;

/**
 * Created by Anton Chernetskij
 */
public class LexRankSummarizationServiceTest {
    @Test
    public void testProcess() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Hoverberget.txt");

        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s, 0.5);
        for (String sentence : summary) {
            System.out.println(sentence);
        }
    }
}