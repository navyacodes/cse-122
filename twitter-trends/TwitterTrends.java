// Navya Jain 
// 11/14/2024
// CSE 122 
// C2: Twitter Trends
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class analyzes tweet data stored in a TweetBot, identifying the word that is the most
// frequent in captions of tweets. It also allows the user to determine which tweets are trending
// by presenting which tweets have the most likes or retweets, or both likes and retweets combined.

public class TwitterTrends {
    private TweetBot bot;

    // Behavior:
    //   - This method constructs a TwitterTrends analyzer for analyzing trends in a TweetBot 
    //     (which can continually be updated).
    // Parameters:
    //   - bot: the TweetBot containing tweets for analysis.
    public TwitterTrends(TweetBot bot) {
        this.bot = bot;
    }

    // Behavior:
    //   - This method finds and returns the most frequently occurring word across the captions
    //     of all tweets in the originally provided TweetBot (considering all updates to this bot).
    // Parameters: none.
    // Returns:
    //   - String: the most frequently appearing word in the tweet captions.
    // Exceptions: none
    public String getMostFrequentWord() {
        Map<String, Integer> frequentWords = new HashMap<>();
        for (int i = 0; i < bot.numTweets(); i++) {
            Tweet tweet = bot.nextTweet();
            String tweetCaption = tweet.getCaption();
            Scanner captionReader = new Scanner(tweetCaption);
            while (captionReader.hasNext()) {
                String word = captionReader.next().toLowerCase();
                if (frequentWords.containsKey(word)) {
                    frequentWords.put(word, frequentWords.get(word) + 1);
                } else {
                    frequentWords.put(word, 1);
                }
            }
        }
        String mostCommonWord = "";
        int mostAppearances = 0;
        for (String word : frequentWords.keySet()) {
            if (frequentWords.get(word) > mostAppearances) {
                mostAppearances = frequentWords.get(word);
                mostCommonWord = word;
            }
        }
        return mostCommonWord;
    }

    // Behavior:
    //   - This method finds and returns the caption of the tweet with the highest of a 
    //     certain metric; the metric to judge the 'trendiness' of the is chosen by the user
    //     (likes, retweets, or both combined). If the chosen metric is not one of the three,
    //     an empty cpation will be returned.
    // Parameters:
    //   - chosenMetric: a string specifying the metric type by which to judge the trendiness 
    //                   ("likes", "retweets", or "likes and retweets").
    // Returns:
    //   - String: the caption of the tweet with the highest of the chosen metric (most trending),
    //             or nothing (an empty caption) if the chosen metric is invalid.
    public String trendingTweets(String chosenMetric) {
        int mostChosenMetric = 0;
        String captionMostChosenMetric = "";
        for (int i = 0; i < bot.numTweets(); i++) {
            Tweet tweet = bot.nextTweet();
            if (chosenMetric.equalsIgnoreCase("likes")) {
                if (tweet.getLikes() > mostChosenMetric) {
                    captionMostChosenMetric = tweet.getCaption();
                    mostChosenMetric = tweet.getLikes();
                }
            }
            else if (chosenMetric.equalsIgnoreCase("retweets")) {
                if (tweet.getRetweets() > mostChosenMetric) {
                    captionMostChosenMetric = tweet.getCaption();
                    mostChosenMetric = tweet.getRetweets();
                }
            }
            else if (chosenMetric.equalsIgnoreCase("likes and retweets")) {
                if (tweet.getRetweets() + tweet.getLikes() > mostChosenMetric) {
                    captionMostChosenMetric = tweet.getCaption();
                    mostChosenMetric = tweet.getRetweets() + tweet.getLikes();
                }
            }
        }
        return captionMostChosenMetric;
    }
}
