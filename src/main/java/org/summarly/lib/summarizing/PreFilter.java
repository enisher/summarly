package org.summarly.lib.summarizing;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by vaz on 9/13/2014.
 */
public class PreFilter {

    public String filterBrackets(String text) {
        return StringUtils.strip(text, "(){}[]<>");
    }
}
