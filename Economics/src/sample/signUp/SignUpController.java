package sample.signUp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.MainSelection.MainConnector;
import sample.login.loginController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.security.Key;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController implements Initializable{

    private String user,pass,oldUser,oldPass;
    private Stage sg;
    private MainConnector mc;
    private Connection con;

    private Main main;
    //Encryption Stuff
    private final String alg = "AES";
    private final byte[] keyValue = {'Z','E','B','R','A','T','r','E','C','e','S','t','R','a','D','a'};

    @FXML
    public Label labelUsable;
    @FXML
    public TextField oldTextUser;
    @FXML
    public TextField oldTextPass;
    @FXML
    public Button confirmButton;
    @FXML
    public TextField userField;
    @FXML
    public TextField passField;
    @FXML
    public TextField confField;
    @FXML
    public Label label1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mc = new MainConnector();

        try{
            con = mc.getConnection();

        }catch (Exception ex){
            System.out.println("ERROR: " + ex);
        }
    }


    @FXML
    private void confButton() throws Exception {
        user = userField.getText();
        pass = passField.getText();

        if(oldTextUser.getText().equals(oldUser) && oldTextPass.getText().equals(oldPass)){


            if(userField.getText().equals("") || passField.getText().equals("") || passField.getText().equals("")) {
                label1.setText("All fields must be filled");
            }else{
                if(!passField.getText().equals(confField.getText())){
                    label1.setText("Passwords must match");
                }else{

                    label1.setText("Successful sign up!");

                    try {
                        Statement st = con.createStatement();

                        st.executeUpdate("INSERT INTO restaurant.users (ID, userNames, userPass) VALUES ('1', '" + encrypt(user) + "', '" + encrypt(pass) + "');");
                        sg.close();
                    }catch (SQLException ex){

                        Statement st = con.createStatement();
                        st.executeUpdate("UPDATE restaurant.users SET userNames='" + encrypt(user) +"', userPass='" + encrypt(pass) + "' WHERE ID=1;");
                        sg.close();
                    }
                }
            }

            //UPDATE `restaurant`.`users` SET `userNames`='dsadas', `userPass`='dasdas' WHERE `ID`='1';

        }else{
            label1.setText("Last username or password incorrect");
        }
    }

    public void setOld(String oldUser, String oldPass){
        this.oldUser = oldUser;
        this.oldPass = oldPass;
        //System.out.print(oldUser + " " + oldPass);
    }

    public void setStage(Stage sg){
        this.sg = sg;
    }

    private String encrypt(String valueToEnc) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(alg);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encValue);
        return encryptedValue;
    }


    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, alg);
        return key;
    }

}
