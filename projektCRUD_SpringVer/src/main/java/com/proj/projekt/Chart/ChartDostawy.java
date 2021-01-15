package com.proj.projekt.Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChartDostawy extends Barchart {


    public ChartDostawy() throws ClassNotFoundException, SQLException {
        super();
    }

    public void showContent() throws SQLException {
        updateDataset();
        chart = ChartFactory.createBarChart(" ", "Dzień", "Ilość dostaw",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        this.add(monthBox);
        this.add(yearSpinner);
        this.add(leftButton);
        this.add(rightButton);
        ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel);
    }

    public void updateDataset() throws SQLException {
        dataset.clear();
        Statement query = con.createStatement();
        String sql="SELECT DAY(data_dostawy) FROM dostawy" +
                " WHERE MONTH(data_dostawy) = "+ (currentMonth + 1) +" AND YEAR(data_dostawy) = " + currentYear;
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
