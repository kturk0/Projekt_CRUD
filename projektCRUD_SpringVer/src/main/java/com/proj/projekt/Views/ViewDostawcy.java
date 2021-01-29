package com.proj.projekt.Views;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class ViewDostawcy extends Wyswietlacz {
    public ViewDostawcy() throws SQLException, IOException, ClassNotFoundException {
        super();

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
}
