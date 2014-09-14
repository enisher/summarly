package org.summarly.lib.summarizing.modifiers;

import org.summarly.lib.common.RankedSentence;

/**
 * @author Anton Chernetskij
 */
public class NumbersModifier extends BaseRankModifier {

    private static final double K = 1.1;

    @Override
    public RankedSentence modifySentence(RankedSentence sentence) {

        String text = sentence.getSentence().getText().trim();
        for (int i = 1; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                sentence.setRank(sentence.getRank() * K);
                break;
            }
        }
        return sentence;
    }
}
