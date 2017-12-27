package mainPackage;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import loginPackage.DBConnection;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeDataController implements Initializable {
    @FXML
    JFXTextField userName, skill1, skill2, skill3, skill4,realNameField,ageField,ortField,streetField,telefonNumberField,eMailField,descriptionField;
    @FXML
    JFXSlider sliderSkill1,sliderSkill2,sliderSkill3,sliderSkill4;
    @FXML
    JFXButton saveAllButton, addSkillButton, substractSkillButton;
    @FXML
    ImageView imageViewAddSkill;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    JFXCheckBox passwordCheckBox;

    @FXML
    private void substractSkill(ActionEvent event) throws SQLException{
        if (!skill4.getText().isEmpty()){
            skill4.setText("");
            sliderSkill4.setValue(0);
            skill4.setVisible(false);
            sliderSkill4.setVisible(false);
        }
        else if (!skill3.getText().isEmpty()){
            skill3.setText("");
            sliderSkill3.setValue(0);
            skill3.setVisible(false);
            sliderSkill3.setVisible(false);
        }
        else if (!skill2.getText().isEmpty()){
            skill2.setText("");
            sliderSkill2.setValue(0);
            skill2.setVisible(false);
            sliderSkill2.setVisible(false);
        }
    }

    @FXML
    private void SaveAll(ActionEvent event) throws SQLException {
        String update = userName.getText() + "'" + passwordField.getText()+ "'" + realNameField.getText() + "'";
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
        update += updateSkills + "'" + ageField.getText() + "'" + ortField.getText() + "'" + streetField.getText() + "'"
                + telefonNumberField.getText() + "'" + eMailField.getText() + "'" + descriptionField.getText();
        DBConnection.getInstance().UpdateAll(update);
    }
    @FXML
    private void addSkill(ActionEvent event){
        if (skill1.getText().isEmpty()) {
            skill1.setVisible(true);
            sliderSkill1.setVisible(true);
            skill1.setText("YourSkill");
            sliderSkill1.setValue(0);
        }
        else if (skill2.getText().isEmpty()) {
            skill2.setVisible(true);
            sliderSkill2.setVisible(true);
            skill2.setText("YourSkill");
            sliderSkill2.setValue(0);
        }
        else if (skill3.getText().isEmpty()) {
            skill3.setVisible(true);
            sliderSkill3.setVisible(true);
            skill3.setText("YourSkill");
            sliderSkill3.setValue(0);
        }
        else if (skill4.getText().isEmpty()) {
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
        String password = DBConnection.getInstance().password;

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

        if (skills != null && !skills.isEmpty()) {
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
            }
        }
        passwordField.setText(password);

        String realName = "";
        try {
            realName = DBConnection.getInstance().getUserRealName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (realName != null &&!realName.isEmpty()){
            realNameField.setText(realName);
        }

        int age = -1;
        try {
            age = DBConnection.getInstance().getUserAge(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (age > 0){
            ageField.setText(Integer.toString(age));
        }

        String place = "";
        try {
            place = DBConnection.getInstance().getUserPlace(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (place != null && !place.isEmpty()){
            ortField.setText(place);
        }

        String street = "";
        try {
            street = DBConnection.getInstance().getUserStreet(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (street != null && !street.isEmpty()){
            streetField.setText(street);
        }

        String telefonNumber = "";
        try {
            telefonNumber = DBConnection.getInstance().getUserTelefonnumber(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (telefonNumber != null && !telefonNumber.isEmpty()){
            telefonNumberField.setText(telefonNumber);
        }

        String eMail = "";
        try {
            eMail = DBConnection.getInstance().getUserEMail(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (eMail != null && !eMail.isEmpty()){
            eMailField.setText(eMail);
        }
        String description = "";
        try {
            description = DBConnection.getInstance().getUserDescription(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (description != null && !description.equals("")){
            descriptionField.setText(description);
        }
    }
    @FXML
    public void writePassword(ActionEvent event){
        String password = DBConnection.getInstance().password;
        if (passwordCheckBox.isSelected() == true){
            passwordField.clear();
            passwordField.setPromptText(password);
            return;
        }
        passwordField.setText(password);
    }
}
