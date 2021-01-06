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

/**
 * This is the frame of the details about the incomes, costs And their division by dates.
 * @author Inbal mishal and Tal levi
 */
public class DetailsFrame extends JFrame {
    private IViewModel vm;
    private JFrame frame;
    private JPanel panelNorth,panelCenter,panelSouth;
    private CreateJDatePicker datePickerFrom,datePickerTo;
    private JTextField tfIdForDelete;
    private JCheckBox cbIncomes, cbExpenses;
    private JTable tbCostAndIncomeTable;
    private JButton btOk, btHomePage, btDelete;
    private DefaultTableModel tbModel;
    private JLabel lbDateFrom, lbDateTo, lbDeleteId;
    private JScrollPane jsp;
    private DefaultPieDataset pieDataSet;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    /**
     * Create all the components in this frame.
     * @param vm Represent the view model that connected to the model.
     */
    DetailsFrame(IViewModel vm) {
        setVm(vm);
        frame = new JFrame("Cost Manager");
        btOk = new JButton("OK");
        btHomePage = new JButton("Home Page");
        btDelete = new JButton("Delete");
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        tfIdForDelete = new JTextField(10);
        cbIncomes = new JCheckBox("incomes ");
        cbExpenses = new JCheckBox(("expenses "));
        lbDateFrom = new JLabel("From:");
        lbDateTo = new JLabel("To:");
        lbDeleteId = new JLabel("id for delete: ");
        tbModel = new DefaultTableModel(null, new Object[]{"id","description", "cost", "date", "category"});
        pieDataSet = new DefaultPieDataset();

        //Create the table and pie chart with details from the DB.
        ArrayList<CostOrIncome> myArray = vm.getAllCostsAndIncomes();
        Object[] data = new Object[5];
        ArrayList<Category> categoryArray = vm.getAllCategories();
        DataForPie[] dataPie = new DataForPie[categoryArray.size()];
        for(int i=0;i<categoryArray.size();i++) {
            String name = categoryArray.get(i).getCategoryName();
            dataPie[i] = new DataForPie(name);
        }
        for (CostOrIncome costOrIncome : myArray) {
            int id = costOrIncome.getId();
            String description = costOrIncome.getDescription();
            double cost = costOrIncome.getCost();
            Date date = costOrIncome.getDate();
            Category category = costOrIncome.getCategory();
            for (int k = 0; k < categoryArray.size(); k++) {
                if (category.getCategoryName().equals(dataPie[k].getCategoryName())) {
                    dataPie[k].setMoneySum(dataPie[k].getMoneySum() + cost);
                }
            }
            data[0] = id;
            data[1] = description;
            data[2] = cost;
            data[3] = date;
            data[4] = category.getCategoryName();
            tbModel.addRow(data);

        }

        for (DataForPie dataForPie : dataPie) {
            pieDataSet.setValue(dataForPie.getCategoryName(), dataForPie.getMoneySum());
        }
        chart = ChartFactory.createPieChart3D("Pie Chart",pieDataSet);


        tbCostAndIncomeTable = new JTable(tbModel);
        jsp = new JScrollPane();
        jsp.setBounds(215, 71, 615, 237);
    }

    /**
     * Set the viewModel parameter.
     * @param vm Represent the view model that connected to the model.
     */
    public void setVm(IViewModel vm) {
        this.vm = vm;
    }


