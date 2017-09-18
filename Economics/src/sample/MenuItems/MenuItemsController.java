package sample.MenuItems;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.MainSelection.MenuClass;
import sample.MainSelection.MainConnector;
import sample.tablePreset1.preset1Controller;

import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class MenuItemsController implements Initializable {

    private Main main;
    private preset1Controller ps;
    private Connection con;
    private MainConnector connector;
    private ObservableList<MenuClass> list1 = FXCollections.observableArrayList();//list for menuTable
    private ObservableList<MenuClass> listTab = FXCollections.observableArrayList();//List for tabs
    private String itemId;
    private int max1;
    private Stage thisStage;
    private Stage popUp;

    @FXML
    public Button clearButton;
    @FXML
    public TextField priceField;
    @FXML
    public Label label1;
    @FXML
    public Button addButton;
    @FXML
    public TableView<MenuClass> menuTable;
    @FXML
    public TableColumn<MenuClass, String> nameMenuTable;
    @FXML
    public TableColumn<MenuClass, Double> priceMenuTable;
    @FXML
    public TableView<MenuClass> tabTable;
    @FXML
    public TableColumn<MenuClass, String> nameTabTable;
    @FXML
    public TableColumn<MenuClass, Double> priceTabTable;
    @FXML
    public Label label2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listClear();

        priceField.setEditable(false);

        nameMenuTable.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceMenuTable.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        //Individual Table tab
        tabTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameTabTable.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceTabTable.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));


        connector = new MainConnector();

        try{
            con = connector.getConnection();
            pushData(menuTable);

        }catch(Exception ex){
            System.out.println("ERROR: " + ex);
        }

    }

    private int currentSize(){

        String sql = "SELECT * FROM tabscurrent ORDER BY ID DESC LIMIT 1";

        int provmax = 0;

        try {
            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()){
                provmax = rs.getInt("ID");
            }

        }catch (SQLException ex){
            System.out.println("ERROR: " + ex);
        }

        return provmax;
    }

    private void loadTab(String itemId){

        String sql = "SELECT * FROM tabscurrent WHERE tabID = " + itemId + ";";

        try{

            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()){

                listTab.add(new MenuClass(rs.getInt("ID"),rs.getString("itemName"),rs.getDouble("price")));
            }

            tabTable.setItems(listTab);

        }catch (SQLException ex){
            System.out.println("ERROR: " + ex);
        }
    }

    /************This pushes data from the database to the tableview*****************/
    private void pushData(TableView tb) {

        String sql = "SELECT * FROM menufinal";
        try{


            ResultSet rs = con.createStatement().executeQuery(sql);
            while(rs.next()){

                list1.add(new MenuClass(rs.getInt("ID"),rs.getString("NAME"),rs.getDouble("PRICE")));
            }

            tb.setItems(list1);
        }catch (Exception ex){
            System.out.println("ERROR: " + ex);

        }
    }

    /**********Utility Function************/
    private void clearTable(TableView s){
        for(int i = 0; i < s.getItems().size() + 1; i++){
            s.getItems().clear();
        }
    }

    /************This pushes data from the menuDatabase to the tableTab dataBase, click onAction at the add to tab button**************/
    @FXML
    private void addToTab(){

        MenuClass mp = menuTable.getSelectionModel().getSelectedItem();

        String sql = "INSERT INTO restaurant.tabscurrent (ID, tabID, itemName, price) VALUES ('" + (currentSize() + 1) +"', '" + itemId + "', '" + mp.getItemName() + "', '" + mp.getItemPrice() + "');" ;

        try{
            Statement st = con.createStatement();
            st.executeUpdate(sql);

        }catch (SQLException e){
            System.out.println("ERROR: " + e);
        }

        clearTable(tabTable);
        loadTab(itemId);
        setPriceText();

    }

    /****************This is connected to the preset1Controller java class, we can  transmit this way the id of the button that was clicked in that controller*************************/
    public void setItemId(String t){

        this.itemId = t;
        updateLabel(itemId);
        loadTab(itemId);
        max1 = currentSize();
        setPriceText();
    }

    private void updateLabel(String x){

        label1.setText("Current Table is: " + x);
    }

    public void listClear(){
        listTab.clear();
    }

    /***********THIS NEEDS WORK, THROWS NullPointerException**************/

    private void setPriceText(){
        double Sum = 0;

        for(MenuClass mp: listTab){
           Sum += mp.getItemPrice();
        }

        priceField.setText(valueOf(Sum) + " RON");
    }

    @FXML
    private void delElements() {

        MenuClass mp = tabTable.getSelectionModel().getSelectedItem();

        if (mp.getItemId() != null) {

            String sql = "DELETE FROM restaurant.tabscurrent WHERE ID =" + mp.getItemId() + ";";

            try {

                Statement st = con.createStatement();
                st.executeUpdate(sql);

                clearTable(tabTable);
                loadTab(itemId);
                setPriceText();


            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
            }
        } else {
            label2.setText("Please select an item in order to delete it");
        }
    }

    @FXML
    private void clearAll() throws IOException, SQLException{


        Statement st = con.createStatement();

        for(MenuClass mp: listTab){
            String sql = "DELETE FROM restaurant.tabscurrent WHERE ID =" + mp.getItemId() + ";";

            try {
                st.executeUpdate(sql);

            }catch (SQLException ex){
                System.out.println("ERROR: " + ex);
            }
        }

        clearTable(tabTable);
        loadTab(itemId);
        setPriceText();


    }

    public int sizeTable(){
        return listTab.size();
    }

}
