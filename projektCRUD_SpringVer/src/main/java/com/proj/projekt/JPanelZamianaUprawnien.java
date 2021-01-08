package com.proj.projekt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class JPanelZamianaUprawnien {
    Connection con;

    void zamianaUprawnien()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from pracownicy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("id_pracownik");
                String z=wynik_sql.getString("imie");
                String y=wynik_sql.getString("nazwisko");
                String lvl=wynik_sql.getString("poziom_uprawnien");
                lista.add(t);
                lista.add(z);
                lista.add(y);
                lista.add(lvl);
            }
            String[] pracownicyNazwy = new String[lista.size()/3];
            String idP = "un";
            String imieP = "un";
            String nazwiskoP = "un";
            String lvlP = "un";
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%4 == 0)
                    idP = lista.get(i);
                else if (i%4 == 1)
                    imieP = lista.get(i);
                else if (i%4 == 2)
                    nazwiskoP = lista.get(i);
                else if (i%4 == 3)
                {
                    lvlP = lista.get(i);
                    pracownicyNazwy[i/4] = idP + " / " + imieP + " " + nazwiskoP + " (" + lvlP + ")";
                }
            }
            JComboBox pracownicyBox = new JComboBox(pracownicyNazwy);
            pracownicyBox.setFocusable(false);
            JRadioButton r1=new JRadioButton("0 - Wyświetlanie danych",true);
            JRadioButton r2=new JRadioButton("1 - Wprowadzanie i usuwanie produktów/zamówień");
            JRadioButton r3=new JRadioButton("2 - Wprowadzanie i usuwanie dostaw");
            r1.setFocusPainted(false);
            r2.setFocusPainted(false);
            r3.setFocusPainted(false);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            JLabel naglowek = new JLabel("ID / IMIĘ NAZWISKO (lvl):");
            myPanel.add(naglowek);
            myPanel.add(pracownicyBox);
            ButtonGroup bg=new ButtonGroup();
            bg.add(r1);
            bg.add(r2);
            bg.add(r3);
            myPanel.add(r1);
            myPanel.add(r2);
            myPanel.add(r3);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "MODYFIKACJA UPRAWNIEŃ", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(pracownicyBox.getSelectedIndex() * 3);
                String selected_Level = "0";
                Enumeration<AbstractButton> buttons = bg.getElements();
                AbstractButton button;
                for(int i=0;buttons.hasMoreElements();i++) {
                    button = buttons.nextElement();
                    if (button.isSelected()) {
                        selected_Level = String.valueOf(i);
                    }
                }
                String upd = "UPDATE pracownicy "
                        + "SET poziom_uprawnien=" + selected_Level
                        +" WHERE id_pracownik=" + selected_Id;
                PreparedStatement delPra = con.prepareStatement(upd);
                delPra.executeUpdate();
                delPra.close();

                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "ZMODYFIKOWANO UPRAWNIENIA",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            con.close();

        }
        catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());

        }  catch (ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");
        }
    }
}