package il.ac.hit.view;

import il.ac.hit.viewModel.IViewModel;

import javax.swing.*;

import static java.awt.image.ImageObserver.HEIGHT;

/**
 * Represent the user interface of the application.
 * @author Inbal mishal and Tal levi
 */
public class View implements IView {
    private IViewModel vm;
    private ImageIcon okIcon;

    /**
     * Create the view object and it's components.
     */
    public View() {
        okIcon = new ImageIcon("ok.png");
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
