package sample;

import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Main main;
    private Formatter x;
    private File fl;

    @Override
    public void initialize(URL url, ResourceBundle rez){

        try {
            x = new Formatter("config1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        x.format("%s","true");
        x.close();


    }

    public void killWindow(){
        main.closeMain();
    }

}

