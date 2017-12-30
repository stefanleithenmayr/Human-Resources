package mainPackage;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import loginPackage.DBConnection;
import sun.security.pkcs11.Secmod;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ApplicationsManager implements Initializable {
    @FXML
    JFXListView outputForAppliances;
    @FXML
    Button acceptButton,refressAppliancesButton,loadInfosButton;
    @FXML
    JFXComboBox<String> comboBoxJobs;
    @FXML
    Text nameText,ageText,telefonText,emailText,descriptionText;
    @FXML
    JFXTextArea descriptionField,realNameField,ageField,telefonNumberField,emailField;

    @FXML
    public void loadInfos(ActionEvent event) throws SQLException {
        nameText.setVisible(true);
        realNameField.setVisible(true);
        ageText.setVisible(true);
        ageField.setVisible(true);
        telefonText.setVisible(true);
        telefonNumberField.setVisible(true);
        emailText.setVisible(true);
        emailField.setVisible(true);
        descriptionText.setVisible(true);
        descriptionField.setVisible(true);

        String userName = DBConnection.getInstance().getUserNameJobIDByString((String)outputForAppliances.getSelectionModel().getSelectedItem()).split(";")[0];
        String realName = DBConnection.getInstance().getUserRealName(userName);
        Integer ageCache = DBConnection.getInstance().getUserAge(userName);
        String age = Integer.toString(ageCache);
        String  telefonNumber = DBConnection.getInstance().getUserTelefonnumber(userName);
        String email =DBConnection.getInstance().getUserEMail(userName);
        String description = DBConnection.getInstance().getUserDescription(userName)  ;
        if (realName == null || realName.equals("<null>")|| realName.equals(" ")){
            realName = "-";
        }
        if (age == null || age.equals("<null>") || age.equals(" ")){
            age = "-";
        }
        if (telefonNumber == null || telefonNumber.equals("<null>")|| telefonNumber.equals(" ")){
            telefonNumber = "-";
        }
        if (email == null || email.equals("<null>")|| email.equals(" ")){
            email = "-";
        }
        if (description == null || description.equals("<null>")|| description.equals(" ")){
            description = "-";
        }
        realNameField.setText(realName);
        ageField.setText(age);
        telefonNumberField.setText(telefonNumber);
        emailField.setText(email);
        descriptionField.setText(description);
    }

    @FXML
    public void sortByOffers(ActionEvent event) throws SQLException {
        Integer id = DBConnection.getInstance().getApplicationIdByName(comboBoxJobs.getValue());
        if (id != null && id != -1){
            PrintAllJobsSelectedByOffer(id);
        }
    }

    private void PrintAllJobsSelectedByOffer(Integer id) throws SQLException {
        outputForAppliances.getItems().clear();
        String appliances = "";
        try {
            appliances = DBConnection.getInstance().GetJob_AppliancesNameID();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] lines = appliances.split("\n");
        for (int i = 0; i < lines.length; i++){
            String[] line = lines[i].split(";");
            if (Integer.parseInt(line[1]) == id){
                String name = DBConnection.getInstance().getUserRealName(line[0]);
                outputForAppliances.getItems().add(name);
            }
        }
    }
    @FXML
    public  void accept(ActionEvent event) throws SQLException {
        if (comboBoxJobs.getSelectionModel().getSelectedItem() == null){
            String userNameJobID = DBConnection.getInstance().getUserNameJobIDByString((String)outputForAppliances.getSelectionModel().getSelectedItem());
            String[] line = userNameJobID.split(";");
            String userName = line[0];
            Integer job_id = Integer.parseInt(line[1]);
            //Auf Hirachie adden
            //Email an genommenen Kandidaten senden
            //set Mitarbeiter auf true
        }
    }
    @FXML
    public  void visibleTrue(MouseEvent event){
        loadInfosButton.setVisible(true);
        acceptButton.setVisible(true);
        nameText.setVisible(false);
        ageText.setVisible(false);
        telefonText.setVisible(false);
        emailText.setVisible(false);
        descriptionText.setVisible(false);
        realNameField.setVisible(false);
        ageField.setVisible(false);
        telefonNumberField.setVisible(false);
        emailField.setVisible(false);
        descriptionField.setVisible(false);
    }
    @FXML
    public  void visibleFalse(MouseEvent event){
        loadInfosButton.setVisible(false);
        acceptButton.setVisible(false);
        nameText.setVisible(false);
        realNameField.setVisible(false);
        ageText.setVisible(false);
        ageField.setVisible(false);
        emailText.setVisible(false);
        emailField.setVisible(false);
        telefonText.setVisible(false);
        telefonNumberField.setVisible(false);
        descriptionText.setVisible(false);
        descriptionField.setVisible(false);
    }
    @FXML
    public void refresh(ActionEvent event) throws SQLException {
        PrintAllJobsAndAddCheckbox();
        comboBoxJobs.getSelectionModel().clearSelection();
        outputForAppliances.getItems().clear();
        PrintAllJobsAndAddCheckbox();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            PrintAllJobsAndAddCheckbox();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void PrintAllJobsAndAddCheckbox() throws SQLException {
        comboBoxJobs.getItems().clear();
        outputForAppliances.getItems().clear();
        String appliances = "", dropdown="",cache;
        try {
            appliances = DBConnection.getInstance().GetJob_AppliancesNameID();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] lines = appliances.split("\n");
        if (!lines[0].isEmpty()){
            for (int i = 0; i < lines.length; i++){
                String[] line = lines[i].split(";");
                String job = DBConnection.getInstance().getJobByJobID(line[1]);
                String name = DBConnection.getInstance().getUserRealName(line[0]);
                cache = job+"§ JobID: "+ line[1];
                if (!dropdown.contains(cache)){
                    dropdown += (cache+"\n");
                }
                String output = name + " hat sich für "+job+" beworben";
                outputForAppliances.getItems().add(output);
            }
            lines = dropdown.split("\n");
            boolean changed = true;
            String cache1;
            while (changed){
                changed = false;
                for (int i = 0; i+1 < lines.length; i++){
                    String[] lineNow = lines[i].split(": ");
                    String[] lineNext = lines[i+1].split(": ");
                    if (Integer.parseInt(lineNow[1]) > Integer.parseInt(lineNext[1])){
                        cache1 = lines[i+1];
                        lines[i+1] = lines[i];
                        lines[i] = cache1;
                        changed = true;
                    }
                }
            }
            for (int i = 1; i < lines.length; i++){
                String[] line = lines[i].split("§");
                comboBoxJobs.getItems().add(line[0]);
            }
        }
    }
}
