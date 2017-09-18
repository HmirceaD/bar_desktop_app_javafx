package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private static Stage window;
    private static BorderPane mainLayout;
    private static Stage windowMenu;

    public static void main(String[] args) {
        launch(args);
    }

    /********We need this ******/
    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.setTitle("RezApp");
        window.setResizable(false);

        showMain();
        loginMain();

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.setFullScreen(true);
        window.setResizable(false);
        window.show();
    }

    /*****Loader For the Main Window*******/
    private static void showMain() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("sample.fxml"));
        mainLayout = loader.load();

    }

    /******Utility Funtion For the close button right-up of the main window*******/
    public static void closeMain(){
        window.close();
    }

    /****First Scene/Login*******/
    public static void loginMain() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("login/loginScreen.fxml"));
        BorderPane borderLayout = loader.load();
        mainLayout.setCenter(borderLayout);

    }

    /****Main Selection Screen******/
    public static void selectMain() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainSelection/MainSelect.fxml"));
        BorderPane selectLayout = loader.load();
        mainLayout.setCenter(selectLayout);
    }

    /****First preset of the tables******/
    public static void presetMain() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("tablePreset1/tablePreset1.fxml"));
        AnchorPane tableLayout = loader.load();
        mainLayout.setCenter(tableLayout);
    }

    /****Stage to manage the menu,Add/remove elements that show up on the default menu Stage *****/
    public static void openMenuAdd()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainSelection/MenuChange.fxml"));
        BorderPane menuAddLayout = loader.load();

        windowMenu = new Stage();
        windowMenu.setTitle("Menu Change");
        windowMenu.initModality(Modality.WINDOW_MODAL);
        windowMenu.initOwner(window);

        Scene scene = new Scene(menuAddLayout);
        windowMenu.setScene(scene);
        windowMenu.showAndWait();
    }

    /**********PopUp window for adding items to Database***********/
    public static void openSurePopUp()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainSelection/PopUp.fxml"));
        AnchorPane popUp = loader.load();

        Stage popUpStage = new Stage();
        popUpStage.setTitle("PopUp");
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(windowMenu);

        Scene scene = new Scene(popUp);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    /**Returns the main stage**/
    public Stage returnStage(){
        return window;
    }

    /***Returns the stage for the menu table*******/
    public Stage returnMenuStage(){ return windowMenu;}


}
