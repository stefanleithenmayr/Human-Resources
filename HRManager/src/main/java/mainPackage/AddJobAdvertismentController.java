package mainPackage;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import loginPackage.DBConnection;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddJobAdvertismentController implements Initializable {

    @FXML
    private JFXTextField firstSkillInputName, secondSkillInputName, thirdSkillInputName, quattroSkillInputName, jobNameField;

    @FXML
    private JFXSlider firstSlider, secondSlider, thirdSlider, quattroSlider;
    @FXML
    private JFXTextArea jobdescField;

    private int count;

    @FXML
    private Button addSkillButton;

    @FXML
    private void setSkillVisible(ActionEvent event){
        if (count <= 4){
            count++;
            if (count == 2){
                secondSlider.setVisible(true);
                secondSkillInputName.setVisible(true);
            }else if(count == 3){
                thirdSlider.setVisible(true);
                thirdSkillInputName.setVisible(true);
            }else if(count == 4){
                quattroSlider.setVisible(true);
                quattroSkillInputName.setVisible(true);
            }
        }
    }

    @FXML
    private void removeSkills(ActionEvent event){
        if (count >= 1){
            if (count == 4){
                quattroSkillInputName.setVisible(false);
                quattroSlider.setVisible(false);
            }else if(count == 3){
                thirdSlider.setVisible(false);
                thirdSkillInputName.setVisible(false);
            }else if(count == 2){
                secondSkillInputName.setVisible(false);
                secondSlider.setVisible(false);
            }
            count--;
        }
    }

    @FXML
    private void saveToDatabase(ActionEvent event) throws SQLException {
        String skillString = "";
        skillString += (firstSkillInputName.getText() + "=" + Math.round(firstSlider.getValue()) + ";");

        if (count >= 2){
            skillString += (secondSkillInputName.getText() + "=" + Math.round(secondSlider.getValue()) + ";");
        }
        if(count >= 3){
            skillString += (thirdSkillInputName.getText() + "=" + Math.round(thirdSlider.getValue()) + ";");
        }
        if(count >= 4){
            skillString += (quattroSkillInputName.getText() + "=" + Math.round(quattroSlider.getValue()) + ";");
        }
        DBConnection.getInstance().insertIntoJobs(jobNameField.getText(), jobdescField.getText(), skillString);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        count = 1;
    }
}
