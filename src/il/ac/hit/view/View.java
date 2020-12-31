package il.ac.hit.view;

import il.ac.hit.viewModel.IViewModel;

import javax.swing.*;

import static java.awt.image.ImageObserver.HEIGHT;

public class View implements IView {
    private IViewModel vm;
    private ImageIcon okIcon;

    public View() {
        okIcon = new ImageIcon("ok.png");
    }

    @Override
    public void start() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                {
                    StartFrame startFrame = new StartFrame(vm);
                    startFrame.start();
                }
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showGoodMessage(String text) {
        if(SwingUtilities.isEventDispatchThread()){
            JOptionPane.showMessageDialog(null,text,"Success!",HEIGHT,okIcon);
        }else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null,text,"Success!",HEIGHT,okIcon);
                }
            });
        }
    }

    @Override
    public void showBadMessage(String text) {
        if(SwingUtilities.isEventDispatchThread()){
            JOptionPane.showMessageDialog(null,text,"Error!",HEIGHT);
        }else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null,text,"Error!",HEIGHT);
                }
            });
        }
    }
}
