package view;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleGUI {
    private JFrame frame;
    private JPanel panel1,panel2,panel3,panel4;
    private JButton showMyExpenseIncome,addNewExpenseIncome,addNewCategory,deleteExpenseIncome,showDetails,addNew;
    private JCheckBox incomes,expenses,shekel,dollar,euro;
    private JTextField dateFrom,dateTo;
    private JTable costAndIncomeTable,categories;
    private CreateJDatePicker datePickerFrom,datePickerTo;
    public SimpleGUI(){
        frame = new JFrame("Cost Manager");
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

    }
    public void start(){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }
}
