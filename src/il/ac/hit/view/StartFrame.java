package il.ac.hit.view;

import il.ac.hit.viewModel.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartFrame {
    private IViewModel vm;
    private JFrame frame;
    private JPanel panelNorth,panelWest,panelCenter;
    private JButton showMyExpenseIncome,addNewExpenseIncome,addOrDeleteNewCategory;
    private JLabel icon,showBalance,title;
    private double balance;

    //Create all the components in this frame.
    public StartFrame(IViewModel vm) {
        setVm(vm);
        frame = new JFrame("Cost Manager");
        showMyExpenseIncome = new JButton("Show my expense and income");
        addNewExpenseIncome = new JButton("Add new expense or income");
        addOrDeleteNewCategory = new JButton("Add or delete category");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        balance = vm.getTheBalance();
        showBalance = new JLabel("Hey user,\n your balance is :" + balance);
        icon = new JLabel(new ImageIcon("Image.png"));
        title = new JLabel("Cost Manager");
        title.setFont(new Font("serif",Font.PLAIN,40));
        showBalance.setFont(new Font("serif",Font.PLAIN,40));
    }

    public void setVm(IViewModel vm) {
        this.vm = vm;
    }

    //Organize all the components in this frame.
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
        panelWest.add(addOrDeleteNewCategory);
        frame.add(panelWest,BorderLayout.WEST);
        panelCenter.add(showBalance,BorderLayout.CENTER);
        frame.add(panelCenter,BorderLayout.CENTER);

        //Adding events listeners.

        //This button move the user to the AddCostOrIncomeFrame.
        addNewExpenseIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddCostOrIncomeFrame addCostOrIncomeFrame = new AddCostOrIncomeFrame(vm);
                addCostOrIncomeFrame.start();
                frame.dispose();
            }
        });
        //This button move the user to the AddOrDeleteCategoryFrame.
        addOrDeleteNewCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddOrDeleteCategoryFrame addOrDeleteCategoryFrame = new AddOrDeleteCategoryFrame(vm);
                addOrDeleteCategoryFrame.start();
                frame.dispose();
            }
        });
        //When the user close the screen the program shutdown.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //This button move the user to the DetailsFrame.
        showMyExpenseIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DetailsFrame detailsFrame = new DetailsFrame(vm);
                detailsFrame.start();
                frame.dispose();
            }
        });
        frame.setVisible(true);

    }
}
