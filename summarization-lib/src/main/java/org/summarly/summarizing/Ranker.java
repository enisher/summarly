package org.summarly.summarizing;

import org.summarly.common.RankedSentence;
import org.summarly.common.Text;

import java.util.List;

/**
 * Created by Anton Chernetskij
 */
public interface Ranker {

    List<RankedSentence> rank(Text text);

}
