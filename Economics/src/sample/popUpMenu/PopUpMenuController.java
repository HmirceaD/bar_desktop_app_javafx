package sample.popUpMenu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;

public class PopUpMenuController implements Initializable {

    private String itemName, itemPrice;
    private Stage thisStage;
    private boolean key;
    private boolean dontAsk;
    private Formatter x;

    @FXML
    public Label label1;
    @FXML
    public CheckBox checkBox1;
    @FXML
    public Button yesButton;
    @FXML
    public Button noButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        checkBox1.setSelected(false);

        try {
            x = new Formatter(new File("config1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void retYesButton(){
        key = true;

        if(checkBox1.isSelected()){
            x.format("%s","false");
        }else{
            x.format("%s","true");
        }

        thisStage.close();
        x.close();

    }

    @FXML
    public void retNoButton(){
        key = false;

        System.out.println(checkBox1.isSelected());
        if(checkBox1.isSelected()){
            x.format("%s","false");
        }else{
            x.format("%s","true");
        }

        thisStage.close();
        x.close();

    }


    public boolean getValBool() {
        return key;
    }

    public boolean openOrNot(){
        return dontAsk;
    }

    public void setString(String name, String price){
        itemName = name;
        itemPrice = price;

        label1.setText("Are you sure you want add " + itemName + " to the menu?");
    }

    public void setStagePop(Stage s){
        thisStage = s;
    }
}
