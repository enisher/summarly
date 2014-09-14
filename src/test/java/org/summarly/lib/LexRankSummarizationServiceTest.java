package org.summarly.lib;

import org.junit.Assert;
import org.junit.Test;
import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.TextReader;
import org.summarly.lib.summarizing.PreFilter;

import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by Anton Chernetskij
 */
public class LexRankSummarizationServiceTest {
    @Test
    public void testProcess() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Hoverberget.txt");

        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessForRussianText() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Russian.txt");

        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s);
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
        List<String> summary = summarizer.summarise(s);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessHabrArticle() throws Exception {
        TextReader reader = new TextReader();
        String s = reader.readTextFromURL("http://habrahabr.ru/post/236673/").getText();
        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(s);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }


    @Test
    public void testProcessAnotherHabrArticle() throws Exception {
        TextReader reader = new TextReader();
        String s = reader.readTextFromURL("http://www.stopfake.org/kanal-rossiya-24-snyal-eksklyuzivnyj-miting-kotoryj-sam-zhe-i-srezhissiroval/").getText();

        LexRankSummarizationService summarizer = new LexRankSummarizationService();

        List<String> summary = summarizer.summarise(s);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testProcessCensorArticle() throws Exception {
        TextReader reader = new TextReader();
        String source = reader.readTextFromURL("http://youarenotsosmart.ru/2013/10/normalcy-bias/").getText();
        LexRankSummarizationService summarizer = new LexRankSummarizationService();
        List<String> summary = summarizer.summarise(source);
        Assert.assertFalse(summary.isEmpty());
        summary.forEach(out::println);
    }

    @Test
    public void testGetRatio() throws Exception {
        LexRankSummarizationService service = new LexRankSummarizationService();
        double ratio = service.getRatio(Collections.nCopies(5, new RankedSentence(0, null)));
        Assert.assertEquals(1, ratio, 0.001);

        ratio = service.getRatio(Collections.nCopies(200, new RankedSentence(0, null)));
        Assert.assertEquals(0.5, ratio, 0.05);
    }
}
