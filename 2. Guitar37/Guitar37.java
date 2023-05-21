import java.util.*;

// James Huang
// TA: Mitchell Levy
// This is a guitar with 37 strings and client can play by pressing the keys. 
public class Guitar37 implements Guitar {
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout

    // TODO: The rest of your Guitar37 class here
    private int counts = 0; // the times that we tic the string
    private GuitarString[] concert;


    // Constroctor creating a guitar with 37 strings
    public Guitar37() {
        concert = new GuitarString[37];
        for(int i = 0; i < KEYBOARD.length(); i++) {
            concert[i] = new GuitarString(440.0 * Math.pow(2, (i - 24.0) / 12.0));
        }
    }

    /*
     * Play note
     * @param pitch - the number of note
     */
    public void playNote(int pitch) {
        if(pitch >= -24 && pitch <= 12){
            concert[pitch + 24].pluck();
        }
    }

    /*
     * Test whether guitar has a string
     * @param string - the string client needs to test
     * @return - true if the guitar has this string
     *           false if the guitar does not have this string
     */
    public boolean hasString(char string) {
        return KEYBOARD.indexOf(string) != -1;
    }

    /*
     * Pluck the string 
     * @throws - IllegalArgumentException if the string is outside of the given range
     * @param String - the string which client plucks
     */
    public void pluck(char string) {
        int temp = KEYBOARD.indexOf(string);
        if(temp < 0 || temp >= KEYBOARD.length()){
            throw new IllegalArgumentException();
        }
        concert[temp].pluck();
    }

    // Return the current frequency of each note
    public double sample() {
        double result = 0.0;
        for(int i = 0; i < KEYBOARD.length(); i++){
            result += concert[i].sample();
        }
        return result;
    }

    // Every tic represents an instant and records the times we tic it. 
    public void tic() {
        for(int i = 0; i < KEYBOARD.length(); i++){
            concert[i].tic();
        }
        counts++;
    }

    // Records how long the client plays guitar
    public int time() {
        return counts;
    }
}
