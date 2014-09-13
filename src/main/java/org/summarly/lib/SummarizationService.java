package org.summarly.lib;

import java.util.List;

/**
 * @author Anton Chernetskij
 */
public interface SummarizationService {
    public List<String> summarise(String text, double ratio);
}
