package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import loginPackage.LoginController;

import javax.xml.bind.annotation.XmlList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane, searchJobPane, hyrachiePane,addEmployeePane;
    @FXML
    private Button searchJob, addMitarbeiter, drawHirachieButton, closeButton;

    private double xOffset;
    private double yOffset;

    @FXML
    private void switchPane(ActionEvent event) {
        Button button = (Button) event.getSource();
        String name = button.getId();

        mainAnchorPane.getChildren().clear();

        if (name.equals("searchJob")){
            mainAnchorPane.getChildren().add(searchJobPane);
        }else if(name.equals("drawHirachieButton")){
            mainAnchorPane.getChildren().add(hyrachiePane);
        }else if(name.equals("drawHirachieButton")){
            mainAnchorPane.getChildren().add(addEmployeePane);
        }
    }

    @FXML
    private void closePane(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void mouseIsPressedEvent(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void mouseIsDraggedEvent(MouseEvent event) {
        LoginController.mainStage.setX(event.getScreenX() - xOffset);
        LoginController.mainStage.setY(event.getScreenY() - yOffset);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            searchJobPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SearchJobWindow.fxml"));
            hyrachiePane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/DrawHyrachie.fxml"));
            addEmployeePane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/addEmployee.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
