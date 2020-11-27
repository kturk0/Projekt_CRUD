package Delete;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteProdukt {
    Connection con;
    public DeleteProdukt()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from meble";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("id_mebel");
                String z=wynik_sql.getString("nazwa");

                lista.add(t);
                lista.add(z);
            }
            String[] productNames = new String[lista.size()/2];
            String idM = "un";
            String nazwaM;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%2 == 0)
                    idM = lista.get(i);
                else if (i%2 == 1)
                {
                    nazwaM = lista.get(i);
                    productNames[i/2] = idM + " / " + nazwaM;
                }
            }
            JComboBox produktyBox = new JComboBox(productNames);
            AutoCompleteDecorator.decorate(produktyBox);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("ID / NAZWA:"));
            myPanel.add(produktyBox);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz produkt do usunięcia", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(produktyBox.getSelectedIndex() * 2);
                String q = "SELECT count(*) FROM dostawy "
                        + " WHERE id_produktu = " + selected_Id;
                Statement check = con.createStatement();
                ResultSet wynik_check = check.executeQuery(q);
                wynik_check.next();
                int records1 = Integer.parseInt(wynik_check.getString(1));
                check.close();
                String r = "SELECT count(*) FROM zamowienia "
                        + " WHERE id_produktu = " + selected_Id;
                Statement check2 = con.createStatement();
                ResultSet wynik_check2 = check2.executeQuery(r);
                wynik_check2.next();
                int records2 = Integer.parseInt(wynik_check2.getString(1));
                check.close();

                if (records1 > 0 && records2 > 0)
                {
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    if (records1 == 1 && records2 == 1)
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records1 + " DOSTAWIE I "
                                        + records2 + " ZAMOWIENIU\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    else if (records1 > 1 && records2 == 1)
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records1 + " DOSTAWACH I "
                                        + records2 + " ZAMOWIENIU\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    else if (records1 == 1 && records2 > 1)
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records1 + " DOSTAWIE I "
                                        + records2 + " ZAMOWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records1 + " DOSTAWACH I "
                                        + records2 + " ZAMOWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                }
                else if (records1 > 0)
                {
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    if (records1 == 1)
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records1 + " DOSTAWIE\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records1 + " DOSTAWACH\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                }
                else if (records2 > 0)
                {
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    if (records2 == 1)
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records2 + " ZAMÓWIENIU\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(msgFrame, "PRODUKT JEST WPISANY W " + records2 + " ZAMÓWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String del = "DELETE from produkty "
                            + " WHERE id_produkt = " + selected_Id;
                    PreparedStatement delMeb = con.prepareStatement(del);
                    delMeb.executeUpdate();
                    delMeb.close();

                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO PRODUKT",
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

        } catch (ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");
        }
    }
}
