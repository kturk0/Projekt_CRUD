package com.proj.projekt.Delete;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteZamowienie {
    Connection con;
    public DeleteZamowienie()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from zamowienia";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("id_zamowienie");
                String z=wynik_sql.getString("data_zamowienia");

                lista.add(t);
                lista.add(z);
            }
            String[] zamowieniaNazwy = new String[lista.size()/2];
            String idZ = "un";
            String dataZ;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%2 == 0)
                    idZ = lista.get(i);
                else if (i%2 == 1)
                {
                    dataZ = lista.get(i);
                    zamowieniaNazwy[i/2] = idZ + " / " + dataZ;
                }
            }
            JComboBox zamowieniaBox = new JComboBox(zamowieniaNazwy);
            AutoCompleteDecorator.decorate(zamowieniaBox);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("ID / DATA ZŁOŻENIA:"));
            myPanel.add(zamowieniaBox);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz zamówienie do usunięcia", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(zamowieniaBox.getSelectedIndex() * 2);
                String selected_Ilosc = lista.get(zamowieniaBox.getSelectedIndex() * 2 + 1);
                Statement getZamowienia = con.createStatement();
                String getZamowieniaQ = "select * from zamowienia "
                        + "where id_zamowienie = " + selected_Id ;
                ResultSet wynik_Zamowienia = getZamowienia.executeQuery(getZamowieniaQ);
                wynik_Zamowienia.next();
                int idZamowienia = wynik_Zamowienia.getInt("id_zamowienie");
                int idProduktu = wynik_Zamowienia.getInt("id_produktu");
                int iloscZ = wynik_Zamowienia.getInt("ilosc");
                getZamowienia.close();

                Statement getProdukty = con.createStatement();
                String getProduktyQ = "select * from produkty "
                        + "where id_produkt = " + String.valueOf(idProduktu) ;
                ResultSet wynik_Produkty = getProdukty.executeQuery(getProduktyQ);
                wynik_Produkty.next();
                int ilosc = wynik_Produkty.getInt("stan_na_magazynie");
                getProdukty.close();

                iloscZ += ilosc;

                String change = "UPDATE produkty "
                        + "SET stan_na_magazynie = " + String.valueOf(iloscZ)
                        + " WHERE id_produkt = " + String.valueOf(idProduktu);
                PreparedStatement changeProdukty = con.prepareStatement(change);
                changeProdukty.executeUpdate();
                changeProdukty.close();

                String del = "DELETE from zamowienia "
                        + " WHERE id_zamowienie = " + String.valueOf(idZamowienia);
                PreparedStatement delZam = con.prepareStatement(del);
                delZam.executeUpdate();
                delZam.close();

                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO ZAMÓWIENIE",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);

            }
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
    }
}
