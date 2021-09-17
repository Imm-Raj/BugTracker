package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Statuses {

    private ArrayList<Integer> validStatusValues;

    private ArrayList<String> validStatusWords;

    private HashMap<Integer, String> intToStatusMap;

    public Statuses() {
        intToStatusMap = new HashMap<>();

        validStatusValues = new ArrayList<>();
        validStatusWords = new ArrayList<>();
        populateAllStatuses("Opened", "Beginning Progress", "Middle Progress", "On Hold", "Nearly Complete");
    }


    public int getMaxValueInteger() {
        return intToStatusMap.size() - 1;
    }

    public int getMinValueInteger() {
        return 0;
    }

    public ArrayList<Integer> getValidStatusValues() {
        return validStatusValues;
    }

    public ArrayList<String> getValidStatusWords() {
        return validStatusWords;
    }

    public HashMap<Integer, String> getIntToStatusMap() {
        return intToStatusMap;
    }

    public String getStatusInWordForm(int statusValue) {
        return intToStatusMap.get(statusValue);
    }

    /**
     *
     * @param wordValue
     * @return A status in integer form
     * @throws NullPointerException if the value entered is not found
     */
    public int getStatusInIntegerForm(String wordValue) throws NullPointerException {

        int result = 0;
        boolean found = false;

        for (Map.Entry<Integer,String> entry : intToStatusMap.entrySet()) {
            if (entry.getValue().equals(wordValue)) {
                result = entry.getKey();
                found = true;
            }
        }

        if (!found) {

            throw new NullPointerException("Value not found");

        }

        return result;
    }


    /**
     * A method that populates the hashmap of all statuses
     * in order of input of the varag
     * assosciates each status word with a corresponding value
     * @param statusWord Please enter in order of lowest status to highest status
     */
    private void populateAllStatuses(String...statusWord) {

        if (statusWord.length < 4) {
            throw new IllegalArgumentException("Please make sure that status word" +
                    "\n" + " input is more than 3 words");
        } else {
            int i = 0;

            for (String word : statusWord) {
                validStatusWords.add(word);
                validStatusValues.add(i);
                intToStatusMap.put(i,word);
                i++;
            }
        }

    }

    /**
     * A testing method to print all positions and corresponding values
     */
    public void printStatuses() {
        for (String word : validStatusWords) {
            System.out.println(word);
        }

        System.out.println();

        for (int value : validStatusValues) {
            System.out.println(value);
        }

        System.out.println();

        System.out.println(intToStatusMap.toString());
    }

    /**
     * Validates entered status value
     * to check if it is within the correct range
     * @param status
     * @return
     */
    public boolean validateStatusValue(int status) {

        for (int val : validStatusValues) {
            if (val == status) {
                return true;
            }
        }

        return false;
    }
}
