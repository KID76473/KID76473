import java.util.*;

// James Huang
// TA: Mitchell Levy
// AnagramSolver is a class to test whether a text has anagram in a dictionary or not
public class AnagramSolver {
    // TODO: Your Code Here
    private Map<String, LetterInventory> processed;
    private List<String> dictionary;

    // @param dictionary - the set of words to find the corresponding anagram
    // This constructor initializes a new anagramSOlver object which find the anagram
    // for the given string
    public AnagramSolver(List<String> dictionary) {
        this.dictionary = dictionary;
        processed = new HashMap<String, LetterInventory>();
        for(String item : dictionary) {
            processed.put(item, new LetterInventory(item));
        }
    }

    /*
     * Get all of combinations of anagram from given words. 
     * @throws - IllegalArgumentException if max is less than 0
     * @param text - find the anagram according to this string
     * @param max - the maximum number in the anagrams and it is unlimited
     *              if max is zero.
     */
    public void print(String text, int max) {
        if(max < 0) {
            throw new IllegalArgumentException();
        }

        LetterInventory processedText = new LetterInventory(text);
        List<String> selected = new ArrayList();
        for(String key : dictionary) {
            if(processedText.subtract(processed.get(key)) != null) {
                selected.add(key);
            }
        }
        print(processedText, selected, new ArrayList<String>(), max);
    }

    /*
     * This is a helper method for print method. 
     * @param processedText - the LetterInventory version of string client chooses
     * @param selected - the list of words which are potential components of anagram
     * @param chosen - the list of words contained by string client chooses
     * @param max - the maximum number in the anagrams and it is unlimited
     *              if max is zero.
     */
    private void print(LetterInventory processedText, List<String> selected, 
                       List<String> chosen, int max) {
        if(processedText.size() == 0) {
            System.out.println(chosen);
        }
        else if(max == 0 || chosen.size() < max) {
            for(String key : selected) {
                LetterInventory letters = processedText.subtract(processed.get(key));
                if(letters != null) {
                    // add
                    chosen.add(key);
                    LetterInventory newText = letters;
                    // explore
                    print(newText, selected, chosen, max);
                    // remove
                    chosen.remove(chosen.size() - 1);
                }
            }
        }
    }
}
