package Views;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;


public abstract class Wyswietlacz {
    Connection con;
    JFrame frame;
    JTable jt;
    JScrollPane sp;
    public Wyswietlacz(){
        frame=new JFrame();
        frame.setLocation(200,200);
        frame.setLocation(550,50);
        frame.setSize(700,350);
    }
    public void setTable(){
        jt.setFont(new Font("Roboto",Font.PLAIN,14));
        jt.setAutoCreateRowSorter(true);
    }

}