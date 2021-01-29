package com.proj.projekt;


import com.proj.projekt.Add.*;
import com.proj.projekt.Delete.*;
import com.proj.projekt.Mail.*;
import com.proj.projekt.Views.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

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

        ButtonAddPracownik.setBounds(10,10,190,30);
        add(ButtonAddPracownik);
        ButtonAddPracownik.addActionListener(this);
        ButtonAddKlient.setBounds(10,50,190,30);
        add(ButtonAddKlient);
        ButtonAddKlient.addActionListener(this);
        ButtonAddDostawca.setBounds(10,90,190,30);
        add(ButtonAddDostawca);
        ButtonAddDostawca.addActionListener(this);
        ButtonAddProdukt.setBounds(10,130,190,30);
        add(ButtonAddProdukt);
        ButtonAddProdukt.addActionListener(this);
        ButtonAddZamowienie.setBounds(10,200,190,30);
        add(ButtonAddZamowienie);
        ButtonAddZamowienie.addActionListener(this);
        ButtonAddDostawe.setBounds(10,240,190,30);
        add(ButtonAddDostawe);
        ButtonAddDostawe.addActionListener(this);

        ButtonDeletePracownik.setBounds(220,10,190,30);
        add(ButtonDeletePracownik);
        ButtonDeletePracownik.addActionListener(this);
        ButtonDeleteKlient.setBounds(220,50,190,30);
        add(ButtonDeleteKlient);
        ButtonDeleteKlient.addActionListener(this);
        ButtonDeleteDostawca.setBounds(220,90,190,30);
        add(ButtonDeleteDostawca);
        ButtonDeleteDostawca.addActionListener(this);
        ButtonDeleteProdukt.setBounds(220,130,190,30);
        add(ButtonDeleteProdukt);
        ButtonDeleteProdukt.addActionListener(this);
        ButtonDeleteZamowienie.setBounds(220,200,190,30);
        add(ButtonDeleteZamowienie);
        ButtonDeleteZamowienie.addActionListener(this);
        ButtonDeleteDostawa.setBounds(220,240,190,30);
        add(ButtonDeleteDostawa);
        ButtonDeleteDostawa.addActionListener(this);

        ButtonInbox =new JButton("MAILS");
        ButtonInbox.setFocusable(false);
        ButtonInbox.setBounds(140,340,150,30);
        add(ButtonInbox);
        ButtonInbox.addActionListener(this);



        setJMenuBar(menuBar);
        menuBar.add(Wyswietl);
        menuBar.add(Zaawansowane);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        JPanelZamianaUprawnien f = new JPanelZamianaUprawnien();
        try {
        if (zrodlo==ZarzadzanieUprawnieniami)
            f.zamianaUprawnien();
        else if (zrodlo==Pracownicy) {
                new ViewPracownicy();
        }
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
        else if (zrodlo== ButtonAddDostawca)
            new AddDostawca();
        else if (zrodlo==ButtonAddZamowienie)
            new AddZamowienie(0);
        else if (zrodlo==ButtonAddDostawe)
            new AddDostawa();
        else if (zrodlo==ButtonAddProdukt)
            new AddProdukt();
        else if (zrodlo==ButtonDeletePracownik)
            new DeletePracownik();
        else if (zrodlo==ButtonDeleteKlient)
            new DeleteKlient();
        else if (zrodlo==ButtonDeleteDostawca)
            new DeleteDostawca();
        else if (zrodlo==ButtonDeleteProdukt)
            new DeleteProdukt();
        else if (zrodlo==ButtonDeleteZamowienie)
            new DeleteZamowienie();
        else if (zrodlo==ButtonDeleteDostawa)
            new DeleteDostawa();
        else if (zrodlo== ButtonInbox) {
            switch(frameExpansionState) {
                case HIDDEN:
                   try {
                       setSize(1100, 650);
                       inboxPanel = new InboxPanel(-1, 0);
                       add(inboxPanel);
                       this.revalidate();
                       this.repaint();
                       frameExpansionState = FrameExpansionState.INBOX;
                   } catch (ClassNotFoundException classNotFoundException) {
                      classNotFoundException.printStackTrace();
                   }
                   break;
                case INBOX:
                    setSize(440, 650);
                    remove(inboxPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.HIDDEN;
                    break;
                case ARCHIVE:
                    try {
                        inboxPanel = new InboxPanel(-1, 0);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.INBOX;
                        break;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
            }
        }
        else if (zrodlo== ButtonArchive) {
            switch(frameExpansionState) {
                case HIDDEN:
                    try {
                        setSize(100, 650);
                        inboxPanel = new InboxPanel(-1, 1);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.ARCHIVE;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case ARCHIVE:
                    setSize(440, 650);
                    remove(inboxPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.HIDDEN;
                    break;
                case INBOX:
                    try {
                        inboxPanel = new InboxPanel(-1, 1);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.INBOX;
                        break;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
            }
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
}