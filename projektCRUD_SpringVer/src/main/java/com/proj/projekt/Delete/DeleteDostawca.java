package com.proj.projekt.Delete;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteDostawca extends DeleteClass {
    public DeleteDostawca() throws SQLException, IOException, ClassNotFoundException {
        super();
        try {
            List<String> lista=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from dostawcy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("id_dostawca");
                String z=wynik_sql.getString("nazwa");

                lista.add(t);
                lista.add(z);
            }
            String[] dostawcyNazwy = new String[lista.size()/2];
            String idD = "un";
            String nazwaD;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%2 == 0)
                    idD = lista.get(i);
                else if (i%2 == 1)
                {
                    nazwaD = lista.get(i);
                    dostawcyNazwy[i/2] = idD + " / " + nazwaD;
                }
            }
            JComboBox dostawcyBox = new JComboBox(dostawcyNazwy);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("ID / NAZWA:"));
            myPanel.add(dostawcyBox);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz dostawcę do usunięcia", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(dostawcyBox.getSelectedIndex() * 2);
                String q = "SELECT count(*) FROM dostawy "
                        + " WHERE id_dostawcy = " + selected_Id;
                Statement check = con.createStatement();
                ResultSet wynik_check = check.executeQuery(q);
                wynik_check.next();
                int records = Integer.parseInt(wynik_check.getString(1));
                check.close();
                if (records > 0)
                {
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "DOSTAWCA JEST WPISANY W " + records + " DOSTAWACH\nUSUNIĘCIE NIEMOŻLIWE",
                            "Błąd", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    String del = "DELETE from dostawcy "
                            + " WHERE id_dostawca = " + selected_Id;
                    PreparedStatement delDost = con.prepareStatement(del);
                    delDost.executeUpdate();
                    delDost.close();

                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO DOSTAWCĘ",
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
