package org.summarly.lib.summarizing;

import org.junit.Assert;
import org.junit.Test;
import org.summarly.lib.common.TextReader;

/**
 * Created by vaz on 9/13/2014.
 */
public class PreFilterTest {
    @Test
    public void testFilterOnStringContent() throws Exception {
        PreFilter preFilter = new PreFilter();
        Assert.assertTrue(preFilter.filterBrackets("()[]{}<>").isEmpty());
    }

    @Test
    public void testFilterOnFileContent() throws Exception {
        PreFilter preFilter = new PreFilter();
        TextReader reader = new TextReader();
        String source = reader.readText("src/test/resources/RussianWithBrackets.txt");
        System.out.println(preFilter.filterBrackets(source));
    }
}
