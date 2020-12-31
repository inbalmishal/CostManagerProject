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
    private JButton btShowMyExpenseIncome, btAddNewExpenseIncome, btAddOrDeleteNewCategory;
    private JLabel lbIcon, lbShowBalance, lbTitle;
    private double balance;

    //Create all the components in this frame.
    public StartFrame(IViewModel vm) {
        setVm(vm);
        frame = new JFrame("Cost Manager");
        btShowMyExpenseIncome = new JButton("Show my expense and income");
        btAddNewExpenseIncome = new JButton("Add new expense or income");
        btAddOrDeleteNewCategory = new JButton("Add or delete category");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        balance = vm.getTheBalance();
        lbShowBalance = new JLabel("Hey user,\n your balance is :" + balance);
        lbIcon = new JLabel(new ImageIcon("Image.png"));
        lbTitle = new JLabel("Cost Manager");
        lbTitle.setFont(new Font("serif",Font.PLAIN,40));
        lbShowBalance.setFont(new Font("serif",Font.PLAIN,40));
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
        panelNorth.add(lbIcon,BorderLayout.WEST);
        panelNorth.add(lbTitle);
        frame.add(panelNorth,BorderLayout.NORTH);
        panelWest.add(btShowMyExpenseIncome);
        panelWest.add(btAddNewExpenseIncome);
        panelWest.add(btAddOrDeleteNewCategory);
        frame.add(panelWest,BorderLayout.WEST);
        panelCenter.add(lbShowBalance,BorderLayout.CENTER);
        frame.add(panelCenter,BorderLayout.CENTER);

        //Adding events listeners.

        //This button move the user to the AddCostOrIncomeFrame.
        btAddNewExpenseIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddCostOrIncomeFrame addCostOrIncomeFrame = new AddCostOrIncomeFrame(vm);
                addCostOrIncomeFrame.start();
                frame.dispose();
            }
        });
        //This button move the user to the AddOrDeleteCategoryFrame.
        btAddOrDeleteNewCategory.addActionListener(new ActionListener() {
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
        btShowMyExpenseIncome.addActionListener(new ActionListener() {
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
