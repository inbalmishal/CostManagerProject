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

/**
 * This is the frame of adding or deleting category.
 * @author Inbal mishal and Tal levi
 */
public class AddOrDeleteCategoryFrame extends JFrame {
    private IViewModel vm;
    private JFrame frame;
    private JPanel panelNorth;
    private JPanel panelWest;
    private JPanel panelCenter;
    private JButton btAdd;
    private JButton btDelete;
    private JButton btRefreshCategories;
    private JButton btHomePage;
    private JTextField tfCategory;
    private JTextArea taCategoryList;
    private JLabel lbNewCategoryName;
    private JLabel lbTitle;
    private JLabel lbListTitle;
    private JLabel lbIcon;
    private JScrollPane jsp;

    /**
     * Create all the components in this frame.
     * @param vm Represent the viewModel that connected to the model.
     */
    AddOrDeleteCategoryFrame(IViewModel vm){
        setVm(vm);
        frame = new JFrame("Add/Delete Category");
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();

        btAdd = new JButton("add");
        btDelete = new JButton("delete");
        tfCategory = new JTextField(40);
        lbNewCategoryName = new JLabel("category name: ");
        lbListTitle = new JLabel("The categories are:\n");
        taCategoryList = new JTextArea();
        btRefreshCategories = new JButton("Refresh Categories");
        lbTitle = new JLabel("Add/Delete Category");
        btHomePage = new JButton("homePage");

        //create img and change it's size
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("categories.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error!",HEIGHT);
        }
        Image dImg = img.getScaledInstance(450, 350, Image.SCALE_SMOOTH);
        lbIcon = new JLabel(new ImageIcon(dImg));

        jsp = new JScrollPane();
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
        frame.setSize(1000,650);

        //north panel
        lbTitle.setFont(new Font("serif", Font.PLAIN, 40));
        panelNorth.add(lbTitle);
        panelNorth.setBackground(new Color(255,255,255));
        frame.add(panelNorth, BorderLayout.NORTH);

        //center panel
        lbNewCategoryName.setFont(new Font("serif", Font.PLAIN, 20));
        panelCenter.add(lbNewCategoryName);
        panelCenter.add(tfCategory);
        tfCategory.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCenter.add(btAdd);
        panelCenter.add(btDelete);
        btAdd.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btDelete.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCenter.add(btHomePage);
        panelCenter.add(lbIcon);
        panelCenter.setBackground(new Color(240,240,255));
        frame.add(panelCenter, BorderLayout.CENTER);

        //west panel
        panelWest.setLayout(new BorderLayout());
        lbListTitle.setFont(new Font("serif", Font.PLAIN, 20));
        panelWest.add(lbListTitle, BorderLayout.NORTH);
        taCategoryList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelWest.add(taCategoryList, BorderLayout.CENTER);
        btRefreshCategories.setPreferredSize(new Dimension(100, 50));
        btRefreshCategories.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelWest.add(btRefreshCategories, BorderLayout.SOUTH);
        panelWest.setSize(150,130);
        panelWest.setBackground(new Color(240,240,255));
        jsp.setBounds(panelWest.getBounds());
        panelWest.add(jsp);
        jsp.setViewportView(taCategoryList);
        frame.add(panelWest, BorderLayout.WEST);

        //Adding events listeners.
        //delete category from the DB.
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the category from the user, create the category object and delete it from the DB
                String s = tfCategory.getText();
                if (s.length() != 0){
                    Category category = new Category(s);
                    vm.deleteCategory(category);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Category must have at least one letter", "Error!", HEIGHT);
                }
            }
        });

        //add category to the DB.
        btAdd.addActionListener(new ActionListener() { //connect to the VM
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the category from the user, create the category object and add it to the DB
                String s = tfCategory.getText();
                if (s.length() != 0){
                    Category category = new Category(s);
                    vm.addNewCategory(category);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Category must have at least one letter", "Error!", HEIGHT);
                }
            }
        });

        //refresh the category list according to the current state
        btRefreshCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the categories from the DB
                ArrayList<Category> categories = vm.getAllCategories();
                //clear the text area
                taCategoryList.setText("");
                //insert each category from the DB to the text area
                for(Category category:categories){
                    taCategoryList.append(category.toString());
                    taCategoryList.append("\n");
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