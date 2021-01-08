package com.proj.projekt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import com.proj.projekt.Add.*;
import com.proj.projekt.Delete.*;
import com.proj.projekt.Mail.*;
import com.proj.projekt.Views.*;

class FramePracownik extends FrameBase implements ActionListener {

    Connection con;
    int userID;
    int userLevel;

    static JLabel l;

    public FramePracownik(int userID, int userLevel){
        super();
        this.userID = userID;
        this.userLevel = userLevel;
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

        if(userLevel > 0){
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
        }
        if(userLevel == 2){
            ButtonDeletePracownik=new JButton("Usuń pracownika");
            ButtonDeletePracownik.setBounds(220,10,190,30);
            add(ButtonDeletePracownik);
            ButtonDeletePracownik.addActionListener(this);
            ButtonDeleteKlient=new JButton("Usuń klienta");
            ButtonDeleteKlient.setBounds(220,50,190,30);
            add(ButtonDeleteKlient);
            ButtonDeleteKlient.addActionListener(this);
            ButtonDeleteDostawca =new JButton("Usuń dostawcę");
            ButtonDeleteDostawca.setBounds(220,90,190,30);
            add(ButtonDeleteDostawca);
            ButtonDeleteDostawca.addActionListener(this);
            ButtonDeleteProdukt=new JButton("Usuń produkt");
            ButtonDeleteProdukt.setBounds(220,130,190,30);
            add(ButtonDeleteProdukt);
            ButtonDeleteProdukt.addActionListener(this);
            ButtonDeleteZamowienie=new JButton("Usuń zamówienie");
            ButtonDeleteZamowienie.setBounds(220,200,190,30);
            add(ButtonDeleteZamowienie);
            ButtonDeleteZamowienie.addActionListener(this);
            ButtonDeleteDostawa=new JButton("Usuń dostawę");
            ButtonDeleteDostawa.setBounds(220,240,190,30);
            add(ButtonDeleteDostawa);
            ButtonDeleteDostawa.addActionListener(this);
        }

        ButtonSend =new JButton("SEND");
        ButtonSend.setBounds(140,340,150,30);
        add(ButtonSend);
        ButtonSend.addActionListener(this);

        ButtonInbox =new JButton("INBOX");
        ButtonInbox.setBounds(140,380,150,30);
        add(ButtonInbox);
        ButtonInbox.addActionListener(this);

        ButtonArchive =new JButton("ARCHIVE");
        ButtonArchive.setBounds(140,420,150,30);
        add(ButtonArchive);
        ButtonArchive.addActionListener(this);



    }


    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        if (zrodlo==Klienci)
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
            new AddZamowienie();
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
                        setSize(1200, 700);
                        inboxPanel = new InboxPanel(userID, 0);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.INBOX;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case INBOX:
                    setSize(500, 700);
                    remove(inboxPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.HIDDEN;
                    break;
                case ARCHIVE:
                    try {
                        remove(inboxPanel);
                        inboxPanel = new InboxPanel(userID, 0);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.INBOX;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case SEND:
                    try {
                        remove(sendPanel);
                        inboxPanel = new InboxPanel(userID, 0);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.INBOX;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
            }
        }
        else if (zrodlo== ButtonArchive) {
            switch(frameExpansionState) {
                case HIDDEN:
                    try {
                        setSize(1200, 700);
                        inboxPanel = new InboxPanel(userID, 1);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.ARCHIVE;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case ARCHIVE:
                    setSize(500, 700);
                    remove(inboxPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.HIDDEN;
                    break;
                case INBOX:
                    try {
                        remove(inboxPanel);
                        inboxPanel = new InboxPanel(userID, 1);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.ARCHIVE;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case SEND:
                    try {
                        remove(sendPanel);
                        inboxPanel = new InboxPanel(userID, 1);
                        add(inboxPanel);
                        this.revalidate();
                        this.repaint();
                        frameExpansionState = FrameExpansionState.ARCHIVE;
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
            }
        }
        else if (zrodlo== ButtonSend) {
            switch(frameExpansionState) {
                case HIDDEN:
                    setSize(1200, 700);
                    sendPanel = new SendPanel(userID);
                    add(sendPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.SEND;
                    break;
                case ARCHIVE:
                case INBOX:
                    remove(inboxPanel);
                    sendPanel = new SendPanel(userID);
                    add(sendPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.SEND;
                    break;
                case SEND:
                    setSize(500, 700);
                    remove(sendPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.HIDDEN;
                    break;

            }
        }

    }

}