package view;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class AddCategoryFrame extends JFrame {
    private JPanel panelNorth;
    private JPanel panelWest;
    private JPanel panelCenter;
    private JFrame frame;

    private JButton add;
    private JTextField category;
    private JLabel newCategoryName;
    private JTextArea categoryList;
    private JButton refreshCategories;
    private JLabel title;
    private JLabel listTitle;
    private JButton homePage;
    private JLabel icon;

    AddCategoryFrame(){
        frame = new JFrame("Add New Category");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();

        add = new JButton("add");
        category = new JTextField(40);
        newCategoryName = new JLabel("new category name: ");
        listTitle = new JLabel("The categories are:\n");
        categoryList = new JTextArea();
        refreshCategories = new JButton("Refresh Categories");
        title = new JLabel("Add New Category");
        homePage = new JButton("homePage");
        icon = new JLabel();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("categories.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dImg = img.getScaledInstance(450, 350, Image.SCALE_SMOOTH);
        ImageIcon categoryImage = new ImageIcon(dImg);
        icon.setIcon(categoryImage);
    }

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
        add.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
        frame.add(panelWest, BorderLayout.WEST);

        add.addActionListener(new ActionListener() { //connect to the VM
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DerbyDBModel db = null;
                try {
                    db = new DerbyDBModel();
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
                String s = category.getText();
                Category category = new Category(s);
                try {
                    db.addNewCategory(category);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
            }
        });

        refreshCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DerbyDBModel db = null;
                try {
                    db = new DerbyDBModel();
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
                try {
                    db.createDB();
                    ArrayList<Category> categories = db.getAllCategories();
                    categoryList.setText("");
                    for(Category category:categories){
                        categoryList.append(category.toString());
                        categoryList.append("\n");
                    }
                } catch (CostManagerException e) {
                    e.printStackTrace();
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
