package Mail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MailFrame extends JFrame implements ActionListener  {
    SidePanel sidePanel;
    MainPanel mainPanel;
    List<AbstractButton> sideButtons;
    public MailFrame() throws ClassNotFoundException {
        setSize(450, 350);
        setLocation(150, 150);
        sidePanel= new SidePanel();
        sideButtons = Collections.list(sidePanel.mailButtons.getElements());
        for(AbstractButton x : sideButtons){
            x.addActionListener(this);
        }
        add(sidePanel, BorderLayout.WEST);
        mainPanel = new MainPanel(sidePanel.mailIDs.get(0));
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        int selectedID = 0;
        for (AbstractButton x : sideButtons) {
            if (x.isSelected())
                break;
            selectedID++;
        }
        this.remove(mainPanel);
        mainPanel = new MainPanel(sidePanel.mailIDs.get(selectedID));
        add(mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
