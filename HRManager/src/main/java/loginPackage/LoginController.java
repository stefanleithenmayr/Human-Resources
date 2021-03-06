package loginPackage;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.soap.Text;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private JFXTextField userNameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField falseInputField;

    public static Stage mainStage;

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loginAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
        boolean loginSuccessful;
        //loginSuccessful = DBConnection.getInstance().login("boss", "12345");
        loginSuccessful = DBConnection.getInstance().login(userNameField.getText(), passwordField.getText());
        if (!loginSuccessful){
            falseInputField.setVisible(true);
            return;
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        Parent mainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainScene.fxml"));
        Scene mainScene = new Scene(mainRoot);
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.TRANSPARENT);

        newStage.setScene(mainScene);
        newStage.setResizable(false);
        mainScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        mainStage = newStage;
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
