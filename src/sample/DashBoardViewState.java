package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DashBoardViewState extends CentralViewState{


    public DashBoardViewState(Account enteredAccount) {
        super(enteredAccount, null);
    }

    @Override
    public void build() {

        int j = 0;

//        for (int i = 0; i<25; i++) {
//            Button lab = new Button("Dash");
//            //lab.setPadding(new Insets(10,10,10,10));
//            insideGridPane.getChildren().add(lab);
//
//        }


        BorderPane borderTemp = new BorderPane();



       // insideGridPane.add(borderTemp, 100,100);

        VBox vbox = new VBox();

        borderTemp.setRight(vbox);

//        for (int i = 0; i < 55; i++) {
//            vbox.getChildren().add(new Button("Create     new Project                        "));
//        }

        insideGridPane.add(new Label("Label"), 0, 0);

        insideGridPane.add(new Label("Label"), 1, 0);

        insideGridPane.add(new Label("Label"), 2, 0);

        insideGridPane.add(new Label("Label"), 20, 1);
//

    }


}
