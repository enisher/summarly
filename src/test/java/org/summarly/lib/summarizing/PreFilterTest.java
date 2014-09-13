package org.summarly.lib.summarizing;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vaz on 9/13/2014.
 */
public class PreFilterTest {
    @Test
    public void testFilter() throws Exception {
        PreFilter preFilter = new PreFilter();
        Assert.assertTrue(preFilter.filterBrackets("()[]{}<>").isEmpty());
    }
}
