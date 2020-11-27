package Delete;


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeletePracownik {
    Connection con;
    public DeletePracownik()
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
                lista.add(t);
                lista.add(z);
                lista.add(y);
            }
            String[] pracownicyNazwy = new String[lista.size()/3];
            String idP = "un";
            String imieP = "un";
            String nazwiskoP;
            for(int i = 0; i < lista.size(); i++)
            {
                if(i%3 == 0)
                    idP = lista.get(i);
                else if (i%3 == 1)
                    imieP = lista.get(i);
                else if (i%3 == 2)
                {
                    nazwiskoP = lista.get(i);
                    pracownicyNazwy[i/3] = idP + " / " + imieP + " " + nazwiskoP;
                }
            }
            JComboBox pracownicyBox = new JComboBox(pracownicyNazwy);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("ID / IMIĘ NAZWISKO:"));
            myPanel.add(pracownicyBox);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz pracownika do usunięcia", JOptionPane.OK_CANCEL_OPTION);

            if (wynik == JOptionPane.OK_OPTION) {
                String selected_Id = lista.get(pracownicyBox.getSelectedIndex() * 3);

                String checkP = "SELECT count(*) FROM zamowienia "
                        + " WHERE id_pracownika = " + selected_Id;
                Statement checkPra = con.createStatement();
                ResultSet wynik_checkP = checkPra.executeQuery(checkP);
                wynik_checkP.next();
                int records = Integer.parseInt(wynik_checkP.getString(1));
                checkPra.close();

                if (records > 0)
                {
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "PRACOWNIK JEST WPISANY W " + records + " ZAMÓWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                            "Błąd", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    String del = "DELETE from pracownicy "
                            + " WHERE id_pracownik = " + selected_Id;
                    PreparedStatement delPra = con.prepareStatement(del);
                    delPra.executeUpdate();
                    delPra.close();

                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO PRACOWNIKA",
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
