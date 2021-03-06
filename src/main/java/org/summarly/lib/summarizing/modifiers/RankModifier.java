package org.summarly.lib.summarizing.modifiers;

import org.summarly.lib.common.RankedSentence;

import java.util.List;

/**
 * @author Anton Chernetskij
 */
public interface RankModifier {
    public List<RankedSentence> modify(List<RankedSentence> text);
}
