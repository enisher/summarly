package org.summarly.lib.summarizing;

//

import org.apache.commons.lang3.StringUtils;
import org.summarly.lib.common.RankedSentence;

import java.util.*;

/**
 * Created by vaz on 9/13/2014.
 */
public class PreFilter {

    public static final int DEFAULT_LEVENSHTEIN_DISTANCE_THRESHOLD = 3;
    public static final double DEFAULT_PERCENT_THRESHOLD = 0.15d;

    public String filterBrackets(String text) {
        return text.replaceAll("\\[.{0,100}?\\]","")
                   .replaceAll("\\(.{0,100}?\\)", "")
                   .replaceAll("<.{0,100}?>", "")
                   .replaceAll("\\{.{0,100}?\\}", "");
    }

    public List<RankedSentence> removeSimilarSentences(List<RankedSentence> sentences) {
        return removeSimilarSentences(sentences, DEFAULT_PERCENT_THRESHOLD);
    }

    public List<RankedSentence> removeSimilarSentences(List<RankedSentence> sentences, double threshold) {
        if(sentences.size()<3) return sentences;
        threshold = (threshold == 0) ? DEFAULT_PERCENT_THRESHOLD : threshold;
        List<RankedSentence> filtered = new ArrayList<>();
        Iterator<RankedSentence> iterator = sentences.iterator();
        int currentPosition = 0;
        while (iterator.hasNext()) {
            RankedSentence current = iterator.next();

            boolean matchedWithPrevious = false;
            for (int prevPos = currentPosition - 1; prevPos >= 0 && prevPos > currentPosition - 3; prevPos--) {
                matchedWithPrevious |= !checkThreshold(threshold, current, sentences.get(prevPos));
            }

            if (!matchedWithPrevious) {
                filtered.add(current);
            }

            currentPosition++;
        }
        return filtered;
    }

    private boolean checkThreshold(double threshold, RankedSentence current, RankedSentence behind) {
        threshold = (threshold > 0) ?  threshold : DEFAULT_PERCENT_THRESHOLD;
        String text = current.getSentence().getText();
        int sentence_length = Math.min(text.length(), behind.getSentence().getText().length());
        double levenstain_threshold = sentence_length * threshold;
        return StringUtils.getLevenshteinDistance(behind.getSentence().getText(), text) > levenstain_threshold;
    }
}
