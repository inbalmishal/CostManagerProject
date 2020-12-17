package view;

import javax.swing.*;

public class VProgram {
    public static void main(String args[]){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SimpleGUI ob = new SimpleGUI();
                ob.start();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }
}
