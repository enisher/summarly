package org.summarly.lib.segmentation;

import com.ibm.icu.text.BreakIterator;
import junit.framework.TestCase;

import java.util.Locale;

public class LuceneSplitterTest extends TestCase {

    public void testSplitTextOnSentences() {
        new LuceneSplitter().splitTextOnSentences("Это наш тест. Это наш T.L.A. тест (возможно вы знаете о чем он). \"Сейчас проф. работает над ним! Ура.\" Все... Сейчас проф. Келдыш Н.А. работает над ним?")
                            .forEach(System.out::println);
    }
}