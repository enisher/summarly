package org.summarly;

import java.util.List;

/**
 * @author Anton Chernetskij
 */
public interface SummarizationService {
    public List<String> summarise(String text, double ratio);
}
