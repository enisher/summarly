package org.summarly.lib.summarizing.modifiers;

import org.summarly.lib.common.RankedSentence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Chernetskij
 */
public abstract class BaseRankModifier implements RankModifier{
    @Override
    public List<RankedSentence> modify(List<RankedSentence> text) {
        List<RankedSentence> result = new ArrayList<>(text.size());
        for (RankedSentence sentence : text) {
            result.add(modifySentence(sentence));
        }
        return result;
    }

    public abstract RankedSentence modifySentence(RankedSentence sentence);
}
