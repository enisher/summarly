package org.summarly.lib;

/**
 * @author Max Grin
 */
public class Util {
    public static int paragraphNumber(int position, String text) {
        int lines = 1;
        int pos = 0;
        while ((pos = text.substring(0, position).indexOf("\n", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }
}
