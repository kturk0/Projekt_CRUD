import Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameAdmin extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu Wyswietl,Zaawansowane;
    JMenuItem Pracownicy, Klienci, Dostawcy, Produkty, Zamowienia, Dostawy, ZarzadzanieUprawnieniami ;

    public FrameAdmin() {
        setSize(450, 350);
        setLocation(100, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Magazyn");

        menuBar=new JMenuBar();

        Wyswietl=new JMenu("Wyświetl"); //utworzenie pierwszej opcji menu


        Pracownicy=new JMenuItem("Pracownicy",'P');
        Klienci=new JMenuItem("Klienci",'K');
        Dostawcy=new JMenuItem("Dostawcy",'D');
        Zamowienia=new JMenuItem("Zamowienia",'Z');
        Produkty=new JMenuItem("Produkty",'R');
        Dostawy=new JMenuItem("Dostawy",'O');
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

        Pracownicy.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        Klienci.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        Dostawcy.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        Zamowienia.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        Produkty.setAccelerator(KeyStroke.getKeyStroke("ctrl M"));
        Dostawy.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

        Zaawansowane=new JMenu("Zaawansowane");
        ZarzadzanieUprawnieniami=new JMenuItem("Zarządzanie Uprawnieniami",'Z');
        ZarzadzanieUprawnieniami.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

        Zaawansowane.add(ZarzadzanieUprawnieniami);

        ZarzadzanieUprawnieniami.addActionListener(this);

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



    }
}