package il.ac.hit.viewModel;

import il.ac.hit.model.Category;
import il.ac.hit.model.CostOrIncome;
import il.ac.hit.model.IModel;
import il.ac.hit.view.IView;

import java.util.ArrayList;
import java.util.Date;

/**
 * This interface included all of the methods that connect between the model and the view.
 * @author Inbal mishal and Tal levi
 */
public interface IViewModel {

    /**
     *
     * @param view
     */
    public void setView(IView view);

    /**
     *
     * @param model
     */
    public void setModel(IModel model);

    /**
     *
     * @param text
     */
    public void showGoodMessage(String text);

    /**
     *
     * @param text
     */
    public void showBadMessage(String text);

    /**
     *
     * @param item
     */
    public void addCostOrIncome(CostOrIncome item);

    /**
     *
     * @param id
     */
    public void deleteCostOrIncome(int id);

    /**
     *
     * @param item
     */
    public void addNewCategory(Category item);

    /**
     *
     * @param item
     */
    public void deleteCategory(Category item );

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to);

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from,Date to);

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from,Date to);

    /**
     *
     * @return
     */
    public double getTheBalance();

    /**
     *
     * @return
     */
    public ArrayList<CostOrIncome> getAllCostsAndIncomes();

    /**
     *
     * @return
     */
    public ArrayList<Category> getAllCategories();
}
