package mainPackage;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import loginPackage.DBConnection;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchJobController implements Initializable {

    @FXML
    private JFXListView outputForJobs;
    @FXML
    private JFXTextField searchElement;
    @FXML
    private ProgressIndicator skillIndi1, skillIndi2, skillIndi3, skillIndi4;
    @FXML
    private Text skilllsText, skillText1, skillText2, skillText3, skillText4, headLineText, descreptionText, descreption;
    @FXML
    private Button bewerbenButton;

    @FXML
    private void printText(MouseEvent event) throws SQLException {
        String job = (String)outputForJobs.getSelectionModel().getSelectedItem();
        String jobDesc = DBConnection.getInstance().getJobDesc(job);
        String jobSkills = DBConnection.getInstance().getJobSkills(job);

        skillText1.setVisible(false);
        skillText2.setVisible(false);
        skillText3.setVisible(false);
        skillText4.setVisible(false);

        skillIndi1.setVisible(false);
        skillIndi2.setVisible(false);
        skillIndi3.setVisible(false);
        skillIndi4.setVisible(false);

        skilllsText.setVisible(true);
        descreption.setVisible(true);
        headLineText.setVisible(true);
        if (!DBConnection.isBoss){
            bewerbenButton.setVisible(true);
        }
        descreptionText.setVisible(true);

        headLineText.setText(job);
        descreption.setText(jobDesc);

        String skillString = "", numberSkill = "";
        int index = 0;

        for (int i = 0;i < jobSkills.length();i++){

            if (jobSkills.charAt(i) == ';'){

                if (index == 0){
                    ouputSkills(skillString, numberSkill, skillText1, skillIndi1);
                }
                else if(index == 1){
                    ouputSkills(skillString, numberSkill, skillText2, skillIndi2);
                }
                else if(index == 2){
                    ouputSkills(skillString, numberSkill, skillText3, skillIndi3);
                }
                else if(index == 3){
                    ouputSkills(skillString, numberSkill, skillText4, skillIndi4);
                }
                skillString = "";
                numberSkill = "";
                index++;
            }
            else{
                if (jobSkills.charAt(i) < '0' && jobSkills.charAt(i) != '='  || jobSkills.charAt(i) > '9' && jobSkills.charAt(i) != '='){
                    skillString +=jobSkills.charAt(i);
                }else if(jobSkills.charAt(i) == '='){}
                else {
                    numberSkill += jobSkills.charAt(i);
                }
            }
        }
    }

    private void ouputSkills(String skillString, String numberSkill, Text skillText, ProgressIndicator skillIndi) {
        skillText.setVisible(true);
        skillText.setText(skillString);
        skillIndi.setVisible(true);
        skillIndi.setProgress(0.099*Integer.parseInt(numberSkill));
    }

    @FXML
    private void filterJobs(KeyEvent event){
        ResultSet jobs = null;
        String searchString = searchElement.getText();
        searchString += event.getText();
        try {
            jobs = DBConnection.getInstance().getJobs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        outputForJobs.getItems().clear();
        try {
            while (jobs.next()) {
                if (searchString.equals("")){
                    PrintJob(jobs);
                }
                else if (jobs.getString("JOB_NAME").toLowerCase().contains(searchString.toLowerCase())){
                    PrintJob(jobs);
                }
            }
        } catch (SQLException ex) {
        }
    }

    private void PrintJob(ResultSet jobs) {
        String output = null;
        try {
            output = jobs.getString("JOB_ID");
            output += ". ";
            output += jobs.getString("JOB_NAME");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        outputForJobs.getItems().add(output);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ResultSet jobs = null;

        try {
            jobs = DBConnection.getInstance().getJobs();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (jobs.next()) {
                PrintJob(jobs);
            }
        } catch (SQLException ex) {
        }
    }
}