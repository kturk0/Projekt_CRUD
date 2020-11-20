package Add;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddKlient {
    Connection con;

    public AddKlient() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");

            JTextField imieField = new JTextField(5);
            JTextField nazwiskoField = new JTextField(5);
            JTextField data_urField = new JTextField(5);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("IMIĘ:"));
            myPanel.add(imieField);
            //  myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("NAZWISKO:"));
            myPanel.add(nazwiskoField);
            myPanel.add(new JLabel("DATA UR (YYYY-MM-DD):"));
            myPanel.add(data_urField);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego klienta", JOptionPane.OK_CANCEL_OPTION);
            if (wynik == JOptionPane.OK_OPTION) {
                String danePrac = "insert into klienci values (?, ?, ?, ? , ?)";
                PreparedStatement prep = con.prepareStatement(danePrac);
                prep.setString(1, imieField.getText());
                prep.setString(2, nazwiskoField.getText());
                prep.setString(3, data_urField.getText());

                prep.executeUpdate();
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO KLIENTA",
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