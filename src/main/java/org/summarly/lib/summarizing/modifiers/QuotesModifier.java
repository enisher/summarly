package org.summarly.lib.summarizing.modifiers;

import org.summarly.lib.common.RankedSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Chernetskij
 */
public class QuotesModifier extends BaseRankModifier {

    private static final double K = 1.1;
    protected Pattern pattern = Pattern.compile("[\\\"']");    //todo add more quotes

    @Override
    public RankedSentence modifySentence(RankedSentence sentence) {
        String text = sentence.getSentence().getSentence().trim();
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            sentence.setRank(sentence.getRank() * K);
        }
        return sentence;
    }
}
