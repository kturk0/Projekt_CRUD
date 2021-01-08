package com.proj.projekt.Views;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewDostawcy extends Wyswietlacz {
    public ViewDostawcy()
    {
        super();
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");

            List<String[]> lista=new ArrayList<String[]>();
            Statement zapytanie = con.createStatement();
            String sql="select * from dostawcy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);

            while(wynik_sql.next()) {
                String[] t={wynik_sql.getString("id_dostawca"),wynik_sql.getString("nazwa"),wynik_sql.getString("stawka_za_km"),
                        wynik_sql.getString("id_siedziby")};
                lista.add(t);
            }
            String array[][]=new String[lista.size()][];
            for (int i=0;i<array.length;i++){
                String[] row=lista.get(i);
                array[i]=row;
            }
            zapytanie.close();

            String[] columns={"ID dostawcy","Nazwa","Stawka za km","ID siedziby"};

            jt=new JTable(array,columns);
            setTable();
            sp=new JScrollPane(jt);
            frame.setTitle("Dostawcy");
            frame.add(sp);
            frame.setVisible(true);
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println("Błąd połączenia z bazą danych");}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}
    }
}
