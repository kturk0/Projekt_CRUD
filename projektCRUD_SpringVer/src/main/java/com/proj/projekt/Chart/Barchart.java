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
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class Barchart extends JPanel {
    Connection con;
    int currentMonth, currentYear, daysInSelectedMonth;
    JComboBox<String> monthBox = new JComboBox<>();
    JSpinner yearSpinner;
    SpinnerModel model;
    JFreeChart chart;
    JButton leftButton = new JButton("<");
    JButton rightButton = new JButton(">");
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
        model = new SpinnerNumberModel(initYear, initYear - 100, initYear + 100, 1);
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
       // leftButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        leftButton.setRequestFocusEnabled(false);
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    leftButtonClicked(evt);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

       // rightButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rightButton.setRequestFocusEnabled(false);
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    rightButtonClicked(evt);
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

    private void leftButtonClicked(ActionEvent evt) throws SQLException {
        if (currentMonth == 0) {
            currentYear--;
            currentMonth = 11;
            yearSpinner.setValue(model.getPreviousValue());
        }
        else {
            currentMonth--;
        }
        monthBox.setSelectedIndex(currentMonth);
        setDaysInMonth();
        updateDataset();
    }

    private void rightButtonClicked(ActionEvent evt) throws SQLException {
        if (currentMonth == 11) {
            currentYear++;
            currentMonth = 0;
            yearSpinner.setValue(model.getNextValue());
        } else {
            currentMonth++;
        }
        monthBox.setSelectedIndex(currentMonth);
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

    public abstract void showContent() throws SQLException;

    public abstract void updateDataset() throws SQLException;
}
