package sample.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.MainSelection.MainConnector;
import sample.signUp.SignUpController;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.chrono.MinguoChronology;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class loginController implements Initializable{

    @FXML
    public TextField userNameField;
    @FXML
    public PasswordField passWordField;
    @FXML
    public Label label1;
    @FXML
    public Button loginButton;
    @FXML
    public Button signButton;
    @FXML
    public Label labelSign;

    private Stage signStage;
    private Scanner x;

    private MainConnector mc;
    private Connection con;
    private Main main;

    //Encryption Stuff
    private final String alg = "AES";
    private final byte[] keyValue = {'Z','E','B','R','A','T','r','E','C','e','S','t','R','a','D','a'};

    private String defUser;
    private String defPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        label1.setText("");

        mc = new MainConnector();

        try {

            con = mc.getConnection();

        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        updateStuff();

    }

    @FXML
    private void loginButton() throws IOException {

        if(userNameField.getText().equals(defUser) && passWordField.getText().equals(defPass)){
            label1.setText("You logged in");
            main.selectMain();
        }else if(!userNameField.getText().equals(defUser) && passWordField.getText().equals(defPass)){
            label1.setText("Incorrect Username");
        }else if(userNameField.getText().equals(defUser) && !passWordField.getText().equals(defPass)){
            label1.setText("Incorrect Password");
        }else if(!userNameField.getText().equals(defUser)&& !passWordField.getText().equals(defPass)){
            label1.setText("Incorrect Username and Password");
        }
    }

    @FXML
    private void signUpWindow() throws IOException {
        main = new Main();
        signStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("signUp/SignUp.fxml"));
        AnchorPane signUpLayout = loader.load();

        SignUpController sc = loader.getController();
        sc.setOld(defUser,defPass);
        sc.setStage(signStage);

        signStage.setTitle("Sign Up window");
        signStage.initModality(Modality.WINDOW_MODAL);
        signStage.initOwner(main.returnStage());

        Scene scene = new Scene(signUpLayout);
        signStage.setScene(scene);
        sc.setStage(signStage);
        signStage.showAndWait();

        updateStuff();

    }

    public Stage returnSignStage(){return signStage;}

    private String decrypt(String encryptedValue) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(alg);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, alg);
        return key;
    }

    private void updateStuff(){

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM restaurant.users;");

            while(rs.next()) {
                defUser = decrypt(rs.getString("userNames"));
                defPass = decrypt(rs.getString("userPass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
