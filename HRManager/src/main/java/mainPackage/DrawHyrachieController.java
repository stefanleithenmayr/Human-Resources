package mainPackage;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import loginPackage.DBConnection;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DrawHyrachieController implements Initializable {

    @FXML
    private TableView<ListElement> mainTableView;
    @FXML
    private TableColumn<ListElement,String> bossColumn, firstCol,secondCol,thirdCol,quattroCol;
    @FXML
    private Label nameLabel, placeLabel, emailLabel, numberLabel;

    @FXML
    public void clickItem(MouseEvent event) throws SQLException {
        int index = (mainTableView.getSelectionModel().getSelectedCells().get(0)).getColumn();
        String name = "";

        if (index == 0){
            name = mainTableView.getSelectionModel().getSelectedItem().getFirstVal();
        }else if(index == 1){
            name = mainTableView.getSelectionModel().getSelectedItem().getSecondVal();
        }else if(index == 2){
            name = mainTableView.getSelectionModel().getSelectedItem().getRValue();
        }else if(index == 3){
            name = mainTableView.getSelectionModel().getSelectedItem().getThirdVal();
        }else if(index == 4){
            name = mainTableView.getSelectionModel().getSelectedItem().getQuattroVal();
        }

        if (name != null && !name.isEmpty()){
            nameLabel.setText("Name: " + name);

            String userName = DBConnection.getInstance().getUserName(name);
            String place = DBConnection.getInstance().getUserPlace(userName);
            if (place != null){
                placeLabel.setText("Wohnort: " + place);
            }
            String email = DBConnection.getInstance().getUserEMail(userName);
            if (email != null){
                emailLabel.setText("Email: " + email);
            }
            String number = DBConnection.getInstance().getUserTelefonnumber(userName);
            if (number != null){
                numberLabel.setText("Telefonnummer: " + number);
            }
        }else{
            nameLabel.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bossColumn.setCellValueFactory(new PropertyValueFactory<>("rValue"));
        firstCol.setCellValueFactory(new PropertyValueFactory<>("firstVal"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("secondVal"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("thirdVal"));
        quattroCol.setCellValueFactory(new PropertyValueFactory<>("quattroVal"));

        String bossName = null;
        try {
            bossName = DBConnection.getInstance().getBoss();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (bossName != null && !bossName.isEmpty()){
            data.add(new ListElement(bossName));
        }

        List<String> users = null;
        try {
            users = DBConnection.getInstance().getEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean isFixed = false;
        if (users != null){
            ListElement element = new ListElement();
            for (int i = 0; i < users.size(); i++){
                isFixed = false;
                if (i % 5 == 0){
                    element.setFirstVal(users.get(i));
                }else if((i - 1) % 5 == 0){
                    element.setSecondVal(users.get(i));
                }else if((i - 2) % 5 == 0){
                    element.setRValue(users.get(i));
                }else if((i - 3) % 5 == 0){
                    element.setThirdVal(users.get(i));
                }else if((i - 4) % 5 == 0){
                    element.setQuattroVal(users.get(i));
                    data.add(element);
                    isFixed = true;
                    element = new ListElement();
                }
            }
            if (!isFixed){
                data.add(element);
            }
            mainTableView.setItems(data);
        }
    }
    private final ObservableList<ListElement> data
            = FXCollections.observableArrayList();
}

