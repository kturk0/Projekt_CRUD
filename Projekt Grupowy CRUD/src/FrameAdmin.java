import Add.*;
import Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameAdmin extends FrameBase implements ActionListener {


    public FrameAdmin() {
        super();
        Wyswietl.add(Pracownicy);
        Wyswietl.add(Klienci);
        Wyswietl.add(Dostawcy);
        Wyswietl.addSeparator();
        Wyswietl.add(Produkty);
        Wyswietl.addSeparator();
        Wyswietl.add(Zamowienia);
        Wyswietl.add(Dostawy);

        Pracownicy.addActionListener(this);
        Klienci.addActionListener(this);
        Dostawcy.addActionListener(this);
        Zamowienia.addActionListener(this);
        Produkty.addActionListener(this);
        Dostawy.addActionListener(this);


        Zaawansowane=new JMenu("Zaawansowane");
        ZarzadzanieUprawnieniami=new JMenuItem("Zarządzanie Uprawnieniami",'Z');
        ZarzadzanieUprawnieniami.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

        Zaawansowane.add(ZarzadzanieUprawnieniami);

        ZarzadzanieUprawnieniami.addActionListener(this);

        ButtonAddPracownik =new JButton("Dodaj nowego pracownika");
        ButtonAddPracownik.setBounds(10,10,190,30);
        add(ButtonAddPracownik);
        ButtonAddPracownik.addActionListener(this);
        ButtonAddKlient =new JButton("Dodaj nowego klienta");
        ButtonAddKlient.setBounds(10,50,190,30);
        add(ButtonAddKlient);
        ButtonAddKlient.addActionListener(this);
        ButtonAddDostawca =new JButton("Dodaj nowego dostawcę");
        ButtonAddDostawca.setBounds(10,90,190,30);
        add(ButtonAddDostawca);
        ButtonAddDostawca.addActionListener(this);
        ButtonAddProdukt=new JButton("Dodaj produkt");
        ButtonAddProdukt.setBounds(10,130,190,30);
        add(ButtonAddProdukt);
        ButtonAddProdukt.addActionListener(this);
        ButtonAddZamowienie =new JButton("Dodaj zamówienie");
        ButtonAddZamowienie.setBounds(10,200,190,30);
        add(ButtonAddZamowienie);
        ButtonAddZamowienie.addActionListener(this);
        ButtonAddDostawe=new JButton("Dodaj dostawę");
        ButtonAddDostawe.setBounds(10,240,190,30);
        add(ButtonAddDostawe);
        ButtonAddDostawe.addActionListener(this);

        setJMenuBar(menuBar);
        menuBar.add(Wyswietl);
        menuBar.add(Zaawansowane);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        JPanelZamianaUprawnien f = new JPanelZamianaUprawnien();
        if (zrodlo==ZarzadzanieUprawnieniami)
            f.zamianaUprawnien();
        else if (zrodlo==Pracownicy)
            new ViewPracownicy();
        else if (zrodlo==Klienci)
            new ViewKlienci();
        else if (zrodlo==Dostawcy)
            new ViewDostawcy();
        else if (zrodlo==Zamowienia)
            new ViewZamowienia();
        else if (zrodlo==Produkty)
            new ViewProdukty();
        else if (zrodlo==Dostawy)
            new ViewDostawy();
        else if (zrodlo==ButtonAddPracownik)
            new AddPracownik();
        else if (zrodlo==ButtonAddKlient)
            new AddKlient();
        else if (zrodlo==ButtonAddDostawca)
            new AddDostawca();
        else if (zrodlo==ButtonAddZamowienie)
            new AddZamowienie();
        else if (zrodlo==ButtonAddDostawe)
            new AddDostawa();
        else if (zrodlo==ButtonAddProdukt)
            new AddProdukt();



    }
}