package com.proj.projekt.Delete;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteKlient extends DeleteClass {
    public DeleteKlient() throws SQLException, IOException, ClassNotFoundException {
        super();
        try {

            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from klienci";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("id_klient");
                String z=wynik_sql.getString("imie");
                String y=wynik_sql.getString("nazwisko");
                lista.add(t);
                lista.add(z);
                lista.add(y);
            }
            String[] klienciNazwy = new String[lista.size()/3];
            String idK = "un";
            String imieK = "un";
            String nazwiskoK;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%3 == 0)
                    idK = lista.get(i);
                else if (i%3 == 1)
                    imieK = lista.get(i);
                else if (i%3 == 2)
                {
                    nazwiskoK = lista.get(i);
                    klienciNazwy[i/3] = idK + " / " + imieK + " " + nazwiskoK;
                }
            }
            JComboBox klienciBox = new JComboBox(klienciNazwy);
            AutoCompleteDecorator.decorate(klienciBox);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("ID / IMIĘ NAZWISKO:"));
            myPanel.add(klienciBox);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz klienta do usunięcia", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(klienciBox.getSelectedIndex() * 3);

                String q = "SELECT count(*) FROM zamowienia "
                        + " WHERE id_klienta = " + selected_Id;
                Statement check = con.createStatement();
                ResultSet wynik_check = check.executeQuery(q);
                wynik_check.next();
                int records = Integer.parseInt(wynik_check.getString(1));
                check.close();

                if (records > 0)
                {
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "KLIENT JEST WPISANY W " + records + " ZAMÓWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                            "Błąd", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    String del = "DELETE from klienci "
                            + " WHERE id_klient = " + selected_Id;
                    PreparedStatement delKli = con.prepareStatement(del);
                    delKli.executeUpdate();
                    delKli.close();

                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO KLIENTA",
                            "Sukces", JOptionPane.PLAIN_MESSAGE);
                }
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

        }
    }
}
