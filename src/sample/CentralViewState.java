package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public abstract class CentralViewState {

   // protected GridPane gridPane;
    private BorderPane border;

    protected ScrollPane scrollP;

    protected GridPane insideGridPane;

    protected BorderPane mainPane;

    protected Account account;

    protected ErrorDisplay errorDisplay;

    protected Customiser customiser;


    public CentralViewState(Account enteredAccount) {

        if (enteredAccount == null) {
            throw new NullPointerException("user Account is null within the central view" +
                    " State please ensure that it is entered");
        } else {
            account = enteredAccount;
        }

        errorDisplay = new ErrorDisplay();
        customiser = Customiser.getInstance();


      //  gridPane = new GridPane();

        insideGridPane = new GridPane();

        insideGridPane.setVgap(10);
        insideGridPane.setHgap(10);

        insideGridPane.setAlignment(Pos.CENTER);

        mainPane = new BorderPane();


        //insideGridPane.setGridLinesVisible(true);

        scrollP = new ScrollPane(insideGridPane);

        scrollP.setFitToWidth(true);
        scrollP.setFitToHeight(true);


        border = new BorderPane(scrollP);

        border.setMinSize(800,400);
        border.setMaxSize(1200,600);

        scrollP.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        border.setPadding(new Insets(5,5,5,5));

    }

    public BorderPane getBorder() {
        return border;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public Account getAccount() {
        return account;
    }

    public ErrorDisplay getErrorDisplay() {
        return errorDisplay;
    }

    abstract public void build();

    public BorderPane getCentralViewPane() {
        return border;
    }

    public void setGridPane(GridPane enteredGrid) {
        insideGridPane = enteredGrid;
    }


}
