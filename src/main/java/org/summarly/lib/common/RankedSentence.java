package org.summarly.lib.common;

public class RankedSentence implements Comparable<RankedSentence> {

    private double rank;
    private Sentence sentence;

    public RankedSentence(double rank, Sentence sentence) {
        this.rank = rank;
        this.sentence = sentence;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public double getRank() {
        return rank;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public int compareTo(RankedSentence o) {
        return Double.compare(rank, o.rank);
    }

    @Override
    public String toString() {
        return "RankedSentence{" +
                "rank=" + rank +
                ", sentence=" + sentence +
                '}';
    }
}