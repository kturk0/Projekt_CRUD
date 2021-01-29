package com.proj.projekt.Add;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddZamowienie extends AddClass {
    public AddZamowienie(int id_pracownik) throws SQLException, IOException, ClassNotFoundException {
        super();
        try {
            JTextField iloscField = new JTextField(5);

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

            List<String> listaK=new ArrayList<String>();
            Statement zapytanieK = con.createStatement();
            String sqlK="select * from klienci";
            ResultSet wynik_sqlK = zapytanieK.executeQuery(sqlK);
            while(wynik_sqlK.next()) {
                String y=wynik_sqlK.getString("id_klient");
                String t=wynik_sqlK.getString("imie");
                String z=wynik_sqlK.getString("nazwisko");
                listaK.add(y);
                listaK.add(t);
                listaK.add(z);
            }
            String[] klienciNazwy = new String[listaK.size()/3];
            String idK = "un";
            String imieK = "un";
            String nazwiskoK;
            for(int i = 0; i < listaK.size(); i++)
            {
                if(i%3 == 0)
                    idK = listaK.get(i);
                else if (i%3 == 1)
                    imieK = listaK.get(i);
                else if (i%3 == 2)
                {
                    nazwiskoK = listaK.get(i);
                    klienciNazwy[i/3] = idK + " / " + imieK + " " + nazwiskoK;
                }
            }
            JComboBox klienciBox = new JComboBox(klienciNazwy);
            klienciBox.setSelectedIndex(0);
            zapytanieK.close();

            List<String> listaP=new ArrayList<String>();
            Statement zapytanieP = con.createStatement();
            String sqlP="select * from pracownicy";
            ResultSet wynik_sqlP = zapytanieP.executeQuery(sqlP);
            while(wynik_sqlP.next()) {
                String t=wynik_sqlP.getString("imie");
                String z=wynik_sqlP.getString("nazwisko");
                listaP.add(t);
                listaP.add(z);
            }
            String[] pracownicyNazwy = new String[listaP.size()/2];
            String imieP = "un";
            String nazwiskoP;
            for(int i = 0; i < listaP.size(); i++)
            {
                if(i%2 == 0)
                    imieP = listaP.get(i);
                else {
                    nazwiskoP = listaP.get(i);
                    pracownicyNazwy[i/2] = imieP + " " + nazwiskoP;
                }
            }
            JComboBox pracownicyBox = new JComboBox(pracownicyNazwy);
            pracownicyBox.setSelectedIndex(0);
            zapytanieP.close();


            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("KLIENT:"));
            myPanel.add(klienciBox);
            //  myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            if(id_pracownik == 0) {
                myPanel.add(new JLabel("PRACOWNIK:"));
                myPanel.add(pracownicyBox);
            }
            myPanel.add(new JLabel("PRODUKT:"));
            myPanel.add(produktyBox);
            myPanel.add(new JLabel("ILOŚĆ:"));
            myPanel.add(iloscField);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date());
            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz szczegóły nowego zamówienia", JOptionPane.OK_CANCEL_OPTION);


            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Produkt = lista2.get(produktyBox.getSelectedIndex());
                Statement getProdukt= con.createStatement();
                String getProduktQ = "select * from produkty "
                        + "where nazwa = '" + selected_Produkt + "'";
                ResultSet wynik_Produkt = getProdukt.executeQuery(getProduktQ);
                wynik_Produkt.next();
                int idProduktu = wynik_Produkt.getInt("id_produkt");
                getProdukt.close();

                Statement check = con.createStatement();
                String checkQ = "select stan_na_magazynie from produkty "
                        + "where id_produkt = " + String.valueOf(idProduktu);
                ResultSet wynik_checkQ = check.executeQuery(checkQ);
                wynik_checkQ.next();
                int StanPoZamowieniu = Integer.parseInt(wynik_checkQ.getString("stan_na_magazynie")) - Integer.parseInt(iloscField.getText());
                check.close();
                if (StanPoZamowieniu < 0)
                {
                    JFrame errorFrame = new JFrame();
                    errorFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(errorFrame, "ZA MAŁY STAN NA MAGAYNIE!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    int idPracownika = id_pracownik;
                    if(id_pracownik > 0) {
                        String selected_Imie = listaP.get(pracownicyBox.getSelectedIndex() * 2);
                        String selected_Nazwisko = listaP.get(pracownicyBox.getSelectedIndex() * 2 + 1);
                        Statement getPracownik = con.createStatement();
                        String getPracownikQ = "select * from pracownicy "
                                + "where imie = '" + selected_Imie + "' AND nazwisko = '" + selected_Nazwisko + "'";
                        ResultSet wynik_Pracownik = getPracownik.executeQuery(getPracownikQ);
                        wynik_Pracownik.next();
                        idPracownika = wynik_Pracownik.getInt("id_pracownik");
                        getPracownik.close();
                    }
                    String idKlienta = listaK.get(klienciBox.getSelectedIndex()*3);

                    String danePrac = "insert into zamowienia values (?, ?, ?, ?, ?)";
                    PreparedStatement prep = con.prepareStatement(danePrac);
                    prep.setInt(1, Integer.parseInt(idKlienta));
                    prep.setInt(2, idPracownika);
                    prep.setInt(3, idProduktu);
                    prep.setInt(4, Integer.parseInt(iloscField.getText()));
                    prep.setString(5, date);
                    prep.executeUpdate();
                    prep.close();

                    String change = "UPDATE produkty "
                            + "SET stan_na_magazynie = " + String.valueOf(StanPoZamowieniu)
                            + " WHERE id_produkt = " + String.valueOf(idProduktu);
                    PreparedStatement changeProdukty = con.prepareStatement(change);
                    changeProdukty.executeUpdate();
                    changeProdukty.close();
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "DODANO ZAMÓWIENIE",
                            "Sukces", JOptionPane.PLAIN_MESSAGE);
                }
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
