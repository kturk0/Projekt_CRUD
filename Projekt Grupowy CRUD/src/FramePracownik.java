import Views.Wyswietlacz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import Views.*;

class FramePracownik extends JFrame implements ActionListener {

    Connection con;
    JButton p2, p3, p4, p5, bKlientD;
    int id = 1;
    JMenuBar menuBar;
    JMenu Wyswietl;
    JMenuItem Klienci, Dostawcy, Produkty, Zamowienia, Dostawy ;


    static JLabel l;



    public FramePracownik(int userID){
        setSize(450,300);
        setLocation(100,100);
        setResizable(false);
        setLayout(null);
        setTitle("Hurtownia");
        id = userID;
        menuBar=new JMenuBar(); //utworzenie "paska" menu

        Wyswietl=new JMenu("Wyświetl"); //utworzenie pierwszej opcji menu


        Klienci=new JMenuItem("Klienci",'K');
        Dostawcy=new JMenuItem("Dostawcy",'D');
        Zamowienia=new JMenuItem("Zamowienia",'Z');
        Produkty=new JMenuItem("Meble",'M');
        Dostawy=new JMenuItem("Dostawy",'O');
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

        Klienci.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        Dostawcy.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        Zamowienia.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        Produkty.setAccelerator(KeyStroke.getKeyStroke("ctrl M"));
        Dostawy.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

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

    }

}