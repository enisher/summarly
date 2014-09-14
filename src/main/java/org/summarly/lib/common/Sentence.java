package org.summarly.lib.common;

import java.util.List;

/**
 * Created by Anton Chernetskij
 */
public class Sentence {

    private static final String[] specWords = new String[]{"-lrb-", "-rrb-"};

    private String sentence;
    private List<String> words;
    private int paragraphNum;

    public Sentence(String sentence) {
        this.sentence = sentence;
    }

    public String getText() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            words.set(i, word.toLowerCase());       //todo remove to lowercase
        }
        this.words = filterSpecWords(words);
    }

    private List<String> filterSpecWords(List<String> words) {
        for (int i = 0; i < specWords.length; i++) {
            String specWord = specWords[i];
            while (words.contains(specWord)) {
                int index = words.indexOf(specWord);
                words.remove(index);
            }

        }
        return words;
    }

    public int getParagraphNum() {
        return paragraphNum;
    }

    public void setParagraphNum(int paragraphNum) {
        this.paragraphNum = paragraphNum;
    }

    @Override
    public String toString() {
        return sentence;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }
}
