package il.ac.hit.view;

import il.ac.hit.model.Category;
import il.ac.hit.model.CostOrIncome;
import il.ac.hit.viewModel.IViewModel;

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

import static il.ac.hit.view.Currency.*;

public class AddCostOrIncomeFrame extends JFrame {
    private IViewModel vm;
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

    private JPanel currencyPanel;
    private JLabel currency;
    private JComboBox chosenCurrency;

    private JButton add;
    private JButton homePage;

    AddCostOrIncomeFrame(IViewModel vm) {
        setVm(vm);
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
        currency = new JLabel("currency:");

        //call to function that charges the categories from the db to this JComboBox
        chosenCategory = createChosenCategory();
        chosenCurrency = createChosenCurrency();

        add = new JButton("add");
        homePage = new JButton("homePage");

        descriptionPanel = new JPanel();
        costPanel = new JPanel();
        datePanel = new JPanel();
        categoryPanel = new JPanel();
        currencyPanel= new JPanel();
    }

    public void setVm(IViewModel vm) {
        this.vm = vm;
    }

    public JComboBox createChosenCategory() {
        JComboBox categoriesJCombox = null;
        ArrayList<Category> categories = vm.getAllCategories();
        String[] categoriesNames = new String[categories.size()];
        for(int i =0;i<categories.size();i++){
            categoriesNames[i] = categories.get(i).getCategoryName();
        }
        categoriesJCombox = new JComboBox(categoriesNames);
        return categoriesJCombox;
    }

    public JComboBox createChosenCurrency(){
        JComboBox currenciesJCombox = null;
        String[] Currencies = {"ILS","USD","EUR"};
        currenciesJCombox = new JComboBox(Currencies);
        return currenciesJCombox;
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

        currencyPanel.add(currency);
        currencyPanel.add(chosenCurrency);
        currencyPanel.setBackground(new Color(240,240,255));
        panelCenter.add(currencyPanel);

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
                String desc = descriptionText.getText();
                double cost = 0;
                try {
                    cost = Double.parseDouble(costText.getText());

                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd-MM-yyyy").parse(datePicker.datePicker.getJFormattedTextField().getText());
                    Category category = new Category(chosenCategory.getSelectedItem().toString());
                    String currency = chosenCurrency.getSelectedItem().toString();
                    switch (currency) {
                        case "EUR":
                            EUR.setExchangeRate(3.9355);
                            cost = EUR.convertToShekels(cost);
                            break;
                        case "USD":
                            USD.setExchangeRate(3.2224);
                            cost = USD.convertToShekels(cost);
                            break;
                    }
                    CostOrIncome costOrIncome = new CostOrIncome(desc, cost, new java.sql.Date(date.getYear(), date.getMonth(), date.getDay()), category);

                    vm.addCostOrIncome(costOrIncome);
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", HEIGHT);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "the cost must be a double", "Error!", HEIGHT);
                }
            }
        });


        homePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartFrame startFrame = new StartFrame(vm);
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
