package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchableList extends DisplayComp {

    private List<Closable> compList;

    private ListView<BorderPane> listView;

    private TextField searchBar;

    private VBox mainBox;


    public SearchableList(List<Closable> compList, String name, int prefWidth, int prefHeight) {

        listView = new ListView();

        listView.setPadding(new Insets(10,10,10,10));

        listView.setMinWidth(prefWidth);
        listView.setMaxWidth(prefWidth);

        listView.setMinHeight(prefHeight);
        listView.setMaxHeight(prefHeight);

        searchBar = new TextField();

        searchBar.setPromptText("Search " + name);

        searchBar.setOnKeyTyped(e -> search());

        mainBox = new VBox();

        this.compList = compList;

        build();

        initialize();

    }


    /**
     *
     * @return the Main VBox
     */
    public BorderPane getMainPane() {
        return mainPane;
    }

    /**
     * A method that builds the whole list component
     * by piecing together the VBoxes and HBoxes
     */
    private void build() {

        HBox hb = new HBox();
        hb.setPadding(new Insets(10,10,10,10));
        hb.getChildren().add(searchBar);

        mainBox.getChildren().add(hb);
        mainBox.getChildren().add(listView);

        listView.setMaxHeight(1000);

        mainBox.setMaxHeight(1000);

        mainPane.setCenter(mainBox);
    }


    /**
     * Initializes the listView
     * clears and then repopulates the list with only
     * the main Pane associated with each display component
     */
    public void initialize() {

        clearListView();

        listView.getItems().addAll(retrieveMainePanesListFromCompList(compList));
    }

    /**
     * This method clears the listView and performs the search
     * After search is performed, the search results are repopulated
     * into the listView
     */
    public void search() {

        clearListView();
        listView.getItems().addAll(retrieveMainePanesListFromCompList(searchList(searchBar.getText(), compList)));
    }

    /**
     * A method that clears the list View as long as it is empty
     */
    private void clearListView() {
        if (!listView.getItems().isEmpty()) {
            listView.getItems().clear();
        }
    }

    /**
     * Retrieves the main pain associated with each display
     * component calls the getMainPane() method
     * @param compList
     * @return List<BorderPane>
     */
    private List<BorderPane> retrieveMainePanesListFromCompList(List<Closable> compList) {

        ArrayList<BorderPane> paneList = new ArrayList<>();

        for (Closable c:  compList) {
            paneList.add(c.getMainPane());
        }

        return paneList;
    }

    /**
     * An auxiliary method where the actual searching operation takes place
     * can utilise any field of the displayComponent as long as it is a string
     * returns a list of components whose fields have the closest
     * match to the entered search words
     * @param searchWords
     * @param listOfComps
     * @return List<ClosableTest> list of display components
     */
    private List<Closable> searchList(String searchWords, List<Closable> listOfComps) {

        List<String> searchWordsList = Arrays.asList(searchWords.toLowerCase().trim().split(" "));

        return listOfComps.stream().filter(input -> {
            return searchWordsList.stream().allMatch(word ->
                    input.getName().toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());

    }


}
