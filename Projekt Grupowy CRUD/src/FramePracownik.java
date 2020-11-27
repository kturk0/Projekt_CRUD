
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import Add.*;

class FramePracownik extends FrameBase implements ActionListener {

    Connection con;
    int id = 1;

    static JLabel l;

    public FramePracownik(int userID){
        super();
        Wyswietl.add(Klienci);
        Wyswietl.add(Dostawcy);
        Wyswietl.addSeparator();
        Wyswietl.add(Produkty);
        Wyswietl.addSeparator();
        Wyswietl.add(Zamowienia);
        Wyswietl.add(Dostawy);

        Klienci.addActionListener(this);
        Dostawcy.addActionListener(this);
        Zamowienia.addActionListener(this);
        Produkty.addActionListener(this);
        Dostawy.addActionListener(this);

        setJMenuBar(menuBar);
        menuBar.add(Wyswietl);

    }


    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        if (zrodlo==Klienci)
            new Views.ViewKlienci();
        else if (zrodlo==Dostawcy)
            new Views.ViewDostawcy();
        else if (zrodlo==Zamowienia)
            new Views.ViewZamowienia();
        else if (zrodlo==Produkty)
            new Views.ViewProdukty();
        else if (zrodlo==Dostawy)
            new Views.ViewDostawy();
        else if (zrodlo==ButtonAddPracownik)
            new AddPracownik();
        else if (zrodlo==ButtonAddKlient)
            new AddKlient();
        else if (zrodlo== ButtonDeleteDostawca)
            new AddDostawca();
        else if (zrodlo==ButtonAddZamowienie)
            new AddZamowienie();
        else if (zrodlo==ButtonAddDostawe)
            new AddDostawa();
        else if (zrodlo==ButtonAddProdukt)
            new AddProdukt();

    }

}