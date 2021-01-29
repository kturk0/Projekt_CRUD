package com.proj.projekt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import com.proj.projekt.Add.*;
import com.proj.projekt.Delete.*;
import com.proj.projekt.Mail.*;
import com.proj.projekt.Views.*;

class FramePracownik extends FrameBase implements ActionListener {

    Connection con;
    int userID;
    int userLevel;

    static JLabel l;

    public FramePracownik(int userID, int userLevel) throws IOException {
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
            ButtonAddKlient.setBounds(10,10,190,30);
            add(ButtonAddKlient);
            ButtonAddKlient.addActionListener(this);
            ButtonAddDostawca.setBounds(10,50,190,30);
            add(ButtonAddDostawca);
            ButtonAddDostawca.addActionListener(this);
            ButtonAddProdukt.setBounds(10,90,190,30);
            add(ButtonAddProdukt);
            ButtonAddProdukt.addActionListener(this);
            ButtonAddZamowienie.setBounds(10,160,190,30);
            add(ButtonAddZamowienie);
            ButtonAddZamowienie.addActionListener(this);
            ButtonAddDostawe.setBounds(10,200,190,30);
            add(ButtonAddDostawe);
            ButtonAddDostawe.addActionListener(this);
        }
        if(userLevel == 2){
            ButtonDeleteKlient.setBounds(220,10,190,30);
            add(ButtonDeleteKlient);
            ButtonDeleteKlient.addActionListener(this);
            ButtonDeleteDostawca.setBounds(220,50,190,30);
            add(ButtonDeleteDostawca);
            ButtonDeleteDostawca.addActionListener(this);
            ButtonDeleteProdukt.setBounds(220,90,190,30);
            add(ButtonDeleteProdukt);
            ButtonDeleteProdukt.addActionListener(this);
            ButtonDeleteZamowienie.setBounds(220,160,190,30);
            add(ButtonDeleteZamowienie);
            ButtonDeleteZamowienie.addActionListener(this);
            ButtonDeleteDostawa.setBounds(220,200,190,30);
            add(ButtonDeleteDostawa);
            ButtonDeleteDostawa.addActionListener(this);
        }
        if(userLevel == 1)
        {
            ButtonAddKlient.setBounds(115,10,190,30);
            ButtonAddDostawca.setBounds(115,50,190,30);
            ButtonAddProdukt.setBounds(115,90,190,30);
            ButtonAddZamowienie.setBounds(115,160,190,30);
            ButtonAddDostawe.setBounds(115,200,190,30);
        }
        InputStream in = getClass().getClassLoader().getResourceAsStream("mail.jpg");
        Image img = ImageIO.read(in);
        in.close();
        Image newimg = img.getScaledInstance( 80, 60,  java.awt.Image.SCALE_SMOOTH );
        ImageIcon icon = new ImageIcon(newimg);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(180,280,60,40);
        imageLabel.setOpaque(false);
        add(imageLabel);


        ButtonSend =new JButton("SEND");
        ButtonSend.setBounds(140,340,140,30);
        ButtonSend.setFocusable(false);
        add(ButtonSend);
        ButtonSend.addActionListener(this);

        ButtonInbox =new JButton("INBOX");
        ButtonInbox.setBounds(140,380,140,30);
        ButtonInbox.setFocusable(false);
        add(ButtonInbox);
        ButtonInbox.addActionListener(this);

        ButtonArchive =new JButton("ARCHIVE");
        ButtonArchive.setBounds(140,420,140,30);
        ButtonArchive.setFocusable(false);
        add(ButtonArchive);
        ButtonArchive.addActionListener(this);

        if(userLevel == 0){
            imageLabel.setBounds(180,180,60,40);
            ButtonSend.setBounds(140,240,140,30);
            ButtonInbox.setBounds(140,280,140,30);
            ButtonArchive.setBounds(140,320,140,30);
        }


    }


    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        try {
        if (zrodlo==Klienci) {
                new ViewKlienci();
        }
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
            new AddZamowienie(userID);
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
                        setSize(1100, 680);
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
                    setSize(440, 650);
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
                        setSize(1100, 680);
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
                    setSize(440, 650);
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
                    setSize(1100, 680);
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
                    setSize(440, 650);
                    remove(sendPanel);
                    this.revalidate();
                    this.repaint();
                    frameExpansionState = FrameExpansionState.HIDDEN;
                    break;

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