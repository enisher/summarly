package org.summarly.lib.summarizing.modifiers;

import org.junit.Assert;
import org.junit.Test;
import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Sentence;

import java.util.regex.Pattern;

/**
 * @author Anton Chernetskij
 */
public class QuotesModifierTest {
    @Test
    public void testModifySentence() throws Exception {
        Pattern pattern = new QuotesModifier().pattern;
        Assert.assertTrue(pattern.matcher("aaaaa\"bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa'bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa‘bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa’bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa“bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa”bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa«bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa»bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa„bbb").find());
        Assert.assertTrue(pattern.matcher("aaaaa”bbb").find());
    }
}
