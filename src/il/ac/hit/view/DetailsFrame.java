package il.ac.hit.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.*;
import il.ac.hit.model.Category;
import il.ac.hit.model.CostManagerException;
import il.ac.hit.model.CostOrIncome;
import il.ac.hit.model.DerbyDBModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailsFrame extends JFrame {
    private JFrame frame;
    private JPanel panelNorth,panelWest,panelCenter,panelSouth;
    private CreateJDatePicker datePickerFrom,datePickerTo;
    private JTextField idForDelete;
    private JCheckBox incomes,expenses;
    private JTable costAndIncomeTable;
    private JButton ok,homePage,delete;
    private DefaultTableModel model;
    private JLabel dateFrom,dateTo,deleteId;
    private JScrollPane jsp;
    private DefaultPieDataset pieDataSet;
    private JFreeChart chart;
    private PiePlot3D plot;
    private ChartPanel chartPanel;
    DerbyDBModel db;

    DetailsFrame() {
        frame = new JFrame("Cost Manager");
        try {
            db = new DerbyDBModel();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        ok = new JButton("OK");
        homePage = new JButton("Home Page");
        delete = new JButton("Delete");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        idForDelete = new JTextField(10);
        incomes = new JCheckBox("incomes: ");
        expenses = new JCheckBox(("expenses: "));
        dateFrom = new JLabel("From:");
        dateTo = new JLabel("To:");
        deleteId = new JLabel("id for delete: ");
        model = new DefaultTableModel(null, new Object[]{"id","description", "cost", "date", "category"});
        pieDataSet = new DefaultPieDataset();

        try {
            ArrayList<CostOrIncome> myArray = db.getAllCostsAndIncomes();
            Object data[] = new Object[5];
            ArrayList<Category> categoryArray = db.getAllCategories();
            DataForPie[] dataPie = new DataForPie[categoryArray.size()];
            for(int i=0;i<categoryArray.size();i++) {
                String name = categoryArray.get(i).getCategoryName();
                dataPie[i] = new DataForPie(name);
            }
        for (int j = 0; j < myArray.size(); j++){
                int id = myArray.get(j).getId();
                String description = myArray.get(j).getDescription();
                double cost = myArray.get(j).getCost();
                Date date = myArray.get(j).getDate();
                Category category = myArray.get(j).getCategory();
                for(int k = 0;k<categoryArray.size();k++){
                    if(category.getCategoryName().equals(dataPie[k].getName())){
                        dataPie[k].setCount(dataPie[k].getCount()+cost);
                    }
                }
                data[0] = id;
                data[1] = description;
                data[2] = cost;
                data[3] = date;
                data[4] = category.getCategoryName();
                model.addRow(data);

            }

            for(int m=0;m<dataPie.length;m++){
                pieDataSet.setValue(dataPie[m].getName(),dataPie[m].getCount());
            }
            chart = ChartFactory.createPieChart3D("Pie Chart",pieDataSet);
            plot = (PiePlot3D)chart.getPlot();

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
        panelNorth.add(dateFrom);
        datePickerFrom = new CreateJDatePicker(panelNorth);
        panelNorth.add(dateTo);
        datePickerTo = new CreateJDatePicker(panelNorth);
        panelNorth.add(incomes);
        panelNorth.add(expenses);
        panelNorth.add(ok);
        panelNorth.add(homePage);
        frame.add(panelNorth,BorderLayout.NORTH);
        panelCenter.setLayout(new GridLayout(1,2));
        panelCenter.add(jsp);
        jsp.setViewportView(costAndIncomeTable);
        chartPanel = new ChartPanel(chart);
        panelCenter.add(chartPanel);
        frame.add(panelCenter,BorderLayout.CENTER);
        panelSouth.add(deleteId);
        panelSouth.add(idForDelete);
        panelSouth.add(delete);
        frame.add(panelSouth,BorderLayout.SOUTH);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int id = Integer.parseInt(idForDelete.getText());
                try {
                    db.deleteCostOrIncome(id);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
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
        pieDataSet.clear();

        Object data[] = new Object[5];
        ArrayList<Category> categoryArray = null;
        try {
            categoryArray = db.getAllCategories();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        DataForPie[] dataPie = new DataForPie[categoryArray.size()];
        for(int i=0;i<categoryArray.size();i++) {
            String name = categoryArray.get(i).getCategoryName();
            dataPie[i] = new DataForPie(name);
        }
        for (int i = 0; i < myArray.size(); i++){
            int id = myArray.get(i).getId();
            String description = myArray.get(i).getDescription();
            double cost = myArray.get(i).getCost();
            Date date = myArray.get(i).getDate();
            Category category = myArray.get(i).getCategory();
            for(int k = 0;k<categoryArray.size();k++){
                if(category.getCategoryName().equals(dataPie[k].getName())){
                    dataPie[k].setCount(dataPie[k].getCount()+cost);
                }
            }
            data[0] = id;
            data[1] = description;
            data[2] = cost;
            data[3] = date;
            data[4] = category.getCategoryName();
           model.addRow(data);
        }
        for(int m=0;m<dataPie.length;m++){
            pieDataSet.setValue(dataPie[m].getName(),dataPie[m].getCount());
        }
        chart = ChartFactory.createPieChart3D("Pie Chart",pieDataSet);
        plot = (PiePlot3D)chart.getPlot();
        model.fireTableDataChanged();
        chartPanel.getChart().fireChartChanged();

    }

}
