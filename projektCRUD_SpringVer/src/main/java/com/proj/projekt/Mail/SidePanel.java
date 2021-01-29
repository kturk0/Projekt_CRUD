package com.proj.projekt.Mail;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SidePanel extends JPanel {
    Connection con;
    ButtonGroup mailButtons;
    JPanel gridPanel;
    List<Integer> mailIDs;
    int userID;
    String sql;
    public SidePanel(int userID, int type) throws ClassNotFoundException {
        try {
            this.userID = userID;
            this.sql = sql;
            setLayout(new BorderLayout());
            gridPanel= new JPanel();
            gridPanel.setLayout(new GridLayout(0,1));
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
            mailIDs = new ArrayList<>();
            mailButtons = new ButtonGroup();
            List<String> buttonTexts=new ArrayList<String>();
            Statement query = con.createStatement();
            if(userID == -1)
                sql="select * from mails";
            else if(type == 0)
                sql="select * from mails where id_odbiorcy = " + userID + " AND stan = 1";
            else
                sql="select * from mails where id_nadawcy = " + userID + " AND stan = 1";
            ResultSet wynik_sql = query.executeQuery(sql);
            JToggleButton newButton;
            while(wynik_sql.next()) {
                mailIDs.add(wynik_sql.getInt("id_mail"));
                newButton = new JToggleButton( wynik_sql.getString("title"));
                newButton.setFocusable(false);
                newButton.setBackground(Color.LIGHT_GRAY);
                gridPanel.add(newButton);
                mailButtons.add(newButton);
            }
            add(gridPanel, BorderLayout.PAGE_START);
            con.close();
        }
        catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());

        }
        catch (ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}