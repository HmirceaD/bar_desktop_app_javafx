package sample.MainSelection;

import java.sql.*;

public class MainConnector {

    private final String pass = "doreltitel";


    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root",pass);
    }

}
