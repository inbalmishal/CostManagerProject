package view;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Category;
import model.CostManagerException;
import model.CostOrIncome;
import model.DerbyDBModel;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class DetailsFrame extends JFrame {
    private JFrame frame;
    private JPanel panelNorth,panelWest,panelCenter,panelSouth;
    private CreateJDatePicker datePickerFrom,datePickerTo;
    private JCheckBox incomes,expenses;
    private JTable costAndIncomeTable;
    private JButton ok,homePage,delete;
    private DefaultTableModel model;
    private JScrollPane jsp;
    private PieChart pieChart;
    private ObservableList<PieChart.Data> pieData;
    DerbyDBModel db;

    DetailsFrame() {
        frame = new JFrame("Cost Manager");
        db = new DerbyDBModel();
        ok = new JButton("OK");
        homePage = new JButton("Home Page");
        delete = new JButton("Delete");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        incomes = new JCheckBox("incomes: ");
        expenses = new JCheckBox(("expenses: "));
        model = new DefaultTableModel(null, new Object[]{"description", "cost", "date", "category"});
        try {
            ArrayList<CostOrIncome> myArray = db.getAllCostsAndIncomes();
            Object data[] = new Object[4];
           // ArrayList<Category> categoryArray = db.getAllCategories();
           // DataForPie dataPie[] = new DataForPie[categoryArray.size()];
         //   for(int i=0;i<categoryArray.size();i++) {
           //     String name = categoryArray.get(i).getCategoryName();
           //     dataPie[i].setName(name);
           // }
        for (int i = 0; i < myArray.size(); i++){
                String description = myArray.get(i).getDescription();
                double cost = myArray.get(i).getCost();
                Date date = myArray.get(i).getDate();
                Category category = myArray.get(i).getCategory();
             //   for(int j = 0;j<categoryArray.size();i++){
               //     if(category.getCategoryName() == dataPie[j].getName()){
                 //       dataPie[j].setCount(dataPie[j].getCount()+cost);
                   // }
              //  }
                data[0] = description;
                data[1] = cost;
                data[2] = date;
                data[3] = category.getCategoryName();
                model.addRow(data);

            }
          //
          //  for(int i=0;i<dataPie.length;i++){
          //      pieData.add(new PieChart.Data(dataPie[i].getName(),dataPie[i].getCount()));
          //  }
          //  pieChart = new PieChart(pieData);

        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        costAndIncomeTable = new JTable(model);
        jsp = new JScrollPane();
        jsp.setBounds(215, 71, 615, 237);


    }
    public void start(){
        frame.setLayout(new BorderLayout());
        frame.setSize(1000,600);
        datePickerFrom = new CreateJDatePicker(panelNorth);
        datePickerTo = new CreateJDatePicker(panelNorth);
        panelNorth.add(incomes);
        panelNorth.add(expenses);
        panelNorth.add(ok);
        panelNorth.add(homePage);
        frame.add(panelNorth,BorderLayout.NORTH);
        panelCenter.setLayout(new FlowLayout());
        panelCenter.add(jsp);
        jsp.setViewportView(costAndIncomeTable);
        //panelCenter.add(pieChart);
        frame.add(panelCenter,BorderLayout.CENTER);
        panelSouth.add(delete,BorderLayout.CENTER);
        frame.add(panelSouth,BorderLayout.SOUTH);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        homePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartFrame startFrame = new StartFrame();
                startFrame.start();
                frame.dispose();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Date dateFrom = null;
                Date dateTo = null;
                try {
                    dateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerFrom.datePicker.getJFormattedTextField().getText());
                    dateTo = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerTo.datePicker.getJFormattedTextField().getText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean checkedIncomes,checkedExpenses;
                checkedIncomes = incomes.isSelected();
                checkedExpenses = expenses.isSelected();
                if(checkedExpenses && checkedIncomes){
                    try {
                        changeTheTable(db.getAllCostsAndIncomesBetweenDates(dateFrom,dateTo));
                    } catch (CostManagerException e) {
                        e.printStackTrace();
                    }
                }
                else if(checkedIncomes){
                    try {
                        changeTheTable(db.getAllIncomesBetweenDates(dateFrom,dateTo));
                    } catch (CostManagerException e) {
                        e.printStackTrace();
                    }
                }
                else if(checkedExpenses){
                    try {
                        changeTheTable(db.getAllCostsBetweenDates(dateFrom,dateTo));
                    } catch (CostManagerException e) {
                        e.printStackTrace();
                    }
                }
                else{}


            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);

    }

    private void changeTheTable(ArrayList<CostOrIncome> myArray) {

        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        Object data[] = new Object[4];
        for (int i = 0; i < myArray.size(); i++){
            String description = myArray.get(i).getDescription();
            double cost = myArray.get(i).getCost();
            Date date = myArray.get(i).getDate();
            Category category = myArray.get(i).getCategory();
            data[0] = description;
            data[1] = cost;
            data[2] = date;
            data[3] = category.getCategoryName();
           model.addRow(data);
        }
        model.fireTableDataChanged();

    }

}
