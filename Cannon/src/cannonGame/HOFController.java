package cannonGame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the HOFScene
 */
public class HOFController implements Initializable {
    @FXML
    private TableView winnersTable;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn scoreCol;

    public void closeRecords(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataBaseHelper db = new DataBaseHelper();
        ArrayList<Winner> winners = db.getWinners();
        // populate table
        ObservableList<Winner> data = FXCollections.observableArrayList();
        data.addAll(winners);
        nameCol.setCellValueFactory(new PropertyValueFactory<Winner, String>("name"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Winner, Integer>("score"));
        winnersTable.getItems().setAll(data);
    }
}
