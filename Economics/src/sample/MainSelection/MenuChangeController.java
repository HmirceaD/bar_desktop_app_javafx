package sample.MainSelection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.popUpMenu.PopUpMenuController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MenuChangeController implements Initializable{

    private Main main;
    private MainConnector connect;
    private Connection con;
    private String nameTxt;
    private Double priceTxt;
    private Stage thisStage;
    private Stage popUp;
    private Scanner x;

    @FXML
    public Button closeButton;
    @FXML
    public TableView<MenuClass> tableView1;
    @FXML
    public TextField nameField;
    @FXML
    public TextField priceField;
    @FXML
    public TableColumn<MenuClass, String> nameColumn;
    @FXML
    public TableColumn<MenuClass, Double> priceColumn;
    @FXML
    public Button button1;
    @FXML
    public Button deleteButton;

    private ObservableList<MenuClass> list1 = FXCollections.observableArrayList();

    @FXML
    private void openPopUp()throws IOException{
        main.openSurePopUp();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        connect = new MainConnector();

        try{
            con = connect.getConnection();
            pushData();

        }catch (Exception ex){
            System.out.println("ERROR: " + ex);
        }

        File f = new File("config1.txt");

        if(f.exists()){

            return;

        }else {

            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int numberData(){

        String sql = "SELECT * FROM restaurant.menufinal ORDER BY ID DESC LIMIT 1";

        int max1 = 0;

        try{
            ResultSet rs = con.createStatement().executeQuery(sql);

            while(rs.next())
                max1 = rs.getInt("ID");

        }catch(SQLException ex){
            System.out.println(ex);
        }

        return (max1 + 1);
    }


    /************This pushes data from the database to the tableview*****************/
    private void pushData() {

        String sql = "SELECT * FROM menufinal ORDER BY ID";
        try{
            ResultSet rs = con.createStatement().executeQuery(sql);
            while(rs.next()){

                list1.add(new MenuClass(rs.getInt("ID"),rs.getString("NAME"),rs.getDouble("PRICE")));
            }

            tableView1.setItems(list1);
        }catch (Exception ex){
            System.out.println("ERROR: " + ex);

        }
    }

    private void clearTable(){
        for(int i = 0; i < tableView1.getItems().size() + 1; i++){
            tableView1.getItems().clear();
        }
    }


    @FXML
    private void addData() throws IOException{

        main = new Main();

        try{

           x = new Scanner(new File("config1.txt"));
        }catch (IOException ex){
            System.out.println(ex);
        }

        boolean key;
        boolean doOpen = Boolean.valueOf(x.next());

        /**********This is important************/

        if(doOpen){

            if (!nameField.getText().equals("") && !priceField.getText().equals("")) {
                popUp = new Stage();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("popUpMenu/PopUpMenu.fxml"));
                AnchorPane popLayout = loader.load();

                popUp.setTitle("");
                popUp.setResizable(false);
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(main.returnMenuStage());

                PopUpMenuController popObj = loader.getController();
                popObj.setString(nameField.getText(), priceField.getText());
                popObj.setStagePop(popUp);

                Scene scene = new Scene(popLayout);
                popUp.setScene(scene);
                popUp.showAndWait();

                key = popObj.getValBool();
                doOpen = popObj.openOrNot();

                if (!key) {
                    nameField.setText("");
                    priceField.setText("");

                } else {

                    priceTxt = Double.parseDouble(priceField.getText());
                    nameTxt = nameField.getText();

                    String sql = "INSERT INTO restaurant.menufinal (ID, NAME, PRICE) VALUES (" + numberData() + ", '" + nameTxt + "', '" + priceTxt + "');";

                    try {

                        Statement st = con.createStatement();
                        st.executeUpdate(sql);

                        clearTable();
                        pushData();
                        nameField.clear();
                        priceField.clear();

                    } catch (Exception ex) {
                        System.out.println("ERROR: " + ex);
                    }
                }
            }
        }else{

            priceTxt = Double.parseDouble(priceField.getText());
            nameTxt = nameField.getText();

            String sql = "INSERT INTO restaurant.menufinal (ID, NAME, PRICE) VALUES (" + numberData() + ", '" + nameTxt + "', '" + priceTxt + "');";

            try {

                Statement st = con.createStatement();
                st.executeUpdate(sql);

                clearTable();
                pushData();

                nameField.clear();
                priceField.clear();

            } catch (Exception ex) {
                System.out.println("ERROR: " + ex);
            }
        }

        x.close();

    }

    @FXML
    private void deleteData() throws IOException{ //DELETE FROM `restaurant`.`menu` WHERE `ID`='12';

        MenuClass mp = tableView1.getSelectionModel().getSelectedItem();

        String sql = "DELETE FROM restaurant.menufinal WHERE ID =" + mp.getItemId() + ";";

        try {
            Statement st = con.createStatement();
            st.executeUpdate(sql);

            clearTable();
            pushData();

        }catch (SQLException s){
            System.out.println(s);
        }
    }

    @FXML
    private void closeWindow() throws IOException{

        main = new Main();
        thisStage = main.returnMenuStage();

        thisStage.close();
    }

    public Stage getPopUpStage(){
        return popUp;
    }
}
