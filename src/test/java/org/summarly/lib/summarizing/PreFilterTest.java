package org.summarly.lib.summarizing;

import org.junit.Assert;
import org.junit.Test;
import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Sentence;
import org.summarly.lib.common.TextReader;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by vaz on 9/13/2014.
 */
public class PreFilterTest {
    @Test
    public void testFilterOnStringContent() throws Exception {
        PreFilter preFilter = new PreFilter();
        Assert.assertTrue(preFilter.filterBrackets("()[]{}<>").isEmpty());
        System.out.println(preFilter.filterBrackets("()[]{}<>"));
    }

    @Test
    public void testFilterOnStringContentWithTextInBrackets() throws Exception {
        PreFilter preFilter = new PreFilter();
        Assert.assertTrue(preFilter.filterBrackets("(Qwerty)[ddd]{ABC}<mmm>").isEmpty());
        System.out.println(preFilter.filterBrackets("(Qwerty)[ddd]{ABC}<mmm>"));
    }

    @Test
    public void testFilterOnStringContentWithTextInBracketsGreaterThan100Symbols() throws Exception {
        PreFilter preFilter = new PreFilter();
        Assert.assertFalse(preFilter.filterBrackets("(За пару недель до презентации мы составили масштабный обзор всех основных слухов об iPhone 6, постоянно дополняемый по мере появления новой информации.)").isEmpty());
        System.out.println(preFilter.filterBrackets("(За пару недель до презентации мы составили масштабный обзор всех основных слухов об iPhone 6, постоянно дополняемый по мере появления новой информации.)"));
    }

    @Test
    public void testFilterOnStringContentWithTextInBracketsLessThan100Symbols() throws Exception {
        PreFilter preFilter = new PreFilter();
        Assert.assertTrue(preFilter.filterBrackets("(За пару недель до презентации мы составили масштабный обзор.)").isEmpty());
        System.out.println(preFilter.filterBrackets("(За пару недель до презентации мы составили масштабный обзор)"));
    }

    @Test
    public void testFilterOnFileContent() throws Exception {
        PreFilter preFilter = new PreFilter();
        TextReader reader = new TextReader();
        String source = reader.readText("src/test/resources/RussianWithBrackets.txt");
        System.out.println(preFilter.filterBrackets(source));
    }

    @Test
    public void testRemoveSimilarSentences() throws Exception {
        PreFilter preFilter = new PreFilter();
        RankedSentence rs1 = new RankedSentence(0,new Sentence("Мама мыла раму"));
        RankedSentence rs2 = new RankedSentence(0,new Sentence("Мама мыла рампу"));
        RankedSentence rs3 = new RankedSentence(0,new Sentence("Пума мыла раму"));
        RankedSentence rs4 = new RankedSentence(0,new Sentence("Пума мыла рампу ddddddddd"));
        RankedSentence rs5 = new RankedSentence(0,new Sentence("Мама мыла рампу dddddddd"));
        RankedSentence rs6 = new RankedSentence(0,new Sentence("После получения уведомления сотрудников канала эвакуировали, а на место сразу прибыли правоохранители, взрывотехники, кинологи и спасатели."));
        RankedSentence rs7 = new RankedSentence(0,new Sentence("После получения уведомления сотрудников канала эвакуировали, а на место сразу прибыли правоохранители, взрывотехники, кино."));
        List<RankedSentence> sentenceList = new ArrayList<>();
        sentenceList.add(rs1);
        sentenceList.add(rs2);
        sentenceList.add(rs3);
        sentenceList.add(rs4);
        sentenceList.add(rs5);
        sentenceList.add(rs6);
        sentenceList.add(rs7);

        sentenceList.forEach(out::println);
        out.println();
        preFilter.removeSimilarSentences(sentenceList,0.2)
                 .forEach(p -> out.println(p.getSentence().getText()));
    }



}
