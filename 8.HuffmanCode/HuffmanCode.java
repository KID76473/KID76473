import java.util.*;
import java.io.*;

// James Huang
// TA: Mitchell Levy
// HuffmanCode class is used to compress a file and decompress a file by creating a 
// dictionary called huffman code. Each letter has a shorter version in huffman code, 
// which consumes less space to store it. 
public class HuffmanCode {
    // TODO: Your Code Here
    private HuffmanNode overallRoot;

    /*
     * Consturctor with an array
     * @param - an array representing frequencies of each word. The indices
     * of array are ASCII values of coressponding letters and its values are
     * frequencies of letters.
     * This method creates a tree structure to store the Huffman code for each letter. 
     */
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
        for (int i = 0; i < frequencies.length; i++) {
            if(frequencies[i] > 0) {
                HuffmanNode node = new HuffmanNode(i, frequencies[i]);
                pq.add(node);
            }
        }
        while (pq.size() > 1) {
            HuffmanNode first = pq.remove();
            HuffmanNode second = pq.remove();
            HuffmanNode node = new HuffmanNode(-1, first.freq + second.freq, first, second);
            pq.add(node);
        }
        overallRoot = pq.remove();
    }

    /*
     * Consturctor using an existing tree from a file created by save method
     * @param - a scanner of a file, which is in standard format
     */
    public HuffmanCode(Scanner input) {
        while (input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = help(input, overallRoot, asciiValue, code);
        }
    }

    /*
     * This is a helper method to construct tree structure. 
     * @param input - a scanner of a file, which is in pre-order/standard format
     *        root - the reference of tree structure
     *        letter - the ASCII value of letter
     *        path - a string consisting of 0 and 1 to indicate how to reach
     *               the leaf
     */
    private HuffmanNode help(Scanner input, HuffmanNode root, int letter, String path) {
        if (path.equals("")) {
            root = new HuffmanNode(letter, 0);
        }
        else {
            if (root == null) {
                root = new HuffmanNode(-1, 0);
            }
            if (path.substring(0, 1).equals("0")) {
                root.left = help(input, root.left, letter, path.substring(1));
            }
            else {
                root.right = help(input, root.right, letter, path.substring(1));
            }
        }
        return root;
    }

    /*
     * Convert Huffman code in tree structure to a file in pre-order/standard format
     * @param - a printer of the output file
     */
    public void save(PrintStream output) {
        save(output, overallRoot, "");
    }

    /*
     * This is a helper method to write the file. 
     * @param output - a printer of the output file
     *        root - the reference of huffman tree
     *        code - the path to reach its leaf
     */
    private void save(PrintStream output, HuffmanNode root, String code) {
        if (!isLeaf(root)) {
            save(output, root.left, code + "0");
            save(output, root.right, code + "1");
        }
        else {
            output.println(root.letter);
            output.println(code);
        }
    }

    /*
     * Decompress a file consisting of Huffman codes to a readable,by human, file
     * @param input - a scanner of compressed file
     *        output - a printer of output file
     */
    public void translate(BitInputStream input, PrintStream output) {
        translate(input, output, overallRoot);
    }

    /*
     * This is a helper method to help decompress
     * @param input - a scanner of compressed file
     *        output - a printer of output file
     *        root - the reference of huffman tree
     */
    private void translate(BitInputStream input, PrintStream output, HuffmanNode root) {
        while (input.hasNextBit()) {
            if (input.nextBit() == 0) {
                root = root.left;
            }
            else {
                root = root.right;
            }
            if (isLeaf(root)) {
                    output.write(root.letter);
                    root = overallRoot;
            }
        }
    }

    /* 
     * This is a helper method to judge a root is a leaf or not.
     * @param - tested root
     * @return - true if it is a leaf node
     *         - false if it is not a leaf node
     */
    private boolean isLeaf(HuffmanNode root) {
        return root != null && root.left == null && root.right == null;
    }

    // This is an inner class uesd to create a tree structure containing
    // nodes. This class implements Comparable interface.
    private static class HuffmanNode implements Comparable<HuffmanNode>{
        public int letter;
        public int freq;
        public HuffmanNode left;
        public HuffmanNode right;

        // Constructor with letter and its frequency only
        // @param letter - ASCII value of the letter
        //        freq - frequency of the letter appearing
        public HuffmanNode(int letter, int freq) {
            this(letter, freq, null, null);
        }

        // Constructor with letter, its frequency, left child, and right child
        // @param letter - ASCII value of the letter
        //        freq - frequency of the letter appearing
        //        left - reference of left leaf
        //        right - reference of right leaf
        public HuffmanNode(int letter, int freq, HuffmanNode left, HuffmanNode right) {
            this.letter = letter;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        // Compare two HuffmanNodes with their frequencies
        // @param - the other HuffmanNode comparing to this node
        // @return - 1 if this node is greater than the other
        //           -1 if this node is less than the other
        public int compareTo(HuffmanNode other) {
            return this.freq - other.freq;
        }
    }
}
