import java.util.*;

// James Huang
// TA: Mitchell Levy
// GrammarSolver is a random sentences generator that can generate given numbers of 
// sentences, which are composed of the given grammar. 
public class GrammarSolver {
    // TODO: Your Code Here
    private SortedMap<String, List<String[]>> grammar;

    /* 
     * Create a new generator that generates sentences complying a given rules
     * and selecting words in a given range
     * @throws - IllegalArgumentException if the given range is empty
     *           or there are repeated entries for the same type
     * @param rules - the grammar complied by generator
     */
    public GrammarSolver(List<String> rules) {
        if(rules.isEmpty()) {
            throw new IllegalArgumentException();
        }

        grammar = new TreeMap<String, List<String[]>>();
        for(String rule : rules) { // each line of rules
            String[] splitwords = rule.trim().split("::=");
            String nonTerminal = splitwords[0];
            if(grammar.containsKey(nonTerminal)) {
                throw new IllegalArgumentException();
            }
            List<String[]> values = new ArrayList<String[]>(); 
            String[] words = splitwords[1].split("\\|");
            for(String word : words) {
                values.add(word.trim().split("\\s+"));
            }
            grammar.put(nonTerminal, values);
        }
    }

    /* 
     * Check the type is contained in the range or not
     * @param symbol - the type client wants to check
     * @return true - there is the type
     *         false - the type does not exist
     */
    public boolean grammarContains(String symbol) {
        return grammar.containsKey(symbol);
    }

    // Get the all the types in the range
    public String getSymbols() {
        return grammar.keySet().toString();
    }

    /* 
     * Generate sentences, with given numbers, complying the given type
     * @throws - IllegalArgumentException if the number is less than zero
     *                                    if the type does not exist
     * @param symbol - the type that client wants to generate
     * @param times - the number of sentences the client wants to generate
     * @return - the array of strings containing all of the sentences
     */
    public String[] generate(String symbol, int times) {
        if(times < 0 || !grammar.containsKey(symbol)) {
            throw new IllegalArgumentException();
        }

        String[] result = new String[times];
        // how many sentences the client wants to generate
        for(int i = 0; i < times; i++) {
            result[i] = print(symbol);
        }
        return result;
    }

    /* 
     * Helper method that generates each sentence
     * @param symbol - the type that client wants to generate
     * @return - each sentence containing all of the types
     */
    private String print(String symbol) {
        String result = "";
        String current = symbol;
        // the basic case
        // current reaches terminal
        if(!grammar.containsKey(symbol)) {
            return symbol;
        }
        // recursive case
        // continue to find if current has not reached the terminal
        int rand = (int) (Math.random() * grammar.get(symbol).size());
        for(int i = 0; i < grammar.get(symbol).get(rand).length; i++) {
            current = grammar.get(symbol).get(rand)[i];
            result += print(current) + " ";
        }
        return result.trim();
    }
}
