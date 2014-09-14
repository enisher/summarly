package org.summarly.lib.summarizing;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.summarly.lib.common.HashBag;
import org.summarly.lib.common.RankedSentence;
import org.summarly.lib.common.Sentence;
import org.summarly.lib.common.Text;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Anton Chernetskij
 */
public class LexRankRanker implements Ranker {

    private static final Logger LOGGER = Logger.getLogger(Ranker.class.getName());
    protected static final double THRESHOLD = 0.065;
    private static final double DAMPING_FACTOR = 0.1;

    /**
     * Creates extractive summary of the text
     *
     * @param text
     * @return
     */
    public List<RankedSentence> rank(Text text) {

        LOGGER.info("Summarizing text " + text.getName());

        long start = System.currentTimeMillis();

        double[][] similarities = getSimilarities(text);
//        similarities = filter(similarities, THRESHOLD);

        LOGGER.info("Passed " + numNotZero(similarities) + " connections for " + text.numSentences() + "sentences.");

        double[] ranks = calcRanks(similarities);

        List<Sentence> sentences = text.getSentences();
        List<RankedSentence> rankedSentences = new ArrayList<RankedSentence>(text.numSentences());
        for (int i = 0; i < sentences.size(); i++) {
            Sentence sentence = sentences.get(i);
            rankedSentences.add(new RankedSentence(ranks[i], sentence));
        }

        long finish = System.currentTimeMillis();
        LOGGER.info("Summarizing " + text.getName() + " finished in " + (finish - start) + " ms");
        return rankedSentences;
    }

    protected double[][] getSimilarities(Text text) {
        int numSentences = text.numSentences();
        double[][] similarities = new double[numSentences][numSentences];
        List<Sentence> sentences = text.getSentences();
        Map<String, Double> stringDoubleMap = inverseDocumentFrequency(text);
        for (int i = 0; i < numSentences; i++) {
            for (int j = 0; j < i; j++) {
                similarities[i][j] = similarity(sentences.get(i), sentences.get(j), stringDoubleMap);
                if (Double.isNaN(similarities[i][j])) {
                    similarities[i][j] = 0;
                    LOGGER.severe("NaN similarity for \"" + sentences.get(i) + "\" and \"" + sentences.get(j) + "\" in text \"" + text + "\"");
                }
                similarities[j][i] = similarities[i][j];
            }
            similarities[i][i] = 1;
        }
        return similarities;
    }

    protected double[][] filter(double[][] similarities, double threshold) {
        for (int i = 0; i < similarities.length; i++) {
            for (int j = 0; j < similarities.length; j++) {
                if (similarities[i][j] < threshold) {
                    similarities[i][j] = 0;
                }
            }
        }
        return similarities;
    }

    protected int numNotZero(double[][] similarities) {
        int result = 0;
        for (double[] similarityRow : similarities) {
            for (int j = 0; j < similarities.length; j++) {
                if (similarityRow[j] > 0) {
                    result++;
                }
            }
        }
        return result;
    }

    protected double similarity(Sentence sentence1, Sentence sentence2, Map<String, Double> idf) {
        List<String> words1 = sentence1.getWords();
        List<String> words2 = sentence2.getWords();
        HashBag<String> bag1 = new HashBag<String>(words1);
        HashBag<String> bag2 = new HashBag<String>(words2);
        Set<String> allWords = new TreeSet<String>();
        double s1 = 0;
        for (String word : words1) {
            s1 += Math.pow(bag1.get(word) * idf.get(word), 2);
            allWords.add(word);
        }
        double s2 = 0;
        for (String word : words2) {
            s2 += Math.pow(bag2.get(word) * idf.get(word), 2);
            allWords.add(word);
        }
        double mul = 0;
        for (String word : allWords) {
            mul += bag1.get(word) * bag2.get(word) * Math.pow(idf.get(word), 2);
        }
        return mul / Math.sqrt(s1 * s2);
    }

    protected Map<String, Double> inverseDocumentFrequency(Text text) {
        List<Sentence> sentences = text.getSentences();
        Set<String> allWords = new TreeSet<String>();
        for (Sentence sentence : sentences) {
            List<String> wordsOfSentence = sentence.getWords();
            for (String word : wordsOfSentence) {
                allWords.add(word);
            }
        }

        double textSize = sentences.size();
        Map<String, Double> result = new HashMap<String, Double>();
        for (String word : allWords) {
            int num = 0;
            for (Sentence sentence : sentences) {
                if (sentence.contains(word)) {
                    num++;
                }
            }
            result.put(word, Math.log(textSize / num));
        }
        return result;
    }

    protected double[][] normalize(double[][] similarities) {
        for (double[] row : similarities) {
            double sum = 0;
            for (double similarity : row) {
                sum += similarity;
            }
            for (int j = 0; j < row.length; j++) {
                row[j] = row[j] / sum;
            }
        }
        return similarities;
    }

    protected double[] calcRanks(double[][] similarities) {
        similarities = normalize(similarities);
        final int n = similarities.length;
        double[] p = new double[n];
        for (int i = 0; i < p.length; i++) {
            p[i] = 1.0 / n;
        }
        double[][] kernelData = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                kernelData[i][j] = DAMPING_FACTOR / n + (1 - DAMPING_FACTOR) * similarities[i][j];
            }
        }
        RealMatrix transitionKernel = new Array2DRowRealMatrix(kernelData, false).transpose();
        RealMatrix ranks = new Array2DRowRealMatrix(p);
        double eps = 0;
        int iterations = 0;
        do {
            RealMatrix oldRanks = ranks;
            ranks = transitionKernel.multiply(ranks);
            eps = ranks.subtract(oldRanks).getNorm();
            iterations++;
        } while (eps > 0.0001);
        LOGGER.info("Computed kernel in " + iterations + " iterations.");
        double[] result = ranks.transpose().getData()[0];
        normalise(result);
        return result;
    }

    protected void normalise(double[] ranks) {
        double max = 0;
        double min = 1;
        for (double rank : ranks) {
            if (max < rank) {
                max = rank;
            }
            if (min > rank) {
                min = rank;
            }
        }
        max = max - min;
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = (ranks[i] - min) / max;
        }
    }

    protected void printArr(double[][] arr) {
        for (double[] doubles : arr) {
            StringBuilder builder = new StringBuilder("{");
            for (double val : doubles) {
                builder.append(String.format("%f ,", val));
            }
            builder.append("},");
            System.out.println(builder.toString());
        }
    }


}
