import Mail.InboxPanel;
import Mail.SendPanel;

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
        setSize(500, 700);
        setLocation(100, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Magazyn");

        frameExpansionState = FrameExpansionState.HIDDEN;

        menuBar=new JMenuBar();

        Wyswietl=new JMenu("Wyświetl"); //utworzenie pierwszej opcji menu
        Pracownicy=new JMenuItem("Pracownicy",'P');
        Klienci=new JMenuItem("Klienci",'K');
        Dostawcy=new JMenuItem("Dostawcy",'D');
        Zamowienia=new JMenuItem("Zamowienia",'Z');
        Produkty=new JMenuItem("Meble",'M');
        Dostawy=new JMenuItem("Dostawy",'O');

        Pracownicy.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        Klienci.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        Dostawcy.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        Zamowienia.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        Produkty.setAccelerator(KeyStroke.getKeyStroke("ctrl M"));
        Dostawy.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));





    }
}
