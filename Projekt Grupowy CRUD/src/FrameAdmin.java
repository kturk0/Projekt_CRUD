import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

class FrameAdmin extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu Zaawansowane;
    JMenuItem ZarzadzanieUprawnieniami;

    public FrameAdmin() {
        setSize(450, 350);
        setLocation(100, 100);
        setResizable(false);
        setLayout(null);
        setTitle("Magazyn");

        menuBar=new JMenuBar();

        Zaawansowane=new JMenu("Zaawansowane");
        ZarzadzanieUprawnieniami=new JMenuItem("Zarządzanie Uprawnieniami",'Z');
        ZarzadzanieUprawnieniami.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

        Zaawansowane.add(ZarzadzanieUprawnieniami);

        ZarzadzanieUprawnieniami.addActionListener(this);

        setJMenuBar(menuBar);
        menuBar.add(Zaawansowane);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        JPanelZamianaUprawnien f = new JPanelZamianaUprawnien();
        if (zrodlo==ZarzadzanieUprawnieniami)
            f.zamianaUprawnien();


    }
}