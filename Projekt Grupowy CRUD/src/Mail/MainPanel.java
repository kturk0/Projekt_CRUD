package Mail;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MainPanel extends JPanel {
    Connection con;
    JLabel titleLabel;
    JLabel textLabel;
    public MainPanel(int mailID)
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");
            Statement query = con.createStatement();
            String qr = "select * from mails"
                    + " where id_mail = " + mailID;;
            ResultSet queryResult = query.executeQuery(qr);
            queryResult.next();
            titleLabel = new JLabel(queryResult.getString("title"), SwingConstants.CENTER);
            textLabel = new JLabel(queryResult.getString("body"), SwingConstants.CENTER);
            query.close();
            setLayout(new BorderLayout());
            add(titleLabel, BorderLayout.NORTH);
            add(textLabel, BorderLayout.CENTER);
            con.close();
        }
        catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());
        }
        catch (NumberFormatException error) {
            System.out.println(error.getMessage());
            JFrame errorFrame = new JFrame();
            errorFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE ID MAILA!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");
        }
    }
}
