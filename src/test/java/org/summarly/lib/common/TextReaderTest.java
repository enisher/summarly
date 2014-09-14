package org.summarly.lib.common;

import org.junit.Test;

import java.net.URL;

import junit.framework.Assert;

public class TextReaderTest {

    @Test
    public void testGetTextFromURL() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText(new URL("http://uainfo.org/blognews/394212-v-umovah-vyni-ne-mozhna-zaprovadzhuvati-osobliviy-status-donbasu-timoshenko.html"));

        Article a = reader.readTextFromURL("http://habrahabr.ru/post/236717/");

        Assert.assertNotNull(s);
        }

        }
