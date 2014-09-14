package org.summarly;

import org.junit.Assert;
import org.junit.Test;
import org.summarly.lib.LexRankSummarizationService;
import org.summarly.lib.common.Sentence;
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
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessForRussianText() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Russian.txt");

        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s, 0.5);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessForRussianTextWithBrackets() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/RussianWithBrackets.txt");
        PreFilter preFilter = new PreFilter();
        s = preFilter.filterBrackets(s);
        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s, 0.5);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessHabrArticle() throws Exception {
        TextReader reader = new TextReader();
        String s = reader.readTextFromURL("http://habrahabr.ru/post/236673/").getText();
        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s, 0.5);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }


    @Test
    public void testProcessAnotherHabrArticle() throws Exception {
        TextReader reader = new TextReader();
        String s = reader.readTextFromURL("http://www.stopfake.org/kanal-rossiya-24-snyal-eksklyuzivnyj-miting-kotoryj-sam-zhe-i-srezhissiroval/").getText();

        LexRankSummarizationService summarizer = new LexRankSummarizationService();

        List<String> summary = summarizer.summarise(s, 0.5);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessCensorArticle() throws Exception {
        TextReader reader = new TextReader();
        String source = reader.readTextFromURL("http://habrahabr.ru/post/234551/").getText();
        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(source, 0.5);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }
}
