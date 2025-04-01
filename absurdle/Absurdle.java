// Navya Jain 
// 11/07/2024
// CSE 122 
// P2: Absurdle
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class implements the Absurdle game, where the user attempts to guess a word based on 
// feedback patterns. The user is prompted for a dictionary file and word length. Next, the word 
// guessing begins, and the game continues until the user guesses the correct word. Feedback is 
// provided for each guess, indicating which letters are correct (green), in the wrong position 
// (yellow), or incorrect (gray). The user's journey is displayed at the end, with an emoji for 
// each letter in each guess’s result. The class manages the dictionary of possible words, 
// processes user guesses, and updates the game state until completion. The feedback patterns are 
// chosen to prolong the game as long as possible. 
public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = recordGuess(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    // Behavior:
    //   - This method filters a list of words to only include those of a specified length.
    // Parameters:
    //   - contents: a List<String> containing all words in the dictionary file.
    //   - wordLength: the desired length of words to be used in the game.
    // Returns:
    //   - Set<String>: a set of words from the dictionary that match the specified length.
    // Exceptions:
    //   - Throws an IllegalArgumentException if the specified word length is less than 1.    
    public static Set<String> pruneDictionary(List<String> contents, int wordLength) {
        if (wordLength < 1) {
            throw new IllegalArgumentException("the word length can't be less than 1!");
        }
        Set<String> prunedDictionary = new HashSet<>();
        for (String wordInDictionary : contents) {
            if (wordInDictionary.length() == wordLength) {
                prunedDictionary.add(wordInDictionary);
            }
        }
        return prunedDictionary;
    }

    // Behavior:
    //   - This method records a user's guess by generating the pattern feedback for the guess,
    //     and updating the possible words list based on this feedback to maximize the number
    //     of possible words left.
    // Parameters:
    //   - guess: the word guessed by the user.
    //   - words: the set of possible words that match prior patterns.
    //   - wordLength: the length of the target word for consistency checking.
    // Returns:
    //   - String: a pattern showing the feedback for the user's guess (GREEN, YELLOW, or GRAY).
    // Exceptions:
    //   - Throws IllegalArgumentException if there are no possible words in the set or if the 
    //     guess length does not match the word length originally requested.    
    public static String recordGuess(String guess, Set<String> words, int wordLength) {
        if (words.isEmpty() || guess.length() != wordLength) {
            throw new IllegalArgumentException();
        }
        Map<String, Set<String>> patternsMap = sortPossibleWordPatterns(guess, words);
        String patternMostWords = largestPatternSet(patternsMap);
        words.clear();
        words.addAll(patternsMap.get(patternMostWords));
        return patternMostWords;
    }

    // Behavior:
    //   - This method organizes possible words into groups based on the feedback pattern each  
    //     would produce when compared to the user's guess.
    // Parameters:
    //   - guess: the word guessed by the user.
    //   - words: a set of possible words that might match the target word.
    // Returns:
    //   - Map<String, Set<String>>: a map where patterns are associated with the sets of words
    //                               that produce each pattern when compared to the guess.
    // Exceptions: none
    public static Map<String, Set<String>> sortPossibleWordPatterns(String guess, 
                                                                    Set<String> words) {
        Map<String, Set<String>> patternsMap = new TreeMap<>();
        for (String possibleWord : words) {
            String patternPossibleWord = patternFor(possibleWord, guess);
            if (!patternsMap.containsKey(patternPossibleWord)) {
                patternsMap.put(patternPossibleWord, new HashSet<String>());
            }
            patternsMap.get(patternPossibleWord).add(possibleWord);
        }
        return patternsMap;
    }

    // Behavior:
    //   - This method identifies the pattern associated with the largest set of possible 
    //     remaining words from the map of patterns.
    // Parameters:
    //   - patternsMap: a map where patterns are associated with the sets of words
    //                  that produce each pattern when compared to the guess.
    // Returns:
    //   - String: the pattern associated with the largest set of possible words.
    // Exceptions: none.    
    public static String largestPatternSet(Map<String, Set<String>> patternsMap) {
        String patternMostWords = "";
        int totalPossibleWords = 0;
        for (String pattern : patternsMap.keySet()) {
            if (patternsMap.get(pattern).size() > totalPossibleWords) {
                patternMostWords = pattern;
                totalPossibleWords = patternsMap.get(pattern).size();
            }
        }
        return patternMostWords;
    }

    // Behavior:
    //   - This method generates a feedback pattern by comparing the guessed word to the target 
    //     word, marking letters as green if correct and in the right position, yellow if correct
    //     but in the wrong position, and gray if incorrect.
    // Parameters:
    //   - word: the actual word against which the guess is compared.
    //   - guess: the user's guessed word.
    // Returns:
    //   - String: a pattern showing feedback (green, yellow, or gray) for each letter in the 
    //             guess.
    // Exceptions: none.   
    public static String patternFor(String word, String guess) {
        List<String> userPatternUpdates = new ArrayList<String>();
        Map<Character, Integer> wordCharacterCount = loadWordCharMap(word);
        for (int i = 0; i < guess.length(); i++) {
            if (word.charAt(i) == guess.charAt(i)) {
                userPatternUpdates.add(GREEN);
                wordCharacterCount.put(guess.charAt(i), 
                                       (wordCharacterCount.get(guess.charAt(i)) - 1));
            } 
            else {
                userPatternUpdates.add(GRAY);
            }
        }
        String userPattern = "";
        for (int i = 0; i < guess.length(); i++) {
            if (wordCharacterCount.containsKey(guess.charAt(i)) && 
                wordCharacterCount.get(guess.charAt(i)) > 0 && 
                !userPatternUpdates.get(i).equals(GREEN)) {
                
                userPatternUpdates.set(i, YELLOW);
                wordCharacterCount.put(guess.charAt(i), 
                                       wordCharacterCount.get(guess.charAt(i)) - 1);
            }
            userPattern += userPatternUpdates.get(i);
        }
        return userPattern;
    }


    // Behavior:
    //   - This method counts the frequency of each character in the given word and stores 
    //     it in a map.
    // Parameters:
    //   - word: the word for which character frequencies are counted.
    // Returns:
    //   - Map<Character, Integer>: a map where each character in the word maps to the number 
    //                              of times it appears.
    // Exceptions: none   
    public static Map<Character, Integer> loadWordCharMap(String word) {
        Map<Character, Integer> wordCharacterCount = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            if (wordCharacterCount.containsKey(word.charAt(i))) {
                wordCharacterCount.put(word.charAt(i), wordCharacterCount.get(word.charAt(i)) + 1);
            }
            else {
                wordCharacterCount.put(word.charAt(i), 1);
            }
        }
        return wordCharacterCount;
    }
}
