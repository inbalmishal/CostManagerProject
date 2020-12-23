package il.ac.hit.view;

import il.ac.hit.model.Category;
import il.ac.hit.model.CostManagerException;
import il.ac.hit.model.CostOrIncome;
import il.ac.hit.model.DerbyDBModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCostOrIncomeFrame extends JFrame {
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JFrame frame;
    private ImageIcon okIcon;

    private JLabel title;
    private JPanel descriptionPanel;
    private JLabel description;
    private JTextField descriptionText;

    private JPanel costPanel;
    private JLabel cost;
    private JTextField costText;

    private JPanel datePanel;
    private JLabel date;
    private CreateJDatePicker datePicker;

    private JPanel categoryPanel;
    private JLabel category;
    private JComboBox chosenCategory;

    private JButton add;
    private JButton homePage;

    AddCostOrIncomeFrame() {
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        okIcon = new ImageIcon("ok.png");
        frame = new JFrame("Add New Category");
        title = new JLabel("Add Income Or Expense");
        description = new JLabel ("description:");
        descriptionText = new JTextField(40);
        cost = new JLabel("cost:");
        costText = new JTextField(40);
        date = new JLabel("date:");
        category = new JLabel("category:");

        //call to function that charges the categories from the db to this JComboBox
        chosenCategory = createChosenCategory();

        add = new JButton("add");
        homePage = new JButton("homePage");

        descriptionPanel = new JPanel();
        costPanel = new JPanel();
        datePanel = new JPanel();
        categoryPanel = new JPanel();

    }

    //in the future we need to change it - call with the vm
    public JComboBox createChosenCategory() {
        DerbyDBModel db = null;
        try {
            db = new DerbyDBModel();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        JComboBox categoriesJCombox = null;
        try {
            db.createDB();
            ArrayList<Category> categories = db.getAllCategories();
            String[] categoriesNames = new String[categories.size()];
            for(int i =0;i<categories.size();i++){
                categoriesNames[i] = categories.get(i).getCategoryName();
            }
            categoriesJCombox = new JComboBox(categoriesNames);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return categoriesJCombox;
    }

    public void start(){
        frame.setLayout(new BorderLayout());
        frame.setSize(1000,600);

        //north panel
        title.setFont(new Font("serif", Font.PLAIN, 40));
        panelNorth.add(title);
        panelNorth.setBackground(new Color(255,255,255));
        frame.add(panelNorth, BorderLayout.NORTH);

        //center panel
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));

        descriptionPanel.add(description);
        descriptionPanel.add(descriptionText);
        descriptionPanel.setBackground(new Color(240,240,255));
        panelCenter.add(descriptionPanel);


        costPanel.add(cost);
        costPanel.add(costText);
        costPanel.setBackground(new Color(240,240,255));
        panelCenter.add(costPanel);

        datePanel.add(date);
        datePicker = new CreateJDatePicker(datePanel);
        datePanel.setBackground(new Color(240,240,255));
        panelCenter.add(datePanel);

        categoryPanel.add(category);
        categoryPanel.add(chosenCategory);
        categoryPanel.setBackground(new Color(240,240,255));
        panelCenter.add(categoryPanel);

        frame.add(panelCenter, BorderLayout.CENTER);

        //south panel
        panelSouth.setLayout((new FlowLayout()));
        panelSouth.add(add);
        panelSouth.add(homePage);
        panelSouth.setBackground(new Color(240,240,255));
        frame.add(panelSouth, BorderLayout.SOUTH);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DerbyDBModel db=null;
                try {
                    db = new DerbyDBModel();
                } catch (CostManagerException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Error!",HEIGHT);
                }

                String desc = descriptionText.getText();
                double cost = Double.parseDouble(costText.getText());
                Date date = null;
                try {
                     date = new SimpleDateFormat("dd-MM-yyyy").parse(datePicker.datePicker.getJFormattedTextField().getText());
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Error!",HEIGHT);
                }
                Category category = new Category(chosenCategory.getSelectedItem().toString());
                CostOrIncome costOrIncome = new CostOrIncome(desc,cost,new java.sql.Date(date.getYear(),date.getMonth(),date.getDay()),category);
                try {
                    db.addCostOrIncome(costOrIncome);
                    JOptionPane.showMessageDialog(null,"The object has been added","Success!",HEIGHT,okIcon);
                } catch (CostManagerException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Error!",HEIGHT);
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
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }

}
