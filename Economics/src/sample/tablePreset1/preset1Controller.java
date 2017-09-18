package sample.tablePreset1;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import javafx.event.ActionEvent;
import sample.MenuItems.MenuItemsController;

import java.io.IOException;

public class preset1Controller {

    private Main main;
    private Stage tabStage;

    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;
    @FXML
    public Button button4;
    @FXML
    public Button button5;
    @FXML
    public Button button6;
    @FXML
    public Button button7;
    @FXML
    public Button button8;
    @FXML
    public Button button9;
    @FXML
    public Button button10;
    @FXML
    public Button button11;
    @FXML
    public Button button12;


    @FXML
    public Button backButton;

    @FXML
    private void tabWindow(ActionEvent event) throws IOException {
        main = new Main();

        tabStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MenuItems/TableTab.fxml"));
        AnchorPane tabLayout = loader.load();

        tabStage.setTitle("Table Tab");
        tabStage.initModality(Modality.WINDOW_MODAL);
        tabStage.initOwner(main.returnStage()); /***********IS MAFIOT*********/

        Button btn = (Button) event.getSource();

        MenuItemsController mic = (MenuItemsController)loader.getController();
        mic.setItemId(btn.getId().substring(6));//this way we can send the id of the button clicked

        Scene scene = new Scene(tabLayout);
        tabStage.setScene(scene);
        tabStage.showAndWait();

        int max = mic.sizeTable();

        if(max != 0){
            btn.getStyleClass().removeAll("button-open");
            btn.getStyleClass().add("button-closed");
        }else{
            btn.getStyleClass().removeAll("button-closed");
            btn.getStyleClass().add("button-open");
        }
    }

    @FXML
    private void goBack() throws IOException{
        main.selectMain();
    }

    public Stage getTabStage(){
        return tabStage;
    }
}
