import java.util.*;
import java.io.*;

// James Huang
// TA: Mitchell Levy
// This class is used to play a game of 20 questions. The object of this class can
// read a file and create a tree consisting of questions and answers. Then, the 
// object of this class can play 20 questions with client, and winner will appear
// between computer and client. Additionally, every new word will be updated by computer. 
public class QuestionsGame {
    // TODO: Your Code Here
    private QuestionNode overallRoot;
    private Scanner console;

    // Construct a new object of QuestionGame with an original word "computer"
    public QuestionsGame() {
        overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    // Read a file and construct or create a tree to replace current tree
    // @param - a file reader
    // The input file must be pre-order/standard format. 
    public void read(Scanner input) {
        overallRoot = helpRead(input);
    }

    /* 
     * This is a helper method to read a file and construct a tree.
     * @param - a file reader
     * @return - reference of tree consisting of questions and answers
     */
    private QuestionNode helpRead(Scanner input) {
            String first = input.nextLine();
            String second = input.nextLine();
            QuestionNode root = new QuestionNode(second);
            if(first.equals("Q:")) {
                root.left = helpRead(input);
                root.right = helpRead(input);
            }
            return root;
    }

    // Write the tree in Q&A and pre-order/standard format to a file
    // @param - a file writer
    public void write(PrintStream output) {
        write(output, overallRoot);
    }

    // This is a helper method to write the tree to a file.
    // @param - a file writer
    private void write(PrintStream output, QuestionNode root) {
        if(!isLeaf(root)) {
            output.println("Q:");
            output.println(root.text);
            write(output, root.left);
            write(output, root.right);
        }
        else{
            output.println("A:");
            output.println(root.text);
        }
    }

    // Guess the word and test it is correct or not and tell who is the 
    // winner. If the client wins, computer updates its tree.
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }

    /* 
     * This is a helper method to ask questions and update the tree.
     * @param - reference of tree consisting questions and answers
     * @return - a new tree containing client's word
     */
    private QuestionNode askQuestions(QuestionNode root) {
        if(isLeaf(root)) {
            if(yesTo("Would your object happen to be " + root.text + "?")) {
                System.out.println("Great, I got it right!");
            }
            else{
                System.out.print("What is the name of your object? ");
                String text = console.nextLine();
                QuestionNode answer= new QuestionNode(text);
                System.out.print("Please give me a yes/no question that\ndistinguishes between your object\nand mine--> ");
                String question = console.nextLine();
                if(yesTo("And what is the answer for your object?")) {
                    root = new QuestionNode(question, answer, root);
                }
                else{
                    root = new QuestionNode(question, root, answer);
                }
            }
        }
        else if(yesTo(root.text)) {
            root.left = askQuestions(root.left);
        }
        else{
            root.right = askQuestions(root.right);
        }
        return root;
    }
    
    /* 
     * This is a helper method to judge a root is a leaf or not.
     * @param - tested root
     * @return - true if it is a leaf node
     *         - false if it is not a leaf node
     */
    private boolean isLeaf(QuestionNode root) {
        return root != null && root.left == null && root.right == null;
    }

    // This is a inner class uesd to create a tree structure containing
    // questions and answers.
    private static class QuestionNode {
        public String text;
        public QuestionNode left;
        public QuestionNode right;

        // Constructor with text only
        // @param - text of this node
        public QuestionNode(String s){
            this(s, null, null);
        }
        
        // Constructor with text, left leaf, and right leaf
        // @param s - data of the node
        //        left - reference of left leaf
        //        right - reference of right leaf
        public QuestionNode(String s, QuestionNode left, QuestionNode right){
            this.text = s;
            this.left = left;
            this.right = right;
        }
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    private boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
