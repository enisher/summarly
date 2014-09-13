package org.summarly.lib.summarizing;

import org.summarly.lib.common.RankedSentence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anton Chernetskij
 */
public class Filter {
    public List<RankedSentence> filter(List<RankedSentence> rankedText, double ratio){
        if (ratio > 1 || ratio < 0) {
            throw new IllegalArgumentException("Filter ratio must be between 0 and 1.");
        }
        List<RankedSentence> list = new ArrayList<RankedSentence>(rankedText);
        Collections.sort(list);
        Collections.reverse(list);

        int summarySize = (int) Math.round(ratio * rankedText.size());
        final double minRank = list.get(summarySize - 1).getRank();
        return rankedText.stream()
                .filter(p -> p.getRank() >= minRank)
                .collect(Collectors.<RankedSentence>toList());
    }
}
