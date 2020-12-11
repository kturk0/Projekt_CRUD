package Mail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class InboxPanel extends JPanel implements ActionListener  {
    SidePanel inboxSidePanel;
    MainPanel mainPanel;
    List<AbstractButton> sideButtons;
    JScrollPane sidePanelScroll;
    public InboxPanel(int userID, int panelType) throws ClassNotFoundException {
        setLayout(new BorderLayout());
        inboxSidePanel = new SidePanel(userID,panelType);
        sideButtons = Collections.list(inboxSidePanel.mailButtons.getElements());
        for(AbstractButton x : sideButtons){
            x.addActionListener(this);
        }
        sidePanelScroll = new JScrollPane(inboxSidePanel);
        inboxSidePanel.setOpaque(true);
        add(sidePanelScroll, BorderLayout.LINE_START);
        if(inboxSidePanel.mailIDs.isEmpty())
            mainPanel = new MainPanel(-1);
        else
             mainPanel = new MainPanel(inboxSidePanel.mailIDs.get(0));
        add(mainPanel,BorderLayout.CENTER);
        setBounds(500,0,600,600);
        setBorder(BorderFactory.createDashedBorder(Color.black));
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
        mainPanel = new MainPanel(inboxSidePanel.mailIDs.get(selectedID));
        add(mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
