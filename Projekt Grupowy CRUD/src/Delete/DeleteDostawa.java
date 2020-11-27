package Delete;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteDostawa {
    Connection con;
    public DeleteDostawa()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from dostawy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("id_dostawa");
                String z=wynik_sql.getString("data_dostawy");

                lista.add(t);
                lista.add(z);
            }
            String[] dostawyNazwy = new String[lista.size()/2];
            String idD = "un";
            String dataD;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%2 == 0)
                    idD = lista.get(i);
                else if (i%2 == 1)
                {
                    dataD = lista.get(i);
                    dostawyNazwy[i/2] = idD + " / " + dataD;
                }
            }
            JComboBox dostawyBox = new JComboBox(dostawyNazwy);
            AutoCompleteDecorator.decorate(dostawyBox);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("ID / DATA:"));
            myPanel.add(dostawyBox);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz dostawę do usunięcia", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(dostawyBox.getSelectedIndex() * 2);
                String selected_Ilosc = lista.get(dostawyBox.getSelectedIndex() * 2 + 1);
                Statement getDostawy = con.createStatement();
                String getDostawyQ = "select * from dostawy "
                        + "where id_dostawa = " + selected_Id ;
                ResultSet wynik_Dostawy = getDostawy.executeQuery(getDostawyQ);
                wynik_Dostawy.next();
                int idDostawy = wynik_Dostawy.getInt("id_dostawa");
                int idProduktu = wynik_Dostawy.getInt("id_produktu");
                int iloscZ = wynik_Dostawy.getInt("ilosc");
                getDostawy.close();

                Statement getProdukty = con.createStatement();
                String getMebleQ = "select * from produkty "
                        + "where id_produkt = " + String.valueOf(idProduktu) ;
                ResultSet wynik_Produkty = getProdukty.executeQuery(getMebleQ);
                wynik_Produkty.next();
                int ilosc = wynik_Produkty.getInt("stan_na_magazynie");
                getProdukty.close();

                iloscZ = ilosc - iloscZ;

                String change = "UPDATE produkty "
                        + "SET stan_na_magazynie = " + String.valueOf(iloscZ)
                        + " WHERE id_produkt = " + String.valueOf(idProduktu);
                PreparedStatement changeProdukty = con.prepareStatement(change);
                changeProdukty.executeUpdate();
                changeProdukty.close();

                String del = "DELETE from dostawy "
                        + " WHERE id_dostawa = " + String.valueOf(idDostawy);
                PreparedStatement delDos = con.prepareStatement(del);
                delDos.executeUpdate();
                delDos.close();

                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO DOSTAWĘ",
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
