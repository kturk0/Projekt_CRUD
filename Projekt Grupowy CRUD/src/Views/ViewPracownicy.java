package Views;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewPracownicy extends Wyswietlacz {
    public ViewPracownicy()
    {
        super();
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");
            List<String[]> lista=new ArrayList<String[]>();
            Statement zapytanie = con.createStatement();
            String sql="select * from pracownicy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);

            while(wynik_sql.next()) {
                String[] t={wynik_sql.getString("id_pracownik"),wynik_sql.getString("imie"),wynik_sql.getString("nazwisko"),
                        wynik_sql.getString("data_ur"),wynik_sql.getString("wynagrodzenie_brutto"), wynik_sql.getString("logiin"),
                        wynik_sql.getString("haslo"),wynik_sql.getString("id_adresu")};
                lista.add(t);
            }
            String array[][]=new String[lista.size()][];
            for (int i=0;i<array.length;i++){
                String[] row=lista.get(i);
                array[i]=row;
            }
            zapytanie.close();
            String[] columns={"ID pracownika","Imię","Nazwisko","Data ur.","Wynagrodenie brutto","Login","Hasło","ID adresu"};
            jt=new JTable(array,columns);
            setTable();
            sp=new JScrollPane(jt);
            frame.setTitle("Pracownicy");
            frame.add(sp);
            frame.setLocation(550,50);
            frame.setSize(700,350);
            frame.setVisible(true);
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println("Błąd połączenia z bazą danych");}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}
    }
}
