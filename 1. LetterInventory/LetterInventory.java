// James Huang
// TA: Mitchell Levy
// LetterInventory counts the occerence of each letter
// in alphabet and it is a case-insensitive class.
public class LetterInventory {
    // TODO: Your Code Here
    public static final int CAPACITY = 26;
    private int[] counts;
    private int total;

    // Constructor without parameter
    // Create a new object whose class is LetterInventory
    // Use another constructor with parameter to reduce redundancy
    public LetterInventory() {
        this("");
    }

    /* 
     * Constructor with a parameter
     * @param data - the string that we count the occerence of letters
     * LetterInventory is a case-insensitive class and ignores 
     * all of the elements out of the alphabet.
     */
    public LetterInventory(String data) {
        counts = new int[CAPACITY];
        data = data.toLowerCase();
        for (int i = 0; i < data.length(); i++) {
            if(Character.isLetter(data.charAt(i))){
                counts[(int)(data.charAt(i)) - 'a']++;
                total++;
            }
        } 
    }

    /*
     * Get the occerence of a specific letter
     * @throws -  IllegalArgumentException if the parameter is not in alphabet
     * @param letter- the letter we want to check
     * @return - the occerence of the letter
     * This method is case-insensitive
     */
    public int get(char letter){
        if (!Character.isLetter(letter)){
            throw new IllegalArgumentException();
        }
        letter = Character.toLowerCase(letter);
        return counts[(int)letter - 'a'];
    }

    /* 
     * Change the occerence of a specific letter to a given value
     * @throws - IllegalArgumentException if the letter is not in alphabet
     *           or the value is less than 0
     * @param letter - the letter this method changes
     * @param value - the value this method changes to
     * This method is case-insensitive. 
     */
    public void set(char letter, int value){
        if (!Character.isLetter(letter) || value < 0){
            throw new IllegalArgumentException();
        }
        letter = Character.toLowerCase(letter);
        total += (value - counts[(int)letter - 'a']);
        counts[(int)letter - 'a'] = value;
        
    }

    /* 
     * Get the amount of letters in this LetterInventory
     * @return - the total amount of letters in LetterInventory
     */
    public int size(){
        return total;
    }

    /*
     * Check this LetterInventory is empty or not
     * @return - true if there is not letter in this LetterInventory and
     *           false if there is any letter
     */
    public boolean isEmpty(){
        return total == 0;
    }

    /*
     * Called when the LetterInventory is printed
     * @return - print all of the letters in alphabetic order
     */
    public String toString(){
        if (this.isEmpty()) {
            return "[]";
        }
        String result = "[";
        for(int i = 0; i < CAPACITY; i++){
            for(int j = 0; j < counts[i]; j++){
                result += (char)(i + 'a');
            }
        }
        result += "]";
        return result;
    }

    /* 
     * Combine all of the letters of this LetterInventorys and other LetterInventory
     * @param other - a LetterInventory is added to this LetterInventory which
     *                will not be changed after this method is called
     * @return - a new LetterInventory combining two LetterInventorys
     */
    public LetterInventory add(LetterInventory other){
        LetterInventory result = new LetterInventory();
        for(int i = 0; i < CAPACITY; i++){
            result.set((char)(i + 'a'), 
            this.counts[i] + other.get((char)(i + 'a')));
        }
        return result;
    }

    /*
     * Take away the same portion of one LetterInventory from this LetterInventory
     * @param other - a LetterInventory subtracting from this LetterInventory which 
     *                will not be changed after this method is called
     * @return - a new LetterInventory contained by this LetterInventory, 
     *           and there is not same letter with parameter other
     * If any value of a letter is less than 0, this method will return null. 
     */
    public LetterInventory subtract(LetterInventory other){
        LetterInventory result = new LetterInventory();
        for(int i = 0; i < CAPACITY; i++){
            int value = this.counts[i] - other.get((char)(i + 'a'));
            if(value < 0) {
                return null;
            }
            result.set((char)(i + 'a'), value);
        }
        return result;
    }
}
