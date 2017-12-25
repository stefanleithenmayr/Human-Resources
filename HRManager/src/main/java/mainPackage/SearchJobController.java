package mainPackage;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
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
    private void printText(MouseEvent event) throws SQLException {
        String job = (String)outputForJobs.getSelectionModel().getSelectedItem();
        String jobDesc = DBConnection.getInstance().getJobDesc(job);
        String jobSkills = DBConnection.getInstance().getJobSkills(job);
        skilllsText.setVisible(true);
        headLineText.setText(job);
        headLineText.setVisible(true);
        descreptionText.setVisible(true);
        descreption.setText(jobDesc);
        descreption.setVisible(true);
        if (!jobSkills.isEmpty()){
            skillText1.setVisible(true);
            skillText1.setText(jobSkills.substring(0, jobSkills.indexOf('=')));
            skillIndi1.setVisible(true);
            skillIndi1.setProgress(0.99*Integer.parseInt(jobSkills.substring(jobSkills.indexOf('=')+1, jobSkills.indexOf(';')-1)));
        }
    }

    @FXML
    private void filterJobs(KeyEvent event){
        ResultSet jobs = null;
        String searchString = searchElement.getText();
        String c = event.getText();
        searchString += event.getText();
        try {
            jobs = DBConnection.getInstance().getJobs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (searchString.equals("")){
            System.out.println("a");
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
