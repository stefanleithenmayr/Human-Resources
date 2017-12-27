package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import loginPackage.DBConnection;
import loginPackage.LoginController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane, searchJobPane, hyrachiePane,changeDatePane, applicationsManagerPane, addJobAdvertismentPane, carPane;
    @FXML
    private Button searchJob, addMitarbeiter, drawHirachieButton, closeButton, overviewButton,addJobButton, editDateButton,karButt;
    @FXML
    private ImageView hyrachieIcon, overviewIcon, addJobIcon, editDateIcon;

    private double xOffset;
    private double yOffset;

    @FXML
    private void switchPane(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        String name = button.getId();
        mainAnchorPane.getChildren().clear();

        if (name.equals("searchJob")){
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SearchJobWindow.fxml")));
        }else if(name.equals("drawHirachieButton")){
            mainAnchorPane.getChildren().add(hyrachiePane);
        }else if(name.equals("editDateButton")){
            mainAnchorPane.getChildren().add(changeDatePane);
        }else if(name.equals("overviewButton")){
            mainAnchorPane.getChildren().add(applicationsManagerPane);
        }else if(name.equals("addJobButton")){
            mainAnchorPane.getChildren().add(addJobAdvertismentPane);
        }else if(name.equals("karButt")){
            mainAnchorPane.getChildren().add(carPane);
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

            if (DBConnection.isBoss){
                drawHirachieButton.setVisible(true);
                hyrachieIcon.setVisible(true);

                overviewButton.setVisible(true);
                overviewIcon.setVisible(true);

                addJobButton.setVisible(true);
                addJobIcon.setVisible(true);
            }else{
                editDateButton.setVisible(true);
                editDateIcon.setVisible(true);
            }
            searchJobPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SearchJobWindow.fxml"));
            hyrachiePane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/DrawHyrachie.fxml"));
            changeDatePane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ChangeData.fxml"));
            applicationsManagerPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ApplicationsManager.fxml"));
            addJobAdvertismentPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AddJobAdvertisment.fxml"));
            carPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/CarrerPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
