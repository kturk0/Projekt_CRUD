package com.proj.projekt.Edit;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDostawa extends EditClass {
    public EditDostawa(String id_dostawy, String ilosc, String data, String nazwa_produktu) throws SQLException, IOException, ClassNotFoundException {
        super();
        try {
            JTextField iloscField = new JTextField(ilosc,5);
            JDateChooser dateChooser = new JDateChooser();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateToSet = formatter.parse(data);
            dateChooser.setDate(dateToSet);
            JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
            editor.setEditable(false);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("DATA:"));
            myPanel.add(dateChooser);
            myPanel.add(new JLabel("ILOŚĆ:"));
            myPanel.add(iloscField);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Edytuj dostawę", JOptionPane.OK_CANCEL_OPTION);


            if (wynik == JOptionPane.OK_OPTION) {
                Statement getProdukt= con.createStatement();
                String getProduktQ = "select * from produkty "
                        + "where nazwa = '" + nazwa_produktu + "'";
                ResultSet wynik_Produkt = getProdukt.executeQuery(getProduktQ);
                wynik_Produkt.next();
                int idProduktu = wynik_Produkt.getInt("id_produkt");
                getProdukt.close();

                Statement check = con.createStatement();
                String checkQ = "select stan_na_magazynie from produkty "
                        + "where id_produkt = " + String.valueOf(idProduktu);
                ResultSet wynik_checkQ = check.executeQuery(checkQ);
                wynik_checkQ.next();
                int StanPoZamowieniu = Integer.parseInt(wynik_checkQ.getString("stan_na_magazynie")) + (Integer.parseInt(ilosc) - Integer.parseInt(iloscField.getText()));
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
                    String danePrac = "update dostawy set ilosc = ?, data_dostawy = ? where id_dostawa = ?";
                    PreparedStatement prep = con.prepareStatement(danePrac);
                    prep.setInt(1, Integer.parseInt(iloscField.getText()));
                    prep.setString(2, formatter.format(dateChooser.getDate()));
                    prep.setInt(3, Integer.parseInt(id_dostawy));
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
                    JOptionPane.showMessageDialog(msgFrame, "ZEDYTOWANO DOSTAWĘ",
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

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
