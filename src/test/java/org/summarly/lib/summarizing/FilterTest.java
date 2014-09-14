package org.summarly.lib.summarizing;

import org.junit.Assert;
import org.junit.Test;
import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Chernetskij
 */
public class FilterTest {

    @Test
    public void testFilter() throws Exception {
        Filter filter = new Filter();
        ArrayList<RankedSentence> text = new ArrayList<>();
        text.add(new RankedSentence(0.6, new Sentence("a")));
        text.add(new RankedSentence(0.8, new Sentence("b")));
        text.add(new RankedSentence(0.6, new Sentence("c")));
        text.add(new RankedSentence(0.5, new Sentence("d")));
        text.add(new RankedSentence(0.1, new Sentence("e")));
        text.add(new RankedSentence(0.9, new Sentence("f")));
        text.add(new RankedSentence(0.3, new Sentence("g")));
        text.add(new RankedSentence(0.7, new Sentence("h")));
        text.add(new RankedSentence(0.4, new Sentence("i")));
        text.add(new RankedSentence(0.1, new Sentence("j")));

        List<RankedSentence> result = filter.filter(text, 0.3);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals("b", result.get(0).getSentence().getText());
        Assert.assertEquals("f", result.get(1).getSentence().getText());
        Assert.assertEquals("h", result.get(2).getSentence().getText());
    }
}
