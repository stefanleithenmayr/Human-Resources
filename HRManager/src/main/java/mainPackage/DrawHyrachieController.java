package mainPackage;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DrawHyrachieController implements Initializable {

    @FXML
    private TableView<ListElement> mainTableView;
    @FXML
    private TableColumn bossColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bossColumn.setCellValueFactory(new PropertyValueFactory<>("rValue"));
        mainTableView.setItems(this.data);
    }

    private final ObservableList<ListElement> data
            = FXCollections.observableArrayList(
            new ListElement("Test123")
    );
}

