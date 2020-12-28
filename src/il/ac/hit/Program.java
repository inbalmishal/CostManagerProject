package il.ac.hit;

import il.ac.hit.model.*;
import il.ac.hit.view.*;
import il.ac.hit.viewModel.*;

import javax.swing.*;


public class Program {
    public static void main(String[] args) {
        IModel model = null;
        IView view = new View();
        IViewModel vm = new ViewModel();
        try {
            model = new DerbyDBModel();
            model.createDB();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        view.setViewModel(vm);
        vm.setModel(model);
        vm.setView(view);
        view.start();
    }
}
