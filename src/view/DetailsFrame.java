package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DetailsFrame extends JFrame {
    private JFrame frame;
    private CreateJDatePicker datePickerFrom,datePickerTo;
    private JCheckBox incomes,expenses;
    private JTable costAndIncomeTable;
    private JButton ok,homePage;

    DetailsFrame(){
        frame = new JFrame("Cost Manager");
        ok = new JButton("OK");
        homePage = new JButton("Home Page");

    }
    public void start(){
        frame.setLayout(new FlowLayout());
        frame.setSize(1000,1000);
        datePickerFrom = new CreateJDatePicker(frame);
        datePickerTo = new CreateJDatePicker(frame);
        frame.add(ok);
        frame.add(homePage);
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
