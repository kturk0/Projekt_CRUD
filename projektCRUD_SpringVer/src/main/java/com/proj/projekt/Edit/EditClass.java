package com.proj.projekt.Edit;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class EditClass{

    Connection con;
    public EditClass() throws IOException, ClassNotFoundException, SQLException {
        Properties prop=new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties");
        prop.load(in);
        in.close();

        String drivers = prop.getProperty("spring.datasource.driverClassName");
        String connectionURL = prop.getProperty("spring.datasource.url");
        String username = prop.getProperty("spring.datasource.username");
        String password = prop.getProperty("spring.datasource.password");
        Class.forName(drivers);
        con= DriverManager.getConnection(connectionURL,username,password);
    }
}
