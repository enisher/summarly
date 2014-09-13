package org.summarly;

import org.junit.Test;
import org.summarly.lib.LexRankSummarizationService;
import org.summarly.lib.common.TextReader;
import org.summarly.lib.summarizing.PreFilter;

import static java.lang.System.out;

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
        summary.forEach(out::println);
    }

    @Test
    public void testProcessForRussianText() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Russian.txt");

        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s, 0.5);
        summary.forEach(out::println);
    }

    @Test
    public void testProcessForRussianTextWithBrackets() throws Exception {

        TextReader reader = new TextReader();
        String source = reader.readText("src/test/resources/RussianWithBrackets.txt");
        PreFilter preFilter = new PreFilter();
        source = preFilter.filterBrackets(source);
        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(source, 0.5);
        summary.forEach(out::println);
    }
}
