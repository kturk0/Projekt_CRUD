package Add;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddProdukt {
    Connection con;
    public AddProdukt()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from kategorie";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("nazwa_kategorii");
                lista.add(t);
            }
            String[] kategorieNazwy = new String[lista.size()];
            kategorieNazwy = lista.toArray(kategorieNazwy);
            JComboBox kategorieBox = new JComboBox(kategorieNazwy);
            kategorieBox.setSelectedIndex(0);
            zapytanie.close();

            JTextField nazwaField = new JTextField(5);
            JTextField opisField = new JTextField(5);
            JTextField cenaField = new JTextField(5);
            JTextField stanField = new JTextField(5);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("NAZWA:"));
            myPanel.add(nazwaField);
            myPanel.add(new JLabel("OPIS:"));
            myPanel.add(opisField);
            myPanel.add(new JLabel("KATEGORIA:"));
            myPanel.add(kategorieBox);
            myPanel.add(new JLabel("CENA:"));
            myPanel.add(cenaField);
            myPanel.add(new JLabel("STAN NA MAGAZYNIE"));
            myPanel.add(stanField);
            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego produktu", JOptionPane.OK_CANCEL_OPTION);
            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Kategoria = lista.get(kategorieBox.getSelectedIndex());
                Statement getKategoria= con.createStatement();
                String getKategoriaQ = "select * from kategorie "
                        + "where nazwa_kategorii = '" + selected_Kategoria + "'";
                ResultSet wynik_Kategoria = getKategoria.executeQuery(getKategoriaQ);
                wynik_Kategoria.next();
                int idKategoria = wynik_Kategoria.getInt("id_kategoria");
                getKategoria.close();
                String danePrac = "insert into produkty values (?, ?, ?, ?, ?)";
                PreparedStatement prep = con.prepareStatement(danePrac);
                prep.setString(1, nazwaField.getText());
                prep.setString(2, opisField.getText());
                prep.setInt(3, idKategoria);
                prep.setDouble(4, Double.parseDouble(cenaField.getText()));
                prep.setInt(5, Integer.parseInt(stanField.getText()));
                prep.executeUpdate();
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO PRODUKT",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
                prep.close();
            }
            con.close();
        } catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());
            JFrame errorFrame = new JFrame();
            errorFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
