package com.proj.projekt;

import com.proj.projekt.Add.*;
import com.proj.projekt.Delete.*;
import com.proj.projekt.Mail.*;
import com.proj.projekt.Views.*;

import javax.swing.*;
import java.awt.event.ActionListener;

abstract class FrameBase extends JFrame {
    JMenuBar menuBar;
    JButton ButtonAddPracownik, ButtonAddKlient, ButtonAddDostawca, ButtonAddZamowienie,
            ButtonAddDostawe, ButtonAddProdukt, ButtonDeletePracownik, ButtonDeleteKlient,
            ButtonDeleteDostawca, ButtonDeleteZamowienie, ButtonDeleteDostawa, ButtonDeleteProdukt,
            ButtonInbox, ButtonArchive, ButtonSend;
    JMenu Wyswietl,Zaawansowane;
    JMenuItem Pracownicy, Klienci, Dostawcy, Produkty, Zamowienia, Dostawy, ZarzadzanieUprawnieniami ;
    InboxPanel inboxPanel;
    SendPanel sendPanel;

    FrameExpansionState frameExpansionState;

    enum FrameExpansionState {
        HIDDEN,
        ARCHIVE,
        INBOX,
        SEND
    }

    public FrameBase(){
        setSize(440, 650);
        setLocation(100, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Magazyn");

        frameExpansionState = FrameExpansionState.HIDDEN;

        menuBar=new JMenuBar();

        Wyswietl=new JMenu("Wyświetl");
        Pracownicy=new JMenuItem("Pracownicy",'P');
        Klienci=new JMenuItem("Klienci",'K');
        Dostawcy=new JMenuItem("Dostawcy",'D');
        Zamowienia=new JMenuItem("Zamowienia",'Z');
        Produkty=new JMenuItem("Produkty",'R');
        Dostawy=new JMenuItem("Dostawy",'O');

        Pracownicy.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        Klienci.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        Dostawcy.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        Zamowienia.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        Produkty.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        Dostawy.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

        ButtonAddPracownik =new JButton("Dodaj nowego pracownika");
        ButtonAddPracownik.setFocusable(false);
        ButtonAddKlient =new JButton("Dodaj nowego klienta");
        ButtonAddKlient.setFocusable(false);
        ButtonAddDostawca =new JButton("Dodaj nowego dostawcę");
        ButtonAddDostawca.setFocusable(false);
        ButtonAddProdukt=new JButton("Dodaj produkt");
        ButtonAddProdukt.setFocusable(false);
        ButtonAddZamowienie =new JButton("Dodaj zamówienie");
        ButtonAddZamowienie.setFocusable(false);
        ButtonAddDostawe=new JButton("Dodaj dostawę");
        ButtonAddDostawe.setFocusable(false);

        ButtonDeletePracownik=new JButton("Usuń pracownika");
        ButtonDeletePracownik.setFocusable(false);
        ButtonDeleteKlient=new JButton("Usuń klienta");
        ButtonDeleteKlient.setFocusable(false);
        ButtonDeleteDostawca =new JButton("Usuń dostawcę");
        ButtonDeleteDostawca.setFocusable(false);
        ButtonDeleteProdukt=new JButton("Usuń produkt");
        ButtonDeleteProdukt.setFocusable(false);
        ButtonDeleteZamowienie=new JButton("Usuń zamówienie");
        ButtonDeleteZamowienie.setFocusable(false);
        ButtonDeleteDostawa=new JButton("Usuń dostawę");
        ButtonDeleteDostawa.setFocusable(false);





    }
}
