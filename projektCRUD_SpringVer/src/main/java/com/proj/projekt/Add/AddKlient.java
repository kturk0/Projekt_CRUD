package com.proj.projekt.Add;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AddKlient extends AddClass {

    public AddKlient() throws SQLException, IOException, ClassNotFoundException {
        super();
        try {

            JTextField imieField = new JTextField(5);
            JTextField nazwiskoField = new JTextField(5);
            JDateChooser dateChooser = new JDateChooser();
            JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
            editor.setEditable(false);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0, 1));
            myPanel.add(new JLabel("IMIĘ:"));
            myPanel.add(imieField);
            //  myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("NAZWISKO:"));
            myPanel.add(nazwiskoField);
            myPanel.add(new JLabel("DATA UR (YYYY-MM-DD):"));
            myPanel.add(dateChooser);

            int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego klienta", JOptionPane.OK_CANCEL_OPTION);
            if (wynik == JOptionPane.OK_OPTION) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String danePrac = "insert into klienci values (?, ?, ?)";
                PreparedStatement prep = con.prepareStatement(danePrac);
                prep.setString(1, imieField.getText());
                prep.setString(2, nazwiskoField.getText());
                prep.setString(3, formatter.format(dateChooser.getDate()));

                prep.executeUpdate();
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO KLIENTA",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
                prep.close();
            }
            con.close();
        } catch (SQLException | NumberFormatException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());
            JFrame errorFrame = new JFrame();
            errorFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}