package il.ac.hit.view;


import il.ac.hit.model.CostManagerException;
import il.ac.hit.model.DerbyDBModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartFrame {
    private JFrame frame;
    private JPanel panelNorth,panelWest,panelCenter;
    private JButton showMyExpenseIncome,addNewExpenseIncome,addNewCategory;
    private JLabel icon,showBalance,title;
    private double balance;
    public StartFrame() {

        frame = new JFrame("Cost Manager");
        DerbyDBModel db = null;
        try {
            db = new DerbyDBModel();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        showMyExpenseIncome = new JButton("Show my expense and income");
        addNewExpenseIncome = new JButton("Add new expense or income");
        addNewCategory = new JButton("Add or delete category");
        icon = new JLabel(new ImageIcon("Image.png"));
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        try {
            balance = db.getTheBalance();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        showBalance = new JLabel("Hey user,\n your balance is :" + balance);
        title = new JLabel("Cost Manager");
        title.setFont(new Font("serif",Font.PLAIN,40));
        showBalance.setFont(new Font("serif",Font.PLAIN,40));

    }
    public void start(){
        frame.setLayout(new BorderLayout());
        frame.setSize(1000,600);
        panelNorth.setLayout(new GridLayout(1,2));
        panelNorth.setBackground(Color.white);
        panelWest.setLayout(new GridLayout(3,1));
        panelCenter.setLayout(new BorderLayout());
        panelNorth.add(icon,BorderLayout.WEST);
        panelNorth.add(title);
        frame.add(panelNorth,BorderLayout.NORTH);
        panelWest.add(showMyExpenseIncome);
        panelWest.add(addNewExpenseIncome);
        panelWest.add(addNewCategory);
        frame.add(panelWest,BorderLayout.WEST);
        panelCenter.add(showBalance,BorderLayout.CENTER);
        frame.add(panelCenter,BorderLayout.CENTER);


        addNewExpenseIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddCostOrIncomeFrame addCostOrIncomeFrame = new AddCostOrIncomeFrame();
                addCostOrIncomeFrame.start();
                frame.dispose();
            }
        });
        addNewCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddOrDeleteCategoryFrame addOrDeleteCategoryFrame = new AddOrDeleteCategoryFrame();
                addOrDeleteCategoryFrame.start();
                frame.dispose();
            }
        });
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
