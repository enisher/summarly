package org.summarly.common;

import org.junit.Test;
import org.summarly.common.TextReaderTest;

import java.net.URL;
import java.util.List;

import junit.framework.Assert;

public class TextReaderTest {
    
    @Test
    public void testGetTextFromURL() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText(new URL("http://uainfo.org/blognews/394212-v-umovah-vyni-ne-mozhna-zaprovadzhuvati-osobliviy-status-donbasu-timoshenko.html"));

        Assert.assertNotNull(s);
    }
    
}
