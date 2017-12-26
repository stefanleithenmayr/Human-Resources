package mainPackage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import loginPackage.DBConnection;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeDataController implements Initializable {
    @FXML
    JFXTextField userName, skill1, skill2, skill3, skill4;
    @FXML
    JFXSlider sliderSkill1,sliderSkill2,sliderSkill3,sliderSkill4;
    @FXML
    JFXButton saveAllButton, addSkillButton;
    @FXML
    ImageView imageViewAddSkill;
    @FXML
    private void SaveAll(ActionEvent event) throws SQLException {
        String updateSkills = "";
        if (!skill1.getText().equals("")){
            updateSkills += skill1.getText()+"="+sliderSkill1.getValue()+";";
        }
        if (!skill2.getText().equals("")){
            updateSkills += skill2.getText()+"="+sliderSkill2.getValue()+";";
        }
        if (!skill3.getText().equals("")){
            updateSkills += skill3.getText()+"="+sliderSkill3.getValue()+";";
        }
        if (!skill4.getText().equals("")){
            updateSkills += skill4.getText()+"="+sliderSkill4.getValue()+";";
        }
        try {
            DBConnection.getInstance().UpdateSkills(updateSkills);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(updateSkills+"\n");
        String userName1 = userName.getText();
        System.out.println(userName1);
        DBConnection.getInstance().UpdateUserName(userName1);
    }
    @FXML
    private void addSkill(ActionEvent event){
        String name = DBConnection.getInstance().userName;
        String skills = "";
        try {
            skills = DBConnection.getInstance().getUserSkills(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] lines = skills.split(";");
        if (skill1.getText().equals("")) {
            skill1.setVisible(true);
            sliderSkill1.setVisible(true);
            skill1.setText("YourSkill");
            sliderSkill1.setValue(0);
        }
        else if (skill2.getText().equals("")) {
            skill2.setVisible(true);
            sliderSkill2.setVisible(true);
            skill2.setText("YourSkill");
            sliderSkill2.setValue(0);
        }
        else if (skill3.getText().equals("")) {
            skill3.setVisible(true);
            sliderSkill3.setVisible(true);
            skill3.setText("YourSkill");
            sliderSkill3.setValue(0);
        }
        else if (skill4.getText().equals("")) {
            skill4.setVisible(true);
            sliderSkill4.setVisible(true);
            skill4.setText("YourSkill");
            sliderSkill4.setValue(0);
            addSkillButton.setVisible(false);
            imageViewAddSkill.setVisible(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String name = DBConnection.getInstance().userName;
        userName.setText(name);
        skill1.setVisible(false);
        skill2.setVisible(false);
        skill3.setVisible(false);
        skill4.setVisible(false);
        sliderSkill1.setVisible(false);
        sliderSkill2.setVisible(false);
        sliderSkill3.setVisible(false);
        sliderSkill4.setVisible(false);
        String skills = "";
        try {
            skills = DBConnection.getInstance().getUserSkills(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (skills != null) {
            String[] lines = skills.split(";");

            for (int i = 0; i < lines.length && i < 4 && !skills.equals("<null>"); i++) {
                String[] line = lines[i].split("=");
                if (i == 0) {
                    skill1.setVisible(true);
                    sliderSkill1.setVisible(true);
                    skill1.setText(line[0]);
                    sliderSkill1.setValue(Double.parseDouble(line[1]));
                } else if (i == 1) {
                    skill2.setVisible(true);
                    sliderSkill2.setVisible(true);
                    skill2.setText(line[0]);
                    sliderSkill2.setValue(Double.parseDouble(line[1]));
                } else if (i == 2) {
                    skill3.setVisible(true);
                    sliderSkill3.setVisible(true);
                    skill3.setText(line[0]);
                    sliderSkill3.setValue(Double.parseDouble(line[1]));
                } else if (i == 3) {
                    skill4.setVisible(true);
                    sliderSkill4.setVisible(true);
                    skill4.setText(line[0]);
                    sliderSkill4.setValue(Double.parseDouble(line[1]));
                }
                System.out.println(lines[i]);
            }
        }
    }
}
