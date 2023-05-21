import java.util.*;

// James Huang
// TA: Mitchell Levy
// GuitarString simulates the real guitar string and could be
// plucked and ticed to making sound. 
public class GuitarString {
    // TODO: Your Code Here
    public static final double ENERGY_DECAY_FACTOR = 0.996;

    private Queue<Double> buffer;

    /* 
     * Constructor creating one string
     * @throws - IllegalArgumentException if the frequency is less than 0
     *           or the size of buffer calculated by frequency is less than 2
     * @param frequency - helps to create the size of buffer
     */
    public GuitarString(double frequency) {
        // checks whether the frequency is illegal or not
        if(frequency <= 0) {
            throw new IllegalArgumentException();
        }
        int N = (int)(Math.round(StdAudio.SAMPLE_RATE / frequency));
        // chech whether the size of ring buffer is illegal or not
        if(N < 2) {
            throw new IllegalArgumentException();
        }
        buffer = new LinkedList<Double>();
        for(int i = 0; i < N; i++) {
            buffer.add(0.0);
        }
    }

    /* 
     * Constroctor creating many strings
     * @throws - IllegalArgumentException if the legnth of parameter is less than 2
     * @param init - is the list of frequency used to create string
     */
    public GuitarString(double[] init) {
        if(init.length < 2){
            throw new IllegalArgumentException();
        }
        buffer = new LinkedList<Double>();
        for(int i = 0; i < init.length; i++) {
            buffer.add(init[i]);
        }
    }

    // Fill in ring buffer
    public void pluck() {
        for(int i = 0; i < buffer.size(); i++) {
            buffer.remove();
            buffer.add(Math.random() - 0.5);
        }
    }

    // Update ring buffer as the time increases
    public void tic() {
        buffer.add((buffer.remove() + buffer.peek()) * ENERGY_DECAY_FACTOR / 2);
    }

    // Play the sound of this instant 
    public double sample() {
        return buffer.peek();
    }
}
