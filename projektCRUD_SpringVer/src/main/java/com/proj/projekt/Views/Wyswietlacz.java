package com.proj.projekt.Views;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public abstract class Wyswietlacz {
    Connection con;
    JFrame frame;
    JTable jt;
    JScrollPane sp;
    public Wyswietlacz() throws IOException, SQLException, ClassNotFoundException {
        Properties prop=new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties");
        prop.load(in);
        in.close();

        String drivers = prop.getProperty("spring.datasource.driverClassName");
        String connectionURL = prop.getProperty("spring.datasource.url");
        String username = prop.getProperty("spring.datasource.username");
        String password = prop.getProperty("spring.datasource.password");
        Class.forName(drivers);
        con=DriverManager.getConnection(connectionURL,username,password);
        frame=new JFrame();
        frame.setLocation(200,200);
        frame.setLocation(550,50);
        frame.setSize(700,350);
    }
    public void setTable(){
        jt.setFont(new Font("Roboto",Font.PLAIN,14));
        jt.setDefaultEditor(Object.class, null);
        jt.setAutoCreateRowSorter(true);
    }

}