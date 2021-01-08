package com.proj.projekt.Mail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SendPanel extends JPanel implements ActionListener {
    Connection con;
    JLabel titleLabel, recipentLabel;
    JTextArea titleArea;
    JTextArea textArea;
    JComboBox recipentBox;
    JButton confirmButton;
    List<String> lista;
    int userID;
    public SendPanel(int userID)
    {
        this.userID = userID;
        setLayout(null);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            lista=new ArrayList<String>();
            Statement query = con.createStatement();
            String sql="select * from pracownicy";
            ResultSet queryResult = query.executeQuery(sql);
            while(queryResult.next()) {
                String t=queryResult.getString("id_pracownik");
                String z=queryResult.getString("imie");
                String y=queryResult.getString("nazwisko");
                lista.add(t);
                lista.add(z);
                lista.add(y);
            }
            String[] pracownicyNazwy = new String[lista.size()/3];
            String idP = "un";
            String imieP = "un";
            String nazwiskoP;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%3 == 0)
                    idP = lista.get(i);
                else if (i%3 == 1)
                    imieP = lista.get(i);
                else {
                    nazwiskoP = lista.get(i);
                    pracownicyNazwy[i/3] = idP + " / " + imieP + " " + nazwiskoP;
                }
            }
            recipentBox = new JComboBox(pracownicyNazwy);
            con.close();
        }
        catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());

        } catch (NumberFormatException error) {
            System.out.println(error.getMessage());
            JFrame errorFrame = new JFrame();
            errorFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");
        }
        titleLabel = new JLabel("Tytuł : ");
        titleLabel.setBounds(20,20,50,30);
        recipentLabel = new JLabel("Do    : ");
        recipentLabel.setBounds(20,60,50,30);
        titleArea = new JTextArea();
        titleArea.setFont(titleArea.getFont().deriveFont(12f));
        titleArea.setBounds(70,20,300,30);
        recipentBox.setBounds(70,60,300,30);
        recipentBox.setFocusable(false);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(12f));
        JScrollPane textScroll = new JScrollPane(textArea);
        textScroll.setBounds(20,100,500,400);
        add(titleLabel);
        add(titleArea);
        add(recipentLabel);
        add(recipentBox);
        add(textScroll);
        confirmButton =new JButton("WYŚLIJ");
        confirmButton.setBounds(20,520,150,30);
        add(confirmButton);
        confirmButton.addActionListener(this);
        setBounds(500,0,600,600);
        setBorder(BorderFactory.createDashedBorder(Color.black));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo == confirmButton) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://"
                        + "localhost:1433;databaseName=dbo;"
                        + "user=sa;password=haslosql;");
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date(System.currentTimeMillis());
                String daneMail = "insert into mails values (?, ?, ?, ?, ?, ?)";
                PreparedStatement prep = con.prepareStatement(daneMail);
                prep.setString(1, Integer.toString(userID));
                prep.setString(2, lista.get(recipentBox.getSelectedIndex() * 3));
                prep.setString(3, titleArea.getText());
                prep.setString(4, textArea.getText());
                prep.setString(5, formatter.format(currentDate));
                prep.setString(6, "0");

                prep.executeUpdate();

                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "Wysłano wiadomość",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
                prep.close();
                con.close();
            } catch (SQLException | NumberFormatException error_polaczenie) {
                System.out.println(error_polaczenie.getMessage());
                JFrame errorFrame = new JFrame();
                errorFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException error_sterownik) {
                System.out.println("Brak sterownika");
            }
        }
    }
}