    /**
     * Organize all the components in this frame and create events listeners to the buttons.
     */
    public void start(){
        frame.setLayout(new BorderLayout());
        frame.setSize(1000,600);
        panelNorth.add(lbDateFrom);
        datePickerFrom = new CreateJDatePicker(panelNorth);
        panelNorth.add(lbDateTo);
        datePickerTo = new CreateJDatePicker(panelNorth);
        panelNorth.add(cbIncomes);
        panelNorth.add(cbExpenses);
        panelNorth.add(btOk);
        panelNorth.add(btHomePage);
        frame.add(panelNorth,BorderLayout.NORTH);
        panelCenter.setLayout(new GridLayout(1,2));
        panelCenter.add(jsp);
        jsp.setViewportView(tbCostAndIncomeTable);
        chartPanel = new ChartPanel(chart);
        panelCenter.add(chartPanel);
        frame.add(panelCenter,BorderLayout.CENTER);
        panelSouth.add(lbDeleteId);
        panelSouth.add(tfIdForDelete);
        panelSouth.add(btDelete);
        frame.add(panelSouth,BorderLayout.SOUTH);

        //Adding events listeners.

        //Delete expense or incomes from the DB and refresh the table and pie with this change.
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int id, n = 0;
                try {
                    //Take from the user the id of the CostOrIncome item.
                    id = Integer.parseInt(tfIdForDelete.getText());
                //Delete the CostOrIncome item with the id.
                vm.deleteCostOrIncome(id);
                Date dateFrom = null;
                Date dateTo = null;
                try {
                    //Take dates from the user.
                    dateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerFrom.getDatePicker().getJFormattedTextField().getText());
                    dateTo = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerTo.getDatePicker().getJFormattedTextField().getText());
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
                    checkedIncomes = cbIncomes.isSelected();
                    checkedExpenses = cbExpenses.isSelected();
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
                        tbModel.getDataVector().removeAllElements();
                        tbModel.fireTableDataChanged();
                        pieDataSet.clear();
                    }
                }
            }catch (NumberFormatException e) {
                //If the user don't give us a number.
                JOptionPane.showMessageDialog(null, "Id must have value", "Error!", JOptionPane.WARNING_MESSAGE);
            }
        }
        });
        //This button return the user to the StartFrame.
        btHomePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartFrame startFrame = new StartFrame(vm);
                startFrame.start();
                frame.dispose();
            }
        });
        //This button take details from the user and show information accordingly.

        btOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Date dateFrom = null;
                Date dateTo = null;
                int n = 0;
                try {
                    //Take dates from the user.
                    dateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerFrom.getDatePicker().getJFormattedTextField().getText());
                    dateTo = new SimpleDateFormat("dd-MM-yyyy").parse(datePickerTo.getDatePicker().getJFormattedTextField().getText());
                } catch (ParseException e) {
                    //Put 1 in n if the user don't put dates.
                    n = 1;
                }
                boolean checkedIncomes,checkedExpenses;
                checkedIncomes = cbIncomes.isSelected();
                checkedExpenses = cbExpenses.isSelected();
                //Show to the user all the details between dates.
                if(checkedExpenses && checkedIncomes){
                    if(n!=1){
                        changeTheTableAndPie(vm.getAllCostsAndIncomesBetweenDates(dateFrom,dateTo),1);
                    }else{
                        changeTheTableAndPie(vm.getAllCostsAndIncomes(),1);
                    }
                }
                //Show to the user only the incomes details between dates.
                else if(checkedIncomes){
                    if(n!=1){
                        changeTheTableAndPie(vm.getAllIncomesBetweenDates(dateFrom,dateTo),1);
                    }else{
                        changeTheTableAndPie(vm.getAllIncomes(),1);
                    }
                }
                //Show to the user only the expenses details between dates.
                else if(checkedExpenses){
                    if(n!=1){
                        changeTheTableAndPie(vm.getAllCostsBetweenDates(dateFrom,dateTo),-1);
                    }else{
                        changeTheTableAndPie(vm.getAllCosts(),-1);
                    }
                }
                //Remove all the details from the table and oie diagram.
                else{
                    tbModel.getDataVector().removeAllElements();
                    tbModel.fireTableDataChanged();
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
        tbModel.getDataVector().removeAllElements();
        tbModel.fireTableDataChanged();
        pieDataSet.clear();

        Object[] data = new Object[5];
        ArrayList<Category> categoryArray;
        //Get all the categories from the DB.
        categoryArray = vm.getAllCategories();
        DataForPie[] dataPie = new DataForPie[categoryArray.size()];
        //Put inside the array dataPie all the category name we get from DB.
        for(int i=0;i<categoryArray.size();i++) {
            String name = categoryArray.get(i).getCategoryName();
            dataPie[i] = new DataForPie(name);
        }
        for (CostOrIncome costOrIncome : myArray) {
            int id = costOrIncome.getId();
            String description = costOrIncome.getDescription();
            double cost = costOrIncome.getCost();
            Date date = costOrIncome.getDate();
            Category category = costOrIncome.getCategory();
            //Count all the expenses/incomes we get by categories, n give us information on what the user wants to view.
            for (int k = 0; k < categoryArray.size(); k++) {
                if (category.getCategoryName().equals(dataPie[k].getCategoryName())) {
                    dataPie[k].setMoneySum(dataPie[k].getMoneySum() + (cost * n));
                }
            }
            data[0] = id;
            data[1] = description;
            data[2] = cost;
            data[3] = date;
            data[4] = category.getCategoryName();
            //Upload the details to the table.
            tbModel.addRow(data);
        }
        //Upload the details to the pie diagram.
        for (DataForPie dataForPie : dataPie) {
            pieDataSet.setValue(dataForPie.getCategoryName(), dataForPie.getMoneySum());
        }
        //Refresh the frame with the new details inside table and pie chart.
        chart = ChartFactory.createPieChart3D("Pie Chart",pieDataSet);
        tbModel.fireTableDataChanged();
        chartPanel.getChart().fireChartChanged();

    }
}