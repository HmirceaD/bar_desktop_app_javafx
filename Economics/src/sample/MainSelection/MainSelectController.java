package sample.MainSelection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainSelectController implements Initializable{

    private Main main;

    @FXML
    private void goToPreset1() throws IOException{
        main.presetMain();
    }

    @FXML
    private void openMenuStage() throws IOException{
        main.openMenuAdd();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
