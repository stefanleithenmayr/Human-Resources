package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane, searchJobPane;
    @FXML
    private Button searchJob;

    @FXML
    private void switchPane(ActionEvent event) {
        Button button = (Button) event.getSource();
        String name = button.getId();

        mainAnchorPane.getChildren().clear();
        mainAnchorPane.getChildren().add(searchJobPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            searchJobPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SearchJobWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
