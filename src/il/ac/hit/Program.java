package il.ac.hit;

import il.ac.hit.model.*;
import il.ac.hit.view.*;
import il.ac.hit.viewModel.*;

/**
 * This Class Create the model, view and the view model and start the running of the application.
 *  @author Inbal mishal and Tal levi
 */
public class Program {
    /**
     * Create the model, view and the view model, connect between them and start the running of the application.
     * @param args Stores the incoming command line arguments for the program.
     */
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
