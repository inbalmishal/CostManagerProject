package il.ac.hit.view;

import javax.swing.*;

public class VProgram {
    public static void main(String args[]){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StartFrame ob = new StartFrame();
                ob.start();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }
}
