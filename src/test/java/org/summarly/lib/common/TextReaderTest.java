package org.summarly.lib.common;

import org.junit.Test;

import java.net.URL;

import junit.framework.Assert;

public class TextReaderTest {

    @Test
    public void testGetTextFromURL() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText(new URL("http://uainfo.org/blognews/394212-v-umovah-vyni-ne-mozhna-zaprovadzhuvati-osobliviy-status-donbasu-timoshenko.html"));

//        System.out.println(reader.readTextFromURL("http://www.wired.com/2014/09/international-rock-flipping-day/").getKDPVimage());
//        System.out.println(reader.readTextFromURL("http://habrahabr.ru/post/236715/").getKDPVimage());
        System.out.println(reader.readTextFromURL("http://www.wired.com/2014/09/geeks-guide-kim-harrison/").getKDPVimage());
        System.out.println(reader.readTextFromURL("http://techcrunch.com/2014/09/13/producthunt-raises-6-million-from-a16z/").getKDPVimage());
//        System.out.println(reader.readTextFromURL("http://uainfo.org/blognews/394660-segodnya-my-vernulis-k-toy-tochke-s-kotoroy-21-noyabrya-nachalsya-maydan-kolesnikov.html").getKDPVimage());


        Assert.assertNotNull(s);
        }

        }
