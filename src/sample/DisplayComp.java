package sample;

import javafx.scene.layout.BorderPane;

public abstract class DisplayComp {

    protected BorderPane mainPane;



    public DisplayComp() {

        mainPane = new BorderPane();


    }

    public BorderPane getMainPane() {
        return mainPane;
    }






}
