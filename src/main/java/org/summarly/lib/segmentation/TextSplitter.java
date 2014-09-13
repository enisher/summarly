package org.summarly.lib.segmentation;

import org.summarly.lib.common.Text;

/**
 * Created by Anton Chernetskij
 */
public interface TextSplitter {
    Text split(String text, String name);
}
