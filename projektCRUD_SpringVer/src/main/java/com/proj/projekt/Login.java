package com.proj.projekt;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Login extends JFrame implements ActionListener {
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit;
    Login() {
        // login label
        user_label = new JLabel();
        user_label.setText("Login : ");
        userName_text = new JTextField(20);
        // haslo label
        password_label = new JLabel();
        password_label.setText("Hasło : ");
        password_text = new JPasswordField(20);
        // submit
        submit = new JButton("ZALOGUJ");
        submit.setFocusable(false);
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(user_label, cs);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 4;
        panel.add(userName_text, cs);
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(password_label, cs);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 4;
        panel.add(password_text, cs);
        message = new JLabel();
        panel.add(message);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(submit,cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("LOGOWANIE");
        setSize(300,150);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.setBackground( Color.decode("#DCE1E3") );

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Connection con;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");
            int userLevel = -1;
            int userID = 0;
            String userName = userName_text.getText();
            String password = new String(password_text.getPassword());

            Statement zapytanieA = con.createStatement();
            String sqlA="select * from admini";
            ResultSet wynik_sqlA = zapytanieA.executeQuery(sqlA);
            while(wynik_sqlA.next()) {
                String login = wynik_sqlA.getString("logiin");
                String haslo = wynik_sqlA.getString("haslo");
                if ( login.equals(userName) && haslo.equals(password))
                {
                    userLevel = 3;
                    break;
                }
            }
            if(userLevel == -1)
            {
                Statement zapytanieP = con.createStatement();
                String sqlP="select * from pracownicy";
                ResultSet wynik_sqlP = zapytanieA.executeQuery(sqlP);
                while(wynik_sqlP.next()) {
                    String login = wynik_sqlP.getString("logiin");
                    String haslo = wynik_sqlP.getString("haslo");

                    if ( login.equals(userName) && haslo.equals(password))
                    {
                        userID = wynik_sqlP.getInt("id_pracownik");
                        userLevel = wynik_sqlP.getInt("poziom_uprawnien");
                        break;
                    }
                }
                zapytanieP.close();
            }
            zapytanieA.close();
            if(userLevel == 3)
            {

                FrameAdmin okno=new FrameAdmin();
                okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(false);
                okno.setVisible(true);
           }
            else if( userLevel >= 0 && userLevel < 3 )
            {
                FramePracownik okno=new FramePracownik(userID, userLevel);
                okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(false);
                okno.setVisible(true);
            }
            else
            {
                JFrame errorFrame=new JFrame();
                errorFrame.setLocation(400,400);
                JOptionPane.showMessageDialog(errorFrame, "BŁĄD LOGOWANIA\nSPRÓBUJ PONOWNIE",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());
            JFrame errorFrame=new JFrame();
            errorFrame.setLocation(400,400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");} catch (IOException e) {
            e.printStackTrace();
        }
    }
}