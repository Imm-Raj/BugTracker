package sample;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

abstract public class Closable extends DisplayComp {

    protected boolean isOpen; //A flag to identify whether the component is closed
                            //Used to tell the user that the component can no longer be accessed

    protected String name; //The name used to identify the display component by search lists - can be changed

    protected List<Labeled> labeledList; //A list of all labeled components e.g Button, Label

    public Closable() {
        labeledList = new ArrayList<>();

    }

    /**
     * Closes the component by setting isOpen flag to false and
     * changes the background colour of the main pane of the component to grey
     */
    public void setClose() {
        isOpen = false;

        String enteredByUser = "abcdef";
        mainPane.setStyle("-fx-background-color: rgba(177,178,187,0.42)#" + enteredByUser);

    }


    /**
     * Opens the component by setting isOpen flag to true and
     * changes the background colour of the main pane of the component to blue
     */
    public void setOpen() {
        isOpen = true;

        String enteredByUser = "abcdef";
        mainPane.setStyle("-fx-background-color: rgba(171,205,239,0.42)#" + enteredByUser);

    }

    public String getName() {
        return name;
    }

    /**
     * A method that takes in varag Labeled components such as Buttons, Labels etc
     * and adds them all to a list of labeled componentes stored withinin the closable object
     * @param lab
     */
    protected void addAllLabeledNodestoLabeledList(Labeled...lab) {
        for (Labeled l : lab) {
            labeledList.add(l);
        }
    }

    /**
     * Can update all the Labels components to any font which the
     * implementor chooses
     */
    protected void updateFontOfLabeledNodes(String chosenFont, int size) {
        for (Labeled l : labeledList) {
            l.setFont(new Font(chosenFont,size));
        }
    }


}
