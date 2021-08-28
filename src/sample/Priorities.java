package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class Priorities {


    private ArrayList<Integer> validPriorityValues;

    private ArrayList<String> validPriorityWords;

    private HashMap<Integer, String> intToPriorityMap;

    public Priorities() {
        intToPriorityMap = new HashMap<>();

        validPriorityValues = new ArrayList<>();
        validPriorityWords = new ArrayList<>();
        populateAllPriorities("Extremely Low", "Low", "Medium", "High", "Extremely High");
    }

    public ArrayList<Integer> getValidPriorityValues() {
        return validPriorityValues;
    }

    public ArrayList<String> getValidPriorityWords() {
        return validPriorityWords;
    }

    public HashMap<Integer, String> getIntToPriorityMap() {
        return intToPriorityMap;
    }

    public String getPriorityInWordForm(int priorityValue) {
       return intToPriorityMap.get(priorityValue);
    }


    /**
     * A method that populates the hashmap of all priorities
     * in order of input of the varag
     * assosciates each priority word with a corresponding value
     * @param priorityWord Please enter in order of lowest priority to highest priority
     */
    private void populateAllPriorities(String...priorityWord) {

        if (priorityWord.length < 3) {
            throw new IllegalArgumentException("Please make sure that priority word" +
                    "\n" + " input is more than 2 words");
        } else {
            int i = 0;

            for (String word : priorityWord) {
                validPriorityWords.add(word);
                validPriorityValues.add(i);
                intToPriorityMap.put(i,word);
                i++;
            }
        }

    }

    /**
     * A testing method to print all positions and corresponding values
     */
    public void printPriorities() {
        for (String word : validPriorityWords) {
            System.out.println(word);
        }

        System.out.println();

        for (int value : validPriorityValues) {
            System.out.println(value);
        }

        System.out.println();

        System.out.println(intToPriorityMap.toString());
    }

    /**
     * Validates entered priority value
     * to check if it is within the correct range
     * @param priority
     * @return
     */
    public boolean validatePriorityValue(int priority) {

        for (int val : validPriorityValues) {
            if (val == priority) {
                return true;
            }
        }

        return false;
    }
}
