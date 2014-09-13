package org.summarly;

import org.junit.Test;
import org.summarly.common.TextReader;

/**
 * Created by Anton Chernetskij
 */
public class LexRankSummarizationServiceTest {
    @Test
    public void testProcess() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Hoverberget.txt");

        LexRankSummarizationService pipeline = new LexRankSummarizationService();
        pipeline.summarise(s, 0.5);
    }
}
