package com.proj.projekt.Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Barchart extends JPanel {
    Connection con;
    int currentMonth, currentYear, daysInSelectedMonth;
    JComboBox<String> monthBox = new JComboBox<>();
    JSpinner yearSpinner;
    JFreeChart chart;
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    public Barchart() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection("jdbc:sqlserver://"
                + "localhost:1433;databaseName=dbo;"
                + "user=sa;password=haslosql;");
        monthBox.setMaximumRowCount(12);
        String[] months = {"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
        monthBox.setModel(new DefaultComboBoxModel<>(months));
        monthBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                try {
                    monthChange(evt);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        monthBox.setFocusable(false);
        int initYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerModel model = new SpinnerNumberModel(initYear, initYear - 100, initYear + 100, 1);
        yearSpinner = new JSpinner(model);
        yearSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                try {
                    yearChange(evt);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        showContent();
    }

    public void monthChange(ItemEvent evt) throws SQLException {
        currentMonth = monthBox.getSelectedIndex();
        setDaysInMonth();
        updateDataset();
    }

    public void yearChange(ChangeEvent evt) throws SQLException {
        currentYear = (Integer) yearSpinner.getValue();
        setDaysInMonth();
        updateDataset();
    }

    public void setDaysInMonth()
    {
        int day;
        if (currentMonth == 0 || currentMonth == 2 || currentMonth == 4 || currentMonth == 6 || currentMonth == 7 || currentMonth == 9 || currentMonth == 11) {
            day = 31;
        }
        else if (currentMonth == 3 || currentMonth == 5 || currentMonth == 8 || currentMonth == 10) {
            day = 30;
        }
        else if((currentYear % 4 == 0 && currentYear % 100 != 0) || currentYear % 400 == 0)
            day = 28;
        else
            day = 29;
        daysInSelectedMonth = day;
    }

    public void showContent() throws SQLException {
        updateDataset();
        chart = ChartFactory.createBarChart(" ", "Dzień", "Ilość zamówień",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        this.add(monthBox);
        this.add(yearSpinner);
        ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel);
    }

    private void updateDataset() throws SQLException {
        dataset.clear();
        Statement query = con.createStatement();
        String sql="SELECT DAY(data_zamowienia) FROM zamowienia" +
                " WHERE MONTH(data_zamowienia) = "+ (currentMonth + 1) +" AND YEAR(data_zamowienia) = " + currentYear;
        ResultSet sqlResult = query.executeQuery(sql);
        List<Integer> deliveriesInMonth = new ArrayList<>();
        for(int i = 0 ;  i < daysInSelectedMonth; i++)
            deliveriesInMonth.add(0);
        while(sqlResult.next())
            deliveriesInMonth.set(sqlResult.getInt(1) -1, deliveriesInMonth.get(sqlResult.getInt(1) -1) + 1);
        for (int i = 1; i <= daysInSelectedMonth; i++) {
            dataset.setValue(deliveriesInMonth.get(i-1), "Marks", "" + i);
        }
    }
}
