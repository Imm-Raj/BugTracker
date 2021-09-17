package sample;

import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customiser {

    //Instance variable for singleton pattern
    private static Customiser instance;

    private List<Labeled> listOfLabeledNodes;

    private String defaultfont = "FiraGo";
    private int fontSize = 18;

    private Customiser() {
        listOfLabeledNodes = new ArrayList<>();
    }

    /**
     * Returns an instance of Customizer for the
     * singleton pattern
     * @return
     */
    public static Customiser getInstance() {
        if (instance == null) {
            instance = new Customiser();
        }
        return instance;

    }

    /**
     * Customises all the labeled nodes to the default font
     */
    private void customiseAllNodesToDefaultFont(List<Labeled> labeledNodes) {
        if (labeledNodes.size() != 0) {
            for (Labeled node : listOfLabeledNodes) {
                node.setFont(new Font("FiraGo", 18));
            }
        }
    }

    /**
     * Customises single node to default font
     * @param node
     */
    private void customiseSingleLabeledNode(Labeled node) {
        if (node != null) {
            node.setFont(new Font("FiraGo", 18));
        }
    }

    /**
     * Customises a vararg of entered nodes to an entered font and size
     * @param fontName
     * @param fontSize
     * @param nodes
     */
    public void customiseListOfNodesToFont(String fontName, int fontSize, Labeled... nodes) {

        if (nodes.length != 0) {
            for (Labeled node : nodes) {
                node.setFont(new Font(fontName, fontSize));
            }
        }
    }

    /**
     * Customises a list of entered nodes to an entered font and size
     * @param fontName
     * @param fontSize
     * @param nodes
     */
    public void customiseListOfNodesToFont(String fontName, int fontSize, List<Labeled> nodes) {

        if (nodes.size() != 0) {
            for (Labeled node : nodes) {
                node.setFont(new Font(fontName, fontSize));
            }
        }
    }

    /**
     * Adds any number of labeled node to default customisation
     * @param nodes
     */
    public void addLabelledNodeToDefaultCustomisation(Labeled...nodes) {
        if (nodes.length != 0) {

            for (Labeled node : nodes) {
                customiseSingleLabeledNode(node);
                listOfLabeledNodes.add(node);
            }

        }
    }

    public void addTextInputControlNodeToDefaultCustomisation(TextInputControl...nodes) {
        if (nodes.length != 0) {

            for (TextInputControl node : nodes) {
                node.setFont(new Font("FiraGo", 16));
            }


        }
    }

    /**
     * Customises checkBox Nodes to default customisation
     * @param nodes
     */
    public void addCheckBoxNodeToDefaultCustomisation(CheckBox...nodes) {
        if (nodes.length != 0) {

            for (CheckBox node : nodes) {
                node.setFont(new Font("FiraGo", 16));
            }


        }
    }

    /**
     * Adds a list labeled nodes to default customisation
     * @param nodes
     */
    public void addLabelledNodeToDefaultCustomisation(List<Labeled> nodes) {
        if (nodes.size() != 0) {

            for (Labeled node : nodes) {
                customiseSingleLabeledNode(node);
                listOfLabeledNodes.add(node);
            }

        }
    }

    public void addEditableLabeledNodesToDefaultCustomisation(EditableLabel...eLabels) {

        EditableLabelCustomisationVisitor eVisitor = new EditableLabelCustomisationVisitor(defaultfont, fontSize);

        for (EditableLabel eLab : eLabels) {
            eLab.accept(eVisitor);
        }
    }


}
