package com.proj.projekt.Add;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AddDostawca extends AddClass implements ActionListener {
    String[] adresyNazwy;
    java.util.List<String> listaA;

    JLabel id_adresu = new JLabel("ID ADRESU:");
    JComboBox adresBox;
    JLabel miasto = new JLabel("MIASTO :");
    JTextField miastoField = new JTextField(5);
    JLabel ulica = new JLabel("ULICA :");
    JTextField ulicaField = new JTextField(5);
    JLabel nr_budynku = new JLabel("NR BUDYNKU :");
    JTextField nr_budynkuField = new JTextField(5);
    JLabel kod_pocztowy = new JLabel("KOD POCZTOWY :");
    JTextField kod_pocztowyField = new JTextField(5);

    JRadioButton r1=new JRadioButton("Nowy adres",true);
    JRadioButton r2=new JRadioButton("Id adresu");


    Boolean checker = true;
    public AddDostawca() throws SQLException, IOException, ClassNotFoundException {
        super();
        try {


            JTextField nazwaField = new JTextField(5);
            JTextField stawkaField = new JTextField(5);
            adresGetter();

            setLayout(new GridLayout(0, 2));
            this.setPreferredSize(new Dimension(300, 200));
            add(new JLabel("NAZWA:"));
            add(nazwaField);
            add(new JLabel("STAWKA ZA KM:"));
            add(stawkaField);
            r1.setFocusable(false);
            r2.setFocusable(false);
            r1.setBounds(75,50,100,30);
            r2.setBounds(75,100,100,30);
            ButtonGroup bg=new ButtonGroup();
            bg.add(r1);
            bg.add(r2);
            r1.addActionListener(this);
            r2.addActionListener(this);
            add(r1);
            add(r2);
            add(miasto);
            add(miastoField);
            add(ulica);
            add(ulicaField);
            add(nr_budynku);
            add(nr_budynkuField);
            add(kod_pocztowy);
            add(kod_pocztowyField);

            int wynik = JOptionPane.showConfirmDialog(null, this, "Wpisz dane nowego dostawcy", JOptionPane.OK_CANCEL_OPTION);
            if (wynik == JOptionPane.OK_OPTION) {
                if (checker)
                {
                    String daneAdres = "insert into adresy values (?, ?, ?, ?)";
                    PreparedStatement prep1 = con.prepareStatement(daneAdres);
                    prep1.setString(1, miastoField.getText());
                    prep1.setString(2, ulicaField.getText());
                    prep1.setString(3, nr_budynkuField.getText());
                    prep1.setString(4, kod_pocztowyField.getText());
                    prep1.executeUpdate();
                    prep1.close();

                    String idAdresu = "SELECT @@IDENTITY";
                    Statement prep3 = con.createStatement();
                    ResultSet wynik_prep3 = prep3.executeQuery(idAdresu);
                    wynik_prep3.next();
                    idAdresu = wynik_prep3.getString(1);
                    System.out.println(idAdresu);
                    prep3.close();
                    String danePrac = "insert into dostawcy values (?, ?, ?)";
                    PreparedStatement prep2 = con.prepareStatement(danePrac);
                    prep2.setString(1, nazwaField.getText());
                    prep2.setDouble(2, Double.parseDouble(stawkaField.getText()));
                    prep2.setInt(3, Integer.parseInt(idAdresu));
                    prep2.executeUpdate();
                    prep2.close();
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "DODANO DOSTAWCĘ",
                            "Sukces", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    String selected_miasto = listaA.get(adresBox.getSelectedIndex()*3 );
                    String selected_ulica = listaA.get(adresBox.getSelectedIndex()*3 +1);
                    String selected_nr = listaA.get(adresBox.getSelectedIndex()*3 +2);
                    Statement getAdres= con.createStatement();
                    String getAdresQ = "select * from adresy "
                            + "where miasto = '" + selected_miasto + "' AND ulica = '" + selected_ulica + "' AND nr_budynku = '" + selected_nr + "'";
                    ResultSet wynik_Adres = getAdres.executeQuery(getAdresQ);
                    wynik_Adres.next();
                    int idAdresu = wynik_Adres.getInt("id_pracownik");
                    getAdres.close();

                    String danePrac = "insert into dostawcy values (?, ?, ?)";
                    PreparedStatement prep = con.prepareStatement(danePrac);
                    prep.setString(1, nazwaField.getText());
                    prep.setDouble(2, Double.parseDouble(stawkaField.getText()));
                    prep.setInt(3, idAdresu);
                    prep.executeUpdate();
                    prep.close();
                    JFrame msgFrame = new JFrame();
                    msgFrame.setLocation(400, 400);
                    JOptionPane.showMessageDialog(msgFrame, "DODANO DOSTAWCĘ",
                            "Sukces", JOptionPane.PLAIN_MESSAGE);
                }
            }
            con.close();
        } catch (SQLException | NumberFormatException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());
            JFrame errorFrame = new JFrame();
            errorFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(errorFrame, "BŁĘDNE DANE!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    private void adresGetter(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");

            listaA=new ArrayList<String>();
            Statement zapytanie = con.createStatement();
            String sql="select * from adresy";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                String t=wynik_sql.getString("miasto");
                String z=wynik_sql.getString("ulica");
                String y=wynik_sql.getString("nr_budynku");
                listaA.add(t);
                listaA.add(z);
                listaA.add(y);
            }
            adresyNazwy = new String[listaA.size()/3];
            String miasto = "un";
            String ulica = "un";
            String nr_budynku;
            for(int i = 0; i < listaA.size(); i++)
            {
                if(i%3 == 0)
                    miasto = listaA.get(i);
                else if (i%3 == 1)
                    ulica = listaA.get(i);
                else if (i%3 == 2)
                {
                    nr_budynku = listaA.get(i);
                    adresyNazwy[i/3] = miasto + ", " + ulica + " " + nr_budynku;
                }
            }
            adresBox = new JComboBox(adresyNazwy);
            adresBox.setSelectedIndex(0);
            zapytanie.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println("Błąd połączenia z bazą danych");}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}

    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
        if (zrodlo==r2)
        {
            this.remove(miasto);
            this.remove(miastoField);
            this.remove(ulica);
            this.remove(ulicaField);
            this.remove(nr_budynku);
            this.remove(nr_budynkuField);
            this.remove(kod_pocztowy);
            this.remove(kod_pocztowyField);
            this.add(id_adresu);
            this.add(adresBox);
            this.setPreferredSize(new Dimension(300, 200));
            this.revalidate();
            this.repaint();
            checker = true;
        }
        else if (zrodlo==r1)
        {
            this.remove(id_adresu);
            this.remove(adresBox);
            this.add(miasto);
            this.add(miastoField);
            this.add(ulica);
            this.add(ulicaField);
            this.add(nr_budynku);
            this.add(nr_budynkuField);
            this.add(kod_pocztowy);
            this.add(kod_pocztowyField);
            this.setPreferredSize(new Dimension(300, 500));
            this.revalidate();
            this.repaint();
            checker = false;

        }
    }
}
