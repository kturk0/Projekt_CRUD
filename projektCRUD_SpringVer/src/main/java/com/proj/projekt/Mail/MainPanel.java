package com.proj.projekt.Mail;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class MainPanel extends JPanel {
    Connection con;
    JLabel titleLabel, fromLabel, toLabel;
    JTextArea textArea;
    public MainPanel(int mailID)
    {
        setLayout(new BorderLayout());
        if(mailID < 0) {
            titleLabel = new JLabel("Brak wiadomości!",SwingConstants.CENTER);
            add(titleLabel, BorderLayout.CENTER);
        }
        else {
            try {
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
                Statement query = con.createStatement();
                String qr = "select * from mails"
                        + " where id_mail = " + mailID;
                ResultSet queryResult = query.executeQuery(qr);
                queryResult.next();
                titleLabel = new JLabel(queryResult.getString("title"), SwingConstants.CENTER);
                textArea = new JTextArea(50, 30);
                textArea.append(queryResult.getString("body"));

                Statement queryFrom = con.createStatement();
                ResultSet queryFromResult = queryFrom.executeQuery("select * from pracownicy"
                        + " where id_pracownik = " + queryResult.getString("id_nadawcy"));
                queryFromResult.next();
                fromLabel = new JLabel("FROM : " + queryFromResult.getString("imie") + " "
                        + queryFromResult.getString("nazwisko"), SwingConstants.CENTER);
                queryFrom.close();
                ;

                Statement queryTo = con.createStatement();
                ResultSet queryToResult = queryTo.executeQuery("select * from pracownicy"
                        + " where id_pracownik = " + queryResult.getString("id_odbiorcy"));
                queryToResult.next();
                toLabel = new JLabel("TO : " + queryToResult.getString("imie") + " "
                        + queryToResult.getString("nazwisko"), SwingConstants.CENTER);
                queryTo.close();
                ;

                textArea.setEditable(false);
                textArea.setCursor(null);
                textArea.setOpaque(false);
                textArea.setFocusable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setFont(textArea.getFont().deriveFont(12f));
                textArea.setBorder(new EmptyBorder(20, 20, 20, 20));
                query.close();
                JScrollPane textScroll = new JScrollPane(textArea);
                JPanel subPanel = new JPanel();
                subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
                subPanel.setBorder(new EmptyBorder(5, 30, 0, 0));
                subPanel.add(titleLabel);
                subPanel.add(fromLabel);
                subPanel.add(toLabel);
                add(subPanel, BorderLayout.NORTH);
                add(textScroll, BorderLayout.CENTER);
                con.close();
            } catch (SQLException error_polaczenie) {
                System.out.println(error_polaczenie.getMessage());
            } catch (NumberFormatException error) {
                System.out.println(error.getMessage());
                JFrame errorFrame = new JFrame();
                errorFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE ID MAILA!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException error_sterownik) {
                System.out.println("Brak sterownika");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
