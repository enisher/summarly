package org.summarly.summarizing;

import org.summarly.common.RankedSentence;

import java.util.List;

/**
 * @author Anton Chernetskij
 */
public interface RankModifier {
    public List<RankedSentence> modify(List<RankedSentence> text);
}
