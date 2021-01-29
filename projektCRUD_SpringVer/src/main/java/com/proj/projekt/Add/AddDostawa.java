package com.proj.projekt.Add;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddDostawa extends AddClass {
    public AddDostawa() throws SQLException, IOException, ClassNotFoundException {
        super();
        try {
            JTextField iloscField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));

            java.util.List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from dostawcy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("nazwa");
                lista.add(t);
            }
            String[] dostawcyNazwy = new String[lista.size()];
            dostawcyNazwy = lista.toArray(dostawcyNazwy);
            JComboBox dostawcaBox = new JComboBox(dostawcyNazwy);
            dostawcaBox.setSelectedIndex(0);
            zapytanie.close();

            List<String> lista2=new ArrayList<String>();
            Statement zapytanie2 = con.createStatement();
            String sql2="select * from produkty";
            ResultSet wynik_sql2 = zapytanie2.executeQuery(sql2);
            while(wynik_sql2.next()) {
                String t=wynik_sql2.getString("nazwa");
                lista2.add(t);
            }
            String[] produktyNazwy = new String[lista2.size()];
            produktyNazwy = lista2.toArray(produktyNazwy);
            JComboBox produktyBox = new JComboBox(produktyNazwy);
            produktyBox.setSelectedIndex(0);
            zapytanie2.close();

            myPanel.add(new JLabel("DOSTAWCA:"));
            myPanel.add(dostawcaBox);
            myPanel.add(new JLabel("PRODUKT:"));
            myPanel.add(produktyBox);
            myPanel.add(new JLabel("ILOŚĆ:"));
            myPanel.add(iloscField);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date());
            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz szczegóły nowej dostawy", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Dostawca = lista.get(dostawcaBox.getSelectedIndex());
                Statement getDostawca = con.createStatement();
                String getDostawcaQ = "select * from dostawcy "
                        + "where nazwa = '" + selected_Dostawca + "'";
                ResultSet wynik_Dostawca = getDostawca.executeQuery(getDostawcaQ);
                wynik_Dostawca.next();
                int idDostawcy = wynik_Dostawca.getInt("id_dostawca");
                getDostawca.close();


                String selected_Produkt = lista2.get(produktyBox.getSelectedIndex());
                Statement getProdukt= con.createStatement();
                String getProduktQ = "select * from produkty "
                        + "where nazwa = '" + selected_Produkt + "'";
                ResultSet wynik_Produkt = getProdukt.executeQuery(getProduktQ);
                wynik_Produkt.next();
                int idProduktu = wynik_Produkt.getInt("id_produkt");
                getProdukt.close();

                String danePrac = "insert into dostawy values (?, ?, ?, ?)";
                PreparedStatement prep = con.prepareStatement(danePrac);
                prep.setInt(1, idDostawcy);
                prep.setInt(2, idProduktu);
                prep.setInt(3, Integer.parseInt(iloscField.getText()));
                prep.setString(4, date);
                prep.executeUpdate();
                prep.close();

                Statement check = con.createStatement();
                String checkQ = "select stan_na_magazynie from produkty "
                        + "where id_produkt = " + String.valueOf(idProduktu);
                ResultSet wynik_checkQ = check.executeQuery(checkQ);
                wynik_checkQ.next();
                int StanPoZamowieniu = Integer.parseInt(wynik_checkQ.getString("stan_na_magazynie")) + Integer.parseInt(iloscField.getText());
                check.close();

                String change = "UPDATE produkty "
                        + "SET stan_na_magazynie = " + String.valueOf(StanPoZamowieniu)
                        + " WHERE id_produkt = " + String.valueOf(idProduktu);
                PreparedStatement changeProdukty = con.prepareStatement(change);
                changeProdukty.executeUpdate();
                changeProdukty.close();
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO DOSTAWĘ",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            con.close();
        } catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());

        } catch (NumberFormatException error) {
            System.out.println(error.getMessage());
            JFrame errorFrame = new JFrame();
            errorFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
