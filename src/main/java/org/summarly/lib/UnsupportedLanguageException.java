package org.summarly.lib;

/**
 * @author Maxim Grin
 */
public class UnsupportedLanguageException extends Exception {
    final String language;

    public UnsupportedLanguageException(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
