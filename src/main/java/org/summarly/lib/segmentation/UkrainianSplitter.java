package org.summarly.lib.segmentation;

import org.apache.lucene.analysis.Analyzer;

import java.util.List;

/**
 * @author Anton Chernetskij
 */
public class UkrainianSplitter extends LuceneSplitter {
    @Override
    protected List<String> extractNormalizedTokens(String text, Analyzer analyzer) {

        return super.extractNormalizedTokens(text, analyzer);
    }
}
