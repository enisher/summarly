package org.summarly.lib.summarizing;

import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Text;

import java.util.List;

/**
 * Created by Anton Chernetskij
 */
public interface Ranker {

    List<RankedSentence> rank(Text text);

}
