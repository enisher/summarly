package org.summarly.lib.segmentation;

import junit.framework.TestCase;

public class LuceneSplitterTest extends TestCase {

    public void testSplit() throws Exception {
        new LuceneSplitter().splitSentence("У попа была собака он ее любил");
    }
}