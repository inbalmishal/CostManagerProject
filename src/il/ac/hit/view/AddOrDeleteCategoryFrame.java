package il.ac.hit.view;

import il.ac.hit.model.*;
import il.ac.hit.viewModel.IViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class AddOrDeleteCategoryFrame extends JFrame {
    private IViewModel vm;
    private JFrame frame;
    private JPanel panelNorth;
    private JPanel panelWest;
    private JPanel panelCenter;
    private JButton add;
    private JButton delete;
    private JButton refreshCategories;
    private JButton homePage;
    private JTextField category;
    private JTextArea categoryList;
    private JLabel newCategoryName;
    private JLabel title;
    private JLabel listTitle;
    private JLabel icon;
    private JScrollPane jsp;

    //Create all the components in this frame.
    AddOrDeleteCategoryFrame(IViewModel vm){
        setVm(vm);
        frame = new JFrame("Add/Delete Category");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();

        add = new JButton("add");
        delete = new JButton("delete");
        category = new JTextField(40);
        newCategoryName = new JLabel("category name: ");
        listTitle = new JLabel("The categories are:\n");
        categoryList = new JTextArea();
        refreshCategories = new JButton("Refresh Categories");
        title = new JLabel("Add/Delete Category");
        homePage = new JButton("homePage");

        //create img and change it's size
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("categories.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error!",HEIGHT);
        }
        Image dImg = img.getScaledInstance(450, 350, Image.SCALE_SMOOTH);
        icon = new JLabel(new ImageIcon(dImg));

        jsp = new JScrollPane();
    }

    public void setVm(IViewModel vm) {
        this.vm = vm;
    }

    //Organize all the components in this frame.
    public void start(){
        frame.setLayout(new BorderLayout());
        frame.setSize(1000,650);

        //north panel
        title.setFont(new Font("serif", Font.PLAIN, 40));
        panelNorth.add(title);
        panelNorth.setBackground(new Color(255,255,255));
        frame.add(panelNorth, BorderLayout.NORTH);

        //center panel
        newCategoryName.setFont(new Font("serif", Font.PLAIN, 20));
        panelCenter.add(newCategoryName);
        panelCenter.add(category);
        category.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCenter.add(add);
        panelCenter.add(delete);
        add.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        delete.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCenter.add(homePage);
        panelCenter.add(icon);
        panelCenter.setBackground(new Color(240,240,255));
        frame.add(panelCenter, BorderLayout.CENTER);

        //west panel
        panelWest.setLayout(new BorderLayout());
        listTitle.setFont(new Font("serif", Font.PLAIN, 20));
        panelWest.add(listTitle, BorderLayout.NORTH);
        categoryList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelWest.add(categoryList, BorderLayout.CENTER);
        refreshCategories.setPreferredSize(new Dimension(100, 50));
        refreshCategories.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelWest.add(refreshCategories, BorderLayout.SOUTH);
        panelWest.setSize(150,130);
        panelWest.setBackground(new Color(240,240,255));
        jsp.setBounds(panelWest.getBounds());
        panelWest.add(jsp);
        jsp.setViewportView(categoryList);
        frame.add(panelWest, BorderLayout.WEST);

        //Adding events listeners.
        //delete category from the DB.
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the category from the user, create the category object and delete it from the DB
                String s = category.getText();
                Category category = new Category(s);
                vm.deleteCategory(category);
            }
        });

        //add category to the DB.
        add.addActionListener(new ActionListener() { //connect to the VM
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the category from the user, create the category object and add it to the DB
                String s = category.getText();
                Category category = new Category(s);
                vm.addNewCategory(category);
            }
        });

        //refresh the category list according to the current state
        refreshCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the categories from the DB
                ArrayList<Category> categories = vm.getAllCategories();
                //clear the text area
                categoryList.setText("");
                //insert each category from the DB to the text area
                for(Category category:categories){
                    categoryList.append(category.toString());
                    categoryList.append("\n");
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

        //When the user close the screen the program shutdown.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
