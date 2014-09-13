package org.summarly.segmentation;

import org.summarly.common.Text;

/**
 * Created by Anton Chernetskij
 */
public interface TextSplitter {
    Text split(String text, String name);
}
