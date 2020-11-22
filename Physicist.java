import java.util.List;
import java.util.Random;

/**
 * Class for the three Physicists represented in the MurderMystery game.
 * Supports operations for asking a Physicist for a clue, or for accusing
 * a Physicist of having committed the crime.
 */
public class Physicist {
    private String name;
    private boolean guilty; // true if they are the murderer, false if not
    private List<String> responses; // potential responses, read in by file

    public Physicist(String name, boolean guilty, List<String> responses) {
        this.name = name;
        this.guilty = guilty;
        this.responses = responses;
    }

    /**
     * Asks a Physicist for a clue from their list of possible clue responses.
     * @return A pseudo-random response from the possible remaining responses.
     */
    public String askForClue() {
        Random randomNumberGenerator = new Random();
        int randomInt;
        // if the list of responses is empty, return a generic phrase
        if (responses.isEmpty()) {
            return "Ich habe die Nase voll. Ich beantworte keine weitere Fragen mehr.";
        } else {
            // get a random response, then remove it from the future response options
            randomInt = randomNumberGenerator.nextInt(responses.size());
            String randomResponse = responses.get(randomInt);
            responses.remove(randomInt);
            return randomResponse;
        }
    }

    /**
     * @return True if the Physicist is guilty, false if not.
     */
    public boolean accuse() {
        if (this.guilty == true) { return true; }
        else { return false; }
    }

    public String getName() {
        return this.name;
    }
}