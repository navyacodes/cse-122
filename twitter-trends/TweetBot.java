// Navya Jain 
// 11/14/2024
// CSE 122 
// C2: Twitter Trends
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class simulates a Twitter bot that stores and cycles through a list of tweets.
// Users can retrieve tweets sequentially, add new tweets, remove tweets, reset 
// to the beginning of the list, or find the number of tweets in the list.

public class TweetBot {
    private List<Tweet> tweetList;
    private int nextTweetNumber;

    // Behavior:
    //   - This method constructs a TweetBot given an initial list of tweets with one or more
    //     tweets.
    // Parameters:
    //   - tweets: the initial list of tweets to load into the bot.
    // Return: none
    // Exceptions:
    //   - Throws IllegalArgumentException if the provided tweet list is empty.
    public TweetBot(List<Tweet> tweets) {
        if (tweets.size() < 1) {
            throw new IllegalArgumentException();
        }
        List<Tweet> tweetList = new ArrayList<>();
        tweetList.addAll(tweets);
        this.tweetList = tweetList;
        nextTweetNumber = 0;
    }

    // Behavior:
    //   - This method gets and returns the total number of tweets in the bots list.
    // Parameters: none.
    // Returns:
    //   - int: the number of tweets in the TweetBot list.
    // Exceptions: none.
    public int numTweets() {
        return tweetList.size();
    }
    
    // Behavior:
    //   - This method adds a new tweet to the TweetBots' tweets list.
    // Parameters:
    //   - tweet: the Tweet to be added to the bot.
    // Returns: none.
    // Exceptions: none.
    public void addTweet(Tweet tweet) {
        this.tweetList.add(tweet);
    }

    // Behavior:
    //   - Retrieves the next tweet in sequence, wrapping around if the end of the TweetBots
    //     tweets list is reached, and adjusting for the addition or removal of tweets such that 
    //     all tweets will be shown when this is called continually (none will be skipped).
    // Parameters: none.
    // Returns:
    //   - Tweet: the next tweet in the sequence.
    // Exceptions: none.
    public Tweet nextTweet() {
        nextTweetNumber++;
        if (nextTweetNumber > tweetList.size()) {
            nextTweetNumber = 1;
        }
        return tweetList.get(nextTweetNumber - 1);     
    }

    // Behavior:
    //   - This method removes a specific tweet from the TweetBots' tweets list, if present.
    // Parameters:
    //   - tweet: the Tweet to remove.
    // Returns: none
    // Exceptions: none.
    public void removeTweet(Tweet tweet) {
        if (tweetList.contains(tweet)) {
            if (tweetList.indexOf(tweet) <= (nextTweetNumber - 1)) {
                nextTweetNumber--;
            } 
            tweetList.remove(tweet);
        }
    }

    // Behavior:
    //   - This method resets the bots' reading of the next tweet to the beginning of the tweet
    //     list; asking the bot for the next tweet after calling this will give you the first 
    //     tweet. 
    // Parameters: none
    // Returns: none
    // Exceptions: none
    public void reset() {
        nextTweetNumber = 0;
    }
}   
