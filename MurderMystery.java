import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class MurderMystery {

    // Default constructor
    public MurderMystery() {
    }

    /**
     * Reads a file
     * @return A list of responses that a given Physicist is assigned.
     */
    public List<String> lineReader(String inFile) {
        List<String> responses = new ArrayList<>();
        File file = new File(inFile);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        // for each line in the file
        while (scanner.hasNextLine()) {
            String response = scanner.nextLine();
            responses.add(response);
        }
        return responses;
    }

    public void displayIntro() {
        List<String> introduction = lineReader("Introduction.txt");
        for (String line: introduction) {
            System.out.print(line);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        MurderMystery murderMysteryGame = new MurderMystery();

        // load each file
        List<String> guiltyResponses = murderMysteryGame.lineReader("Schuldig.txt");
        List<String> innocentResponses1 = murderMysteryGame.lineReader("Unschuldig1.txt");
        List<String> innocentResponses2 = murderMysteryGame.lineReader("Unschuldig2.txt");

        // assign a random physicist to be guilty
        Map<String, Physicist> physicistMap = new HashMap<String, Physicist>();
        List<String> physicistNames = new ArrayList<String>();
        physicistNames.add("Einstein");
        physicistNames.add("Newton");
        physicistNames.add("Möbius");
        Random randomNumberGenerator = new Random();
        int guiltyPhysicistIndex = randomNumberGenerator.nextInt(physicistNames.size());

        // assign the appropriate file to each new Physicist object and delete
        // the name from the list of names once it has been used

        // guilty physicist
        Physicist guiltyPhysicist = new Physicist(physicistNames.get(guiltyPhysicistIndex),
                                                  true, guiltyResponses);
        physicistMap.put(physicistNames.get(guiltyPhysicistIndex), guiltyPhysicist);
        physicistNames.remove(guiltyPhysicistIndex);

        // first innocent physicist
        int innocentPhysicistIndex1 = randomNumberGenerator.nextInt(physicistNames.size());
        Physicist innocentPhysicist1 = new Physicist(physicistNames.get(innocentPhysicistIndex1),
                                                     false, innocentResponses1);
        physicistMap.put(physicistNames.get(innocentPhysicistIndex1), innocentPhysicist1);
        physicistNames.remove(innocentPhysicistIndex1);

        // other innocent physicist
        Physicist innocentPhysicist2 = new Physicist(physicistNames.get(0), false, innocentResponses2);
        physicistMap.put(physicistNames.get(0), innocentPhysicist2);

        // display introductory instructions to user
        murderMysteryGame.displayIntro();

        // create a new game
        int turn = 0;
        int maxTurns = 10;
        boolean gameOver = false;
        Scanner userInput = new Scanner(System.in);
        while (!gameOver) {
            System.out.println();
            // ask the user what they want to do (ask a question or accuse)
            System.out.println("Was möchten Sie machen?");
            System.out.println("Einen Physiker für einen Hinweis fragen" +
            "(drücken Sie 1) oder einen Physiker beschuldigen (drücken Sie 2)");
            String response = userInput.nextLine();

            // if ask for a clue
            if (response.equals("1")) {
                System.out.println();
                System.out.println("Welchen Physiker möchten Sie dafür fragen?" +
                "(Möbius (Moebius), Einstein, Newton)");
                response = userInput.nextLine().toLowerCase();
                if (response.equals("möbius") || response.equals("moebius")) {
                    System.out.println();
                    System.out.println("Möbius: " + 
                    physicistMap.get("Möbius").askForClue());
                    turn++;
                } else if (response.equals("einstein")) {
                    System.out.println();
                    System.out.println("Einstein: " + 
                    physicistMap.get("Einstein").askForClue());
                    turn++;
                } else if (response.equals("newton")) {
                    System.out.println();
                    System.out.println("Newton: " + 
                    physicistMap.get("Newton").askForClue());
                    turn++;
                } else { // invalid input
                    System.out.println("Ungültige Eingabe");
                }

              // if accuse a physicist
            } else if (response.equals("2")) {
                System.out.println();
                System.out.println("Welchen Physiker möchten Sie beschuldigen?" +
                "(Möbius (Moebius), Einstein, Newton)");
                response = userInput.nextLine().toLowerCase();

                // if accusing meobius
                if (response.equals("möbius") || response.equals("moebius")) {
                    if (physicistMap.get("Möbius").accuse()) {
                        System.out.println();
                        System.out.println("Sie gewinnen! Möbius ist der Mörder!");
                        gameOver = true;
                    } else {
                        System.out.println();
                        System.out.println(guiltyPhysicist.getName() +
                        ": Idiot. *Er erdrosselt Sie*");
                        gameOver = true;
                    }
                    turn++;
                  // if accusing einstein
                } else if (response.equals("einstein")) {
                    if (physicistMap.get("Möbius").accuse()) {
                        System.out.println();
                        System.out.println("Sie gewinnen! Einstein ist der Mörder!");
                        gameOver = true;
                    } else {
                        System.out.println();
                        System.out.println(guiltyPhysicist.getName() +
                        ": Idiot. *Er erdrosselt Sie*");
                        gameOver = true;
                    }
                    turn++;
                  // if accusing newton
                } else if (response.equals("newton")) {   
                    if (physicistMap.get("Möbius").accuse()) {
                        System.out.println();
                        System.out.println("Sie gewinnen! Newton ist der Mörder!");
                        gameOver = true;
                    } else {
                        System.out.println();
                        System.out.println(guiltyPhysicist.getName() +
                        ": Idiot. *Er erdrosselt Sie*");
                        gameOver = true;
                    }
                    turn++;
                } else { System.out.println("Ungültige Eingabe"); }
            } else { // invalid input
                System.out.println("Ungültige Eingabe");
            }

            // check if the max number of turns has been reached
            if (turn > maxTurns) {
                System.out.println();
                System.out.println("Sie haben die Grenze der Hinweise erreicht!");
                System.out.println(guiltyPhysicist.getName() +
                                   ": Idiot. *Er erdrosselt Sie*");
                gameOver = true;
            }
        }
    }
}