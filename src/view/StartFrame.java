package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartFrame {
    private JFrame frame;
    private JButton showMyExpenseIncome,addNewExpenseIncome,addNewCategory;
    private JCheckBox shekel,dollar,euro;
    private JTable categories;
    private JLabel icon;
    public StartFrame(){
        frame = new JFrame("Cost Manager");
        showMyExpenseIncome = new JButton("Show my expense and income");
        addNewExpenseIncome = new JButton("Add new expense or income");
        addNewCategory = new JButton("Add new category");
        icon = new JLabel(new ImageIcon("image.png"));

    }
    public void start(){
        frame.setLayout(new FlowLayout());
        frame.setSize(1000,1000);
        frame.add(icon);
        frame.add(showMyExpenseIncome);
        frame.add(addNewExpenseIncome);
        frame.add(addNewCategory);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        showMyExpenseIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DetailsFrame detailsFrame = new DetailsFrame();
                detailsFrame.start();
                frame.dispose();
            }
        });
        frame.setVisible(true);

    }
}
