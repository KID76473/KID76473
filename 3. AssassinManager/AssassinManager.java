import java.util.*;

// James Huang
// TA: Mitchell Levy
// This class is used to record the assassin relationship between the players,
// manipulate the process of the game, and announce the winner.
 
public class AssassinManager {
    // TODO: Your Code Here 
    private AssassinNode killRing; // alive people
    private AssassinNode graveyard; // dead people

    /*
     * Constructor with parameter
     * @throws - IllegalArgumentException if the name list is isEmpty
     * @param - a list of all of the people in the game
     * This sonstructor initialize a new assassin ring and use methods below to record the assassin game
     */
    public AssassinManager(List<String> name) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for(int i = name.size()-1; i >= 0; i--) {
            killRing = new AssassinNode(name.get(i), killRing);
        }
    }

    // Print the names of all the people still in killing ring if the game is not over
    // Print the winner is stalking the winner when the game is over
    public void printKillRing() {
        AssassinNode current = killRing;
        while(current.next != null) {
            System.out.println("    " + current.name + " is stalking " + current.next.name);
            current = current.next;
        }
        // if there are many people, print the last is stalking the first
        // if there is only one person, print the person is stalking the person
        System.out.println("    " + current.name + " is stalking " + killRing.name);
    }

    // Print the names of all the dead people
    // Do nothing if there is no one dead
    public void printGraveyard() {
        AssassinNode current = graveyard;
        while(current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    /*
     * Judge the person is alive or not
     * @param - the name of the person
     * @return - true if the person is alive
     *           false if the person is dead or not in this game
     * This is a case-insensitive method. Hunter and hUNtEr are same. 
     */
    public boolean killRingContains(String name) {
        return find(name, killRing);
    }

    /*
     * Judge the person is dead or not
     * @param - the name of the person
     * @return - true if the person is killed
     *           false if the person is alive or not in this game
     * This is a case-insensitive method.
     */
    public boolean graveyardContains(String name) {
        return find(name, graveyard);
    }

    /*
     * A helper method used to find the person in the killRing or graveyard
     * @param name - the name of the person
     * @param node - the domain in which the method find
     * @return - true if the person is in the domain
     *           false if the person is not in the domain
     * This is a case-insensitive method.
     */
    private boolean find(String name, AssassinNode node) {
        AssassinNode current = node;
        while(current != null) {
            if(name.equalsIgnoreCase(current.name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /*
     * Judge the game is over or not
     * @return - true if the game cannot continue 
     *           false if there are still people in the ring
     */
    public boolean gameOver() {
        return killRing.next == null;
    }

    /*
     * Announce the winner
     * @return - the name of winner
     *           null if the game is not over
     */
    public String winner() {
        if(!gameOver()) {
            return null;
        }
        return killRing.name;
    }

    /*
     * Kill the person
     * @throws - IllegalStateException if the game is over
     *           IllegalArgumentException if the person is not in the game or dead
     *           IllegalStateException is prior to IllegalArgumentException. 
     * @param - the person this whom this method will kill
     * This method also records the killer and assigns next object for the killer. 
     * This is a case-insensitive method.
     */
    public void kill(String name) {
        if(gameOver()) {
            throw new IllegalStateException();
        }
        if(!killRingContains(name)) {
            throw new IllegalArgumentException();
        }

        AssassinNode current = killRing;
        AssassinNode dead = null;
        // when the first person is killed
        if(current.name.equalsIgnoreCase(name)) {
            dead = killRing;
            while(current.next != null) {
                current = current.next;
            }
            dead.killer = current.name;
            killRing = killRing.next;
        }
        // when the following people are killed 
        else{
            while(current != null && current.next != null) {
                if(current.next.name.equalsIgnoreCase(name)) {
                    dead = current.next;
                    dead.killer = current.name;
                  current.next = current.next.next;
                }
                current = current.next;
            }
        }
        AssassinNode temp = graveyard;
        dead.next = graveyard;
        graveyard = dead;
    }
}
