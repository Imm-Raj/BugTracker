package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DisplayCompTest {

    private Button button;
    private String name;

    private VBox vb;

    private BorderPane mainPane;

    public DisplayCompTest(String name) {
        button = new Button("" + name);
        button.setOnAction(this::printInfo);
        this.name = name;
        vb = new VBox();
        mainPane = new BorderPane();

        mainPane.setCenter(vb);
        build();
    }

    private void printInfo(ActionEvent actionEvent) {
        System.out.println("This is: " + name);
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public Button getButton() {
        return button;
    }

    public String getName() {
        return name;
    }

    public void build(){
        vb.getChildren().add(button);

    }
}
