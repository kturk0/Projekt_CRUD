package Views;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewZamowienia extends Wyswietlacz {
    public ViewZamowienia()
    {

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");

            List<String[]> lista=new ArrayList<String[]>();
            Statement zapytanie = con.createStatement();
            String sql="select * from zamowienia";
            ResultSet wynik_sql = zapytanie.executeQuery(sql);
            while(wynik_sql.next()) {
                Statement ask = con.createStatement();
                String id = wynik_sql.getString("id_zamowienie");
                String klientQ="select imie, nazwisko from klienci " +
                        "where id_klient = " + wynik_sql.getString("id_klienta") ;
                ResultSet wynik_klientQ = ask.executeQuery(klientQ);
                wynik_klientQ.next();
                String imieNaz1 = wynik_klientQ.getString("imie") + " " + wynik_klientQ.getString("nazwisko");

                String pracownikQ="select imie, nazwisko from pracownicy " +
                        "where id_pracownik = " + wynik_sql.getString("id_pracownika") ;
                ResultSet wynik_pracownikQ = ask.executeQuery(pracownikQ);
                wynik_pracownikQ.next();
                String imieNaz2 = wynik_pracownikQ.getString("imie") + " " + wynik_pracownikQ.getString("nazwisko");

                String produktQ="select nazwa, cena from produkty " +
                        "where id_produkt = " + wynik_sql.getString("id_produktu") ;
                ResultSet wynik_produktQ = ask.executeQuery(produktQ);
                wynik_produktQ.next();
                String nazw = wynik_produktQ.getString("nazwa");

                String il = wynik_sql.getString("ilosc");
                String data = wynik_sql.getString("data_zamowienia");
                Double wartosc = Double.parseDouble(il) * Double.parseDouble(wynik_produktQ.getString("cena"));

                ask.close();
                String[] t={id, imieNaz1, imieNaz2, nazw, il, String.valueOf(wartosc), data};
                lista.add(t);
            }
            String array[][]=new String[lista.size()][];
            for (int i=0;i<array.length;i++){
                String[] row=lista.get(i);
                array[i]=row;
            }
            zapytanie.close();

            String[] columns={"ID","Klient","Pracownik","Produkt","ilość","wartość","data"};

            jt=new JTable(array,columns);
            TableColumnModel columnModel = jt.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(7);
            columnModel.getColumn(4).setPreferredWidth(15);
            setTable();
            JScrollPane sp=new JScrollPane(jt);
            frame.setTitle("Zamówienia");
            frame.add(sp);
            frame.setLocation(690,50);
            frame.setSize(700,350);
            frame.setVisible(true);
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}
    }
    public ViewZamowienia(int idK)
    {

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+
                    "localhost:1433;databaseName=dbo;"+
                    "user=sa;password=haslosql;");

            List<String[]> lista=new ArrayList<String[]>();
            Statement zapytanie = con.createStatement();
            String sql="select * from zamowienia where id_klienta = " + idK;
            ResultSet wynik_sql = zapytanie.executeQuery(sql);

            while(wynik_sql.next()) {
                Statement ask = con.createStatement();
                String id = wynik_sql.getString("id_zamowienie");
                String klientQ="select imie, nazwisko from klienci " +
                        "where id_klient = " + wynik_sql.getString("id_klienta") ;
                ResultSet wynik_klientQ = ask.executeQuery(klientQ);
                wynik_klientQ.next();
                String imieNaz1 = wynik_klientQ.getString("imie") + " " + wynik_klientQ.getString("nazwisko");

                String pracownikQ="select imie, nazwisko from pracownicy " +
                        "where id_pracownik = " + wynik_sql.getString("id_pracownika") ;
                ResultSet wynik_pracownikQ = ask.executeQuery(pracownikQ);
                wynik_pracownikQ.next();
                String imieNaz2 = wynik_pracownikQ.getString("imie") + " " + wynik_pracownikQ.getString("nazwisko");

                String mebelQ="select nazwa, cena from produkty " +
                        "where id_produkt = " + wynik_sql.getString("id_produkt") ;
                ResultSet wynik_produktQ = ask.executeQuery(mebelQ);
                wynik_produktQ.next();
                String nazw = wynik_produktQ.getString("nazwa");

                String il = wynik_sql.getString("ilosc");
                String data = wynik_sql.getString("data_zamowienia");
                Double wartosc = Double.parseDouble(il) * Double.parseDouble(wynik_produktQ.getString("cena"));

                ask.close();
                String[] t={id, imieNaz1, imieNaz2, nazw, il, String.valueOf(wartosc), data};
                lista.add(t);
            }
            String array[][]=new String[lista.size()][];
            for (int i=0;i<array.length;i++){
                String[] row=lista.get(i);
                array[i]=row;
            }
            zapytanie.close();

            String[] columns={"ID","Klient","Pracownik","Produkt","ilość","wartość","data"};

            jt=new JTable(array,columns);
            TableColumnModel columnModel = jt.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(7);
            columnModel.getColumn(4).setPreferredWidth(15);
            setTable();
            JScrollPane sp=new JScrollPane(jt);
            frame.setTitle("Zamówienia");
            frame.add(sp);
            frame.setLocation(690,50);
            frame.setSize(700,350);
            frame.setVisible(true);
            con.close();
        }
        catch(SQLException error_polaczenie) {
            System.out.println(error_polaczenie.getMessage());}
        catch(ClassNotFoundException error_sterownik) {
            System.out.println("Brak sterownika");}
    }
}
