import java.util.*;

// James Huang
// TA: Mitchell Levy
// Class of hangman simulates a manager of hangman game. 
// The client can guess the letter of the word one by one and compete with computer. 
public class HangmanManager {
    // TODO: Your Code Here
    private Set<String> dict;
    private int max;
    private Set<Character> answer; // all of the letters the client has already guessed
    private String pattern;

    /* 
     * @throws - IllegalArgumentException if the length of the word is less than 1 and 
     *                                       the times of making mistakes is less than 0
     * @param dictionary - the scope of words the client guess
     * @param length - the length of the words the client choose
     * @param max - the maximum times the client can make mistakes
     * This constructor initializes a new hangman object to record client's guess and
     * judge the result. 
     */
    public HangmanManager (Collection<String> dictionary, int length, int max) {
        if(length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }

        answer = new TreeSet<Character>();
        this.max = max;
        dict = new TreeSet<String>();
        pattern = "";
        for(int i = 0; i < length; i++) {
            pattern += "- ";
        }
        pattern = pattern.substring(0, pattern.length() - 1);
        for(String item : dictionary) {
            if(item.length() == length) {
                dict.add(item);
            }
        }
    }

    // Get the scope of words the client will guess
    public Set<String> words() {
        return dict;
    }

    // Get the remaining times that client could make mistakes
    public int guessesLeft() {
        return max;
    }
    
    // Get a set of all of the letters the client has guessed
    public Set<Character> guesses() {
        return answer;
    }

    // Get the current pattern
    // @throws - IllegalArgumentException if the scope is empty
    public String pattern() {
        if(dict.isEmpty()) {
            throw new IllegalStateException();
        }
        return pattern;
    }

    /*
     * Record the letter which client guesses and get the times of occurence of the 
     * guessed letter in current pattern
     * The new set of the words and the pattern will be updated after guessing
     * The times of making mistakes is drcreased by one if the letter does not 
     * appear in the words. 
     * @throws - IllegalStateException if the times of making mistakes is less than 1
     *           and the scope is empty
     *         - IllegalArgumentException if letter has already been guessed
     * @param guess - the letter the client guesses
     * @return - the times of occurence of guessed letter in the current pattern
     */
    public int record(char guess) {
        if(dict.isEmpty() || max < 1) {
            throw new IllegalStateException();
        }
        if(answer.contains(guess)) {
            throw new IllegalArgumentException();
        }

        answer.add(guess);
        int occurence = 0;
        
        // divide 
        divide(guess);

        // count
        for(int i = 0; i < pattern.length(); i++) {
            if(pattern.substring(i, i + 1).equals(guess + "")) {
                occurence++;
            }
        }
        if(occurence == 0) {
            max--;
        }
        return occurence;
    }

    /*
     * This is a helper method to divide the set of words into many sets with their keys, 
     * which are their pattern. 
     * @param guess - the letter the client guesses
     * @param length - the length of the words
     */
    private void divide(Character guess) {
        Map<String, Set<String>> newDict = new TreeMap<String, Set<String>>();
        for(String item : dict) {
            String key = "";
            for(int i = 0; i < pattern.length() / 2 + 1; i++) {
                if(pattern.charAt(2 * i) == '-') {
                    if(item.charAt(i) == guess) {
                        key += guess + " ";
                    }
                    else{
                        key += "- ";
                    }
                }
                else{
                    key += pattern.charAt(2 * i) + " ";
                }
            }
            // eliminating the last space
            key = key.substring(0, key.length() - 1);
            // adds item to map
            if(!newDict.keySet().contains(key)) {
                newDict.put(key, new TreeSet<String>());
            }
            newDict.get(key).add(item);
        }
        select(newDict);
    }
 
    /* 
     * This is a helper method to select the biggest set and ignore the others. 
     * @param map - the map storaging the divided words in different patterns
     */
    private void select(Map<String, Set<String>> map) {
        int temp = 0;
        for(String key : map.keySet()) {
            if(map.get(key).size() > temp) {
                temp = map.get(key).size();
                dict = map.get(key);
                pattern = key;
            }
        }
    }

}
