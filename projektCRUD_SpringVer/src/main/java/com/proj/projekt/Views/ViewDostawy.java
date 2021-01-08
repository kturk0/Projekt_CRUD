package com.proj.projekt.Views;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewDostawy extends Wyswietlacz{
    public ViewDostawy()
    {
        super();
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");

            List<String[]> lista=new ArrayList<String[]>();
            Statement zapytanie = con.createStatement();
            String sql="select * from dostawy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);

            while(wynik_sql.next()) {


                Statement ask = con.createStatement();

                String dostawcaQ="select nazwa from dostawcy " +
                        "where id_dostawca = " + wynik_sql.getString("id_dostawcy") ;
                ResultSet wynik_dostawcaQ = ask.executeQuery(dostawcaQ);
                wynik_dostawcaQ.next();
                String nazwaD = wynik_dostawcaQ.getString("nazwa");

                String produktQ="select nazwa from produkty " +
                        "where id_produkt = " + wynik_sql.getString("id_produktu") ;
                ResultSet wynik_produktQ = ask.executeQuery(produktQ);
                wynik_produktQ.next();
                String nazwaP = wynik_produktQ.getString("nazwa");
                ask.close();
                String[] t={wynik_sql.getString("id_dostawa"), nazwaD, nazwaP, wynik_sql.getString("ilosc"), wynik_sql.getString("data_dostawy")};
                lista.add(t);
            }
            String array[][]=new String[lista.size()][];
            for (int i=0;i<array.length;i++){
                String[] row=lista.get(i);
                array[i]=row;
            }
            zapytanie.close();
            String[] columns={"ID","Dostawca","Produkt","ilość","data"};
            jt=new JTable(array,columns);
            TableColumnModel columnModel = jt.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(7);
            columnModel.getColumn(4).setPreferredWidth(15);
            setTable();
            sp=new JScrollPane(jt);
            frame.setTitle("Dostawy");
            frame.add(sp);
            frame.setLocation(650,50);
            frame.setSize(700,350);
            frame.setVisible(true);
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}
    }
}
