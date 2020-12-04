package Mail;


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SidePanel extends JPanel {
    Connection con;
    ButtonGroup mailButtons;
    List<Integer> mailIDs;
    public SidePanel() throws ClassNotFoundException {
        try {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");
            mailIDs = new ArrayList<>();
            mailButtons = new ButtonGroup();
            List<String> buttonTexts=new ArrayList<String>();
            Statement query = con.createStatement();
            String sql="select * from mails";
            ResultSet wynik_sql = query.executeQuery(sql);
            JToggleButton newButton;
            while(wynik_sql.next()) {
                mailIDs.add(wynik_sql.getInt("id_mail"));
                newButton = new JToggleButton( wynik_sql.getString("title"));
                add(newButton);
                mailButtons.add(newButton);
            }

            con.close();
        }
        catch (SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());

        }
        catch (ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");
        }
    }
}
