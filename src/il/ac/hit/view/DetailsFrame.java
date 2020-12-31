package il.ac.hit.view;

import il.ac.hit.viewModel.IViewModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.*;

import il.ac.hit.model.Category;
import il.ac.hit.model.CostOrIncome;

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
    private IViewModel vm;
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
    private ChartPanel chartPanel;
    private ImageIcon okIcon;

    //Create all the components in this frame.
    DetailsFrame(IViewModel vm) {
        setVm(vm);
        frame = new JFrame("Cost Manager");
        okIcon = new ImageIcon("ok.png");
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

        //Create the table and pie chart with details from the DB.
        ArrayList<CostOrIncome> myArray = vm.getAllCostsAndIncomes();
        Object data[] = new Object[5];
        ArrayList<Category> categoryArray = vm.getAllCategories();
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


        costAndIncomeTable = new JTable(model);
        jsp = new JScrollPane();
        jsp.setBounds(215, 71, 615, 237);
    }

    public void setVm(IViewModel vm) {
        this.vm = vm;
    }

    //Organize all the components in this frame.
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

        //Adding events listeners.

        //Delete expense or incomes from the DB and refresh the table and pie with this change.
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int id = 0, n = 0;
                try {
                    //Take from the user the id of the CostOrIncome item.
                    id = Integer.parseInt(idForDelete.getText());
                //Delete the CostOrIncome item with the id.
                vm.deleteCostOrIncome(id);
                Date dateFrom = null;
                Date dateTo = null;
                try {
                    //Take dates from the user.
                    dateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerFrom.datePicker.getJFormattedTextField().getText());
                    dateTo = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerTo.datePicker.getJFormattedTextField().getText());
                } catch (ParseException e) {
                    //Put 1 in n if the user don't put dates.
                    n = 1;
                }
                if(n == 1){
                    //Show to the user all the details without dates or expense/income specifics.
                    changeTheTableAndPie(vm.getAllCostsAndIncomes(),1);
                }
                else{
                    boolean checkedIncomes,checkedExpenses;
                    checkedIncomes = incomes.isSelected();
                    checkedExpenses = expenses.isSelected();
                    //Show to the user all the details between dates.
                    if(checkedExpenses && checkedIncomes){
                        changeTheTableAndPie(vm.getAllCostsAndIncomesBetweenDates(dateFrom,dateTo),1);
                    }
                    //Show to the user only the incomes details between dates.
                    else if(checkedIncomes){

                        changeTheTableAndPie(vm.getAllIncomesBetweenDates(dateFrom,dateTo),1);
                    }
                    //Show to the user only the expenses details between dates.
                    else if(checkedExpenses){

                        changeTheTableAndPie(vm.getAllCostsBetweenDates(dateFrom,dateTo),-1);
                    }
                    //Remove all the details from the table and oie diagram.
                    else{
                        model.getDataVector().removeAllElements();
                        model.fireTableDataChanged();
                        pieDataSet.clear();
                    }
                }
            }catch (NumberFormatException e) {
                //If the user don't give us a number.
                JOptionPane.showMessageDialog(null, "Id must have value", "Error!", HEIGHT);
            }
        }
        });
        //This button return the user to the StartFrame.
        homePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartFrame startFrame = new StartFrame(vm);
                startFrame.start();
                frame.dispose();
            }
        });
        //This button take details from the user and show information accordingly.

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Date dateFrom = null;
                Date dateTo = null;
                try {
                    //Take dates from the user.
                    dateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerFrom.datePicker.getJFormattedTextField().getText());
                    dateTo = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerTo.datePicker.getJFormattedTextField().getText());
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Error!",HEIGHT);
                }
                boolean checkedIncomes,checkedExpenses;
                checkedIncomes = incomes.isSelected();
                checkedExpenses = expenses.isSelected();
                //Show to the user all the details between dates.
                if(checkedExpenses && checkedIncomes){

                    changeTheTableAndPie(vm.getAllCostsAndIncomesBetweenDates(dateFrom,dateTo),1);
                }
                //Show to the user only the incomes details between dates.
                else if(checkedIncomes){

                    changeTheTableAndPie(vm.getAllIncomesBetweenDates(dateFrom,dateTo),1);
                }
                //Show to the user only the expenses details between dates.
                else if(checkedExpenses){

                    changeTheTableAndPie(vm.getAllCostsBetweenDates(dateFrom,dateTo),-1);
                }
                //Remove all the details from the table and oie diagram.
                else{
                    model.getDataVector().removeAllElements();
                    model.fireTableDataChanged();
                    pieDataSet.clear();
                }


            }
        });

        //When the user close the screen the program shutdown.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);

    }

    //Refresh to the table and pie diagram.
    private void changeTheTableAndPie(ArrayList<CostOrIncome> myArray,int n) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        pieDataSet.clear();

        Object data[] = new Object[5];
        ArrayList<Category> categoryArray = null;
        //Get all the categories from the DB.
        categoryArray = vm.getAllCategories();
        DataForPie[] dataPie = new DataForPie[categoryArray.size()];
        //Put inside the array dataPie all the category name we get from DB.
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
            //Count all the expenses/incomes we get by categories, n give us information on what the user wants to view.
            for(int k = 0;k<categoryArray.size();k++){
                if(category.getCategoryName().equals(dataPie[k].getName())){
                    dataPie[k].setCount(dataPie[k].getCount()+(cost*n));
                }
            }
            data[0] = id;
            data[1] = description;
            data[2] = cost;
            data[3] = date;
            data[4] = category.getCategoryName();
            //Upload the details to the table.
            model.addRow(data);
        }
        //Upload the details to the pie diagram.
        for(int m=0;m<dataPie.length;m++){
            pieDataSet.setValue(dataPie[m].getName(),dataPie[m].getCount());
        }
        //Refresh the frame with the new details inside table and pie chart.
        chart = ChartFactory.createPieChart3D("Pie Chart",pieDataSet);
        model.fireTableDataChanged();
        chartPanel.getChart().fireChartChanged();

    }
}