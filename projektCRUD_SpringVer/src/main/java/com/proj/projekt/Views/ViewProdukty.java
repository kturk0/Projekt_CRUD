package com.proj.projekt.Views;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewProdukty extends Wyswietlacz {
    public ViewProdukty() throws SQLException, ClassNotFoundException, IOException {
        super();

        List<String[]> lista=new ArrayList<String[]>();
        Statement zapytanie = con.createStatement();
        String sql="select * from produkty";
        ResultSet wynik_sql = zapytanie.executeQuery(sql);

        while(wynik_sql.next()) {
            Statement ask = con.createStatement();
            String kategoriaQ="select nazwa_kategorii from kategorie " +
                    "where id_kategoria = " + wynik_sql.getString("id_kategorii") ;
            ResultSet wynik_kategoriaQ = ask.executeQuery(kategoriaQ);
            wynik_kategoriaQ.next();
            String kateg = wynik_kategoriaQ.getString("nazwa_kategorii");

            ask.close();
            String[] t={wynik_sql.getString("id_produkt"), wynik_sql.getString("nazwa"), wynik_sql.getString("opis"),
                    kateg, wynik_sql.getString("cena"), wynik_sql.getString("stan_na_magazynie")};
            lista.add(t);
        }
        String array[][]=new String[lista.size()][];
        for (int i=0;i<array.length;i++){
            String[] row=lista.get(i);
            array[i]=row;
        }
        zapytanie.close();

        String[] columns={"ID","nazwa","opis","kategoria","cena","stan na magazynie"};

        jt=new JTable(array,columns);
        TableColumnModel columnModel = jt.getColumnModel();
        jt.setRowHeight(jt.getRowHeight() + 5);
        columnModel.getColumn(0).setPreferredWidth(5);
        columnModel.getColumn(3).setPreferredWidth(25);
        columnModel.getColumn(4).setPreferredWidth(15);
        setTable();
        sp=new JScrollPane(jt);
        frame.setTitle("Produkty");
        frame.add(sp);
        frame.setLocation(670,50);
        frame.setSize(700,350);
        frame.setVisible(true);
        con.close();
    }

}
