package com.proj.projekt.Views;

import com.proj.projekt.Chart.Barchart;
import com.proj.projekt.Chart.ChartZamowienia;
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

public class ViewZamowienia extends Wyswietlacz {


    public ViewZamowienia() throws SQLException, IOException, ClassNotFoundException {
        super();
        remakeTable();
        final JScrollPane[] sp = {new JScrollPane(jt)};
        sp[0].setPreferredSize(new Dimension(500,200));
        JButton editButton = new JButton("Edytuj zaznaczone zamówienie");
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
                    String ilosc = jt.getModel().getValueAt(row, 4).toString();
                    String data = jt.getModel().getValueAt(row, 6).toString();
                    String nazwa_produktu = jt.getModel().getValueAt(row, 3).toString();
                    try {
                        new EditZamowienie(id, ilosc, data, nazwa_produktu);
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
        frame.setTitle("Zamówienia");
        Barchart barchart = new ChartZamowienia();
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(barchart, BorderLayout.CENTER);
        innerPanel.add(buttonPanel, BorderLayout.PAGE_END);
        frame.add(innerPanel, BorderLayout.PAGE_START);
        frame.add(sp[0], BorderLayout.CENTER);
        frame.setLocation(680,50);
        frame.setSize(500,600);
        frame.pack();
        frame.setVisible(true);
    }
    private void remakeTable() throws SQLException {
        List<String[]> lista=new ArrayList<String[]>();
        Statement zapytanie = con.createStatement();
        String sql="select * from zamowienia";
        ResultSet wynik_sql = zapytanie.executeQuery(sql);
        while(wynik_sql.next()) {
            Statement ask = con.createStatement();
            String id = wynik_sql.getString("id_zamowienie");
            String klientQ="select imie, nazwisko from klienci " +
                    "where id_klient = " + wynik_sql.getString("id_klienta") ;
            ResultSet wynik_klientQ = ask.executeQuery(klientQ);
            wynik_klientQ.next();
            String imieNaz1 = wynik_klientQ.getString("imie") + " " + wynik_klientQ.getString("nazwisko");

            String pracownikQ="select imie, nazwisko from pracownicy " +
                    "where id_pracownik = " + wynik_sql.getString("id_pracownika") ;
            ResultSet wynik_pracownikQ = ask.executeQuery(pracownikQ);
            wynik_pracownikQ.next();
            String imieNaz2 = wynik_pracownikQ.getString("imie") + " " + wynik_pracownikQ.getString("nazwisko");

            String produktQ="select nazwa, cena from produkty " +
                    "where id_produkt = " + wynik_sql.getString("id_produktu") ;
            ResultSet wynik_produktQ = ask.executeQuery(produktQ);
            wynik_produktQ.next();
            String nazw = wynik_produktQ.getString("nazwa");

            String il = wynik_sql.getString("ilosc");
            String data = wynik_sql.getString("data_zamowienia");
            Double wartosc = Math.round(Double.parseDouble(il) * Double.parseDouble(wynik_produktQ.getString("cena")) * 100.0) / 100.0;

            ask.close();
            String[] t={id, imieNaz1, imieNaz2, nazw, il, String.valueOf(wartosc), data};
            lista.add(t);
        }
        String array[][]=new String[lista.size()][];
        for (int i=0;i<array.length;i++){
            String[] row=lista.get(i);
            array[i]=row;
        }
        zapytanie.close();



        String[] columns={"ID","Klient","Pracownik","Produkt","ilość","wartość","data"};

        jt=new JTable(array,columns);
        jt.setDefaultEditor(Object.class, null);
        jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableColumnModel columnModel = jt.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(7);
        columnModel.getColumn(4).setPreferredWidth(15);
        setTable();
    }


    public ViewZamowienia(int idK) throws SQLException, IOException, ClassNotFoundException {
        super();
        List<String[]> lista=new ArrayList<String[]>();
        Statement zapytanie = con.createStatement();
        String sql="select * from zamowienia where id_klienta = " + idK;
        ResultSet wynik_sql = zapytanie.executeQuery(sql);

        while(wynik_sql.next()) {
            Statement ask = con.createStatement();
            String id = wynik_sql.getString("id_zamowienie");
            String klientQ="select imie, nazwisko from klienci " +
                    "where id_klient = " + wynik_sql.getString("id_klienta") ;
            ResultSet wynik_klientQ = ask.executeQuery(klientQ);
            wynik_klientQ.next();
            String imieNaz1 = wynik_klientQ.getString("imie") + " " + wynik_klientQ.getString("nazwisko");

            String pracownikQ="select imie, nazwisko from pracownicy " +
                    "where id_pracownik = " + wynik_sql.getString("id_pracownika") ;
            ResultSet wynik_pracownikQ = ask.executeQuery(pracownikQ);
            wynik_pracownikQ.next();
            String imieNaz2 = wynik_pracownikQ.getString("imie") + " " + wynik_pracownikQ.getString("nazwisko");

            String mebelQ="select nazwa, cena from produkty " +
                    "where id_produkt = " + wynik_sql.getString("id_produkt") ;
            ResultSet wynik_produktQ = ask.executeQuery(mebelQ);
            wynik_produktQ.next();
            String nazw = wynik_produktQ.getString("nazwa");

            String il = wynik_sql.getString("ilosc");
            String data = wynik_sql.getString("data_zamowienia");
            Double wartosc = Double.parseDouble(il) * Double.parseDouble(wynik_produktQ.getString("cena"));

            ask.close();
            String[] t={id, imieNaz1, imieNaz2, nazw, il, String.valueOf(wartosc), data};
            lista.add(t);
        }
        String array[][]=new String[lista.size()][];
        for (int i=0;i<array.length;i++){
            String[] row=lista.get(i);
            array[i]=row;
        }
        zapytanie.close();

        String[] columns={"ID","Klient","Pracownik","Produkt","ilość","wartość","data"};

        jt=new JTable(array,columns);
        TableColumnModel columnModel = jt.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(7);
        columnModel.getColumn(4).setPreferredWidth(15);
        setTable();
        JScrollPane sp=new JScrollPane(jt);
        frame.setTitle("Zamówienia");
        frame.add(sp);
        frame.setLocation(690,50);
        frame.setSize(700,350);
        frame.setVisible(true);
        con.close();
    }
}
