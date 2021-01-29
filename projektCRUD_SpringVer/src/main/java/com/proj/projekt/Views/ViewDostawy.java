package com.proj.projekt.Views;

import com.proj.projekt.Chart.Barchart;
import com.proj.projekt.Chart.ChartDostawy;
import com.proj.projekt.Chart.ChartZamowienia;
import com.proj.projekt.Edit.EditDostawa;
import com.proj.projekt.Edit.EditZamowienie;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewDostawy extends Wyswietlacz{
    public ViewDostawy() throws SQLException, IOException, ClassNotFoundException {
        super();
        remakeTable();
        final JScrollPane[] sp = {new JScrollPane(jt)};
        sp[0].setPreferredSize(new Dimension(500,200));
        JButton editButton = new JButton("Edytuj zaznaczoną dostawę");
        JButton refreshButton = new JButton();
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream("refresh.png");
            Image img = ImageIO.read(in);
            in.close();
            Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH );
            ImageIcon icon = new ImageIcon(newimg);
            refreshButton.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(sp[0]);
                try {
                    remakeTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                sp[0] = new JScrollPane(jt);
                sp[0].setPreferredSize(new Dimension(500,200));
                frame.add(sp[0],BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });
        refreshButton.setFocusable(false);
        editButton.setSize(200,20);
        editButton.setFocusable(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = jt.getSelectedRow();
                if(row >= 0) {
                    String id = jt.getModel().getValueAt(row, 0).toString();
                    String ilosc = jt.getModel().getValueAt(row, 3).toString();
                    String data = jt.getModel().getValueAt(row, 4).toString();
                    String nazwa_produktu = jt.getModel().getValueAt(row, 2).toString();
                    try {
                        new EditDostawa(id, ilosc, data, nazwa_produktu);
                    } catch (SQLException | IOException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(editButton);
        buttonPanel.setSize(500, 30);
        Barchart barchart = new ChartDostawy();
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(barchart, BorderLayout.CENTER);
        innerPanel.add(buttonPanel, BorderLayout.PAGE_END);
        frame.setTitle("Dostawy");
        frame.add(innerPanel, BorderLayout.PAGE_START);
        frame.add(sp[0], BorderLayout.CENTER);
        frame.setLocation(680,50);
        frame.setSize(500,600);
        frame.pack();
        frame.setVisible(true);
    }

    public void remakeTable() throws SQLException {
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
        jt.setDefaultEditor(Object.class, null);
        TableColumnModel columnModel = jt.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(7);
        columnModel.getColumn(4).setPreferredWidth(15);
        setTable();
    }

}
