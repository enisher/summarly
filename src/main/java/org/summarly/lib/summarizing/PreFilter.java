package org.summarly.lib.summarizing;

//

import org.springframework.util.StringUtils;

/**
 * Created by vaz on 9/13/2014.
 */
public class PreFilter {

    public String filterBrackets(String text) {
        String res = StringUtils.deleteAny(text, "(){}[]<>");
        return res;
    }
}
