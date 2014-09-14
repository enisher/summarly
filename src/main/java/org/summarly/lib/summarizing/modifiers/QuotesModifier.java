package org.summarly.lib.summarizing.modifiers;

import org.summarly.lib.common.RankedSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Chernetskij
 */
public class QuotesModifier extends BaseRankModifier {

    private static final double K = 1.2;
    protected Pattern pattern = Pattern.compile("[\\\"‘’“”'\"«»„”]");

    @Override
    public RankedSentence modifySentence(RankedSentence sentence) {
        String text = sentence.getSentence().getText().trim();
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            sentence.setRank(sentence.getRank() * K);
        }
        return sentence;
    }
}
