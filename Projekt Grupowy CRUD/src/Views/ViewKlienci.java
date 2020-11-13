package Views;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewKlienci extends Wyswietlacz {
    public ViewKlienci()
    {
        super();
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");

            List<String[]> lista=new ArrayList<String[]>();
            Statement zapytanie = con.createStatement();
            String sql="select * from klienci";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            ResultSetMetaData wynik_kol = wynik_sql.getMetaData();
            int ile_kolumn = wynik_kol.getColumnCount();
            while(wynik_sql.next()) {
                String[] t={wynik_sql.getString("id_klient"),wynik_sql.getString("imie"),
                        wynik_sql.getString("nazwisko"),wynik_sql.getString("data_ur")};
                lista.add(t);
            }
            String array[][]=new String[lista.size()][];
            for (int i=0;i<array.length;i++){
                String[] row=lista.get(i);
                array[i]=row;
            }
            zapytanie.close();
            String[] columns={"ID klienta","Imię","Nazwisko","Data ur."};
            jt=new JTable(array,columns);
            setTable();
            sp=new JScrollPane(jt);
            frame.setTitle("Klienci");
            frame.add(sp);
            frame.setLocation(600,50);
            frame.setSize(400,350);
            frame.setVisible(true);
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println("Błąd połączenia z bazą danych");}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}
    }
}
