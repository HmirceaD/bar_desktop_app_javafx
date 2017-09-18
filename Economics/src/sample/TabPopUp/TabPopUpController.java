package sample.TabPopUp;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class TabPopUpController {

    private boolean key;
    private Stage stage;

    @FXML
    public Button yesButton;
    @FXML
    public Button noButton;
    @FXML
    public Label label1;

    @FXML
    private void yesClose(){
        key = true;
        stage.close();
    }

    @FXML
    private void noClose(){
        key = false;
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public boolean getKey(){
        return key;
    }


}
