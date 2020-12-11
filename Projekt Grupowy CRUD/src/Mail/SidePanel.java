package Mail;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SidePanel extends JPanel {
    Connection con;
    ButtonGroup mailButtons;
    JPanel gridPanel;
    List<Integer> mailIDs;
    int userID;
    String sql;
    public SidePanel(int userID, int type) throws ClassNotFoundException {
        try {
            this.userID = userID;
            this.sql = sql;
            setLayout(new BorderLayout());
            gridPanel= new JPanel();
            gridPanel.setLayout(new GridLayout(0,1));
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"
                    + "localhost:1433;databaseName=dbo;"
                    + "user=sa;password=haslosql;");
            mailIDs = new ArrayList<>();
            mailButtons = new ButtonGroup();
            List<String> buttonTexts=new ArrayList<String>();
            Statement query = con.createStatement();
            if(userID == -1)
                sql="select * from mails";
            else if(type == 0)
                sql="select * from mails where id_odbiorcy = " + userID;
            else
                sql="select * from mails where id_nadawcy = " + userID;
            ResultSet wynik_sql = query.executeQuery(sql);
            JToggleButton newButton;
            while(wynik_sql.next()) {
                mailIDs.add(wynik_sql.getInt("id_mail"));
                newButton = new JToggleButton( wynik_sql.getString("title"));
                newButton.setFocusable(false);
                newButton.setBackground(Color.LIGHT_GRAY);
                gridPanel.add(newButton);
                mailButtons.add(newButton);
            }
            add(gridPanel, BorderLayout.PAGE_START);
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