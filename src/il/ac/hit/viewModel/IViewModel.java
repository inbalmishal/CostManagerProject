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
     * Set the view parameter.
     * @param view view type parameter.
     */
    public void setView(IView view);

    /**
     *Set the model parameter.
     * @param model Model type parameter.
     */
    public void setModel(IModel model);

    /**
     * Calls to a view method that show to the user a message if the action succeeded.
     * @param text Represent the exception message.
     */
    public void showGoodMessage(String text);

    /**
     * Calls to a view method that show to the user a message if the action failed.
     * @param text Represent the exception message.
     */
    public void showBadMessage(String text);

    /**
     * This method takes CostOrIncome type parameter and calls to a model method that adds it to the DB.
     * @param item CostOrIncome type parameter.
     */
    public void addCostOrIncome(CostOrIncome item);

    /**
     *This method take id of CostOrIncome type parameter and calls to a model method that deletes it from the DB.
     * @param id The id of CostOrIncome type parameter.
     */
    public void deleteCostOrIncome(int id);

    /**
     *This method take Category type parameter and calls to a model method that adds it to the DB.
     * @param item Category type parameter.
     */
    public void addNewCategory(Category item);

    /**
     *This method take Category type parameter and calls to a model method that deletes it from the DB.
     * @param item Category type parameter.
     */
    public void deleteCategory(Category item );

    /**
     * This method take date from and date to and calls to a model method that get from the DB all the costs between the dates.
     * @param from The first date we want to see.
     * @param to The last date we want to see.
     * @return Array list with all costs between the dates.
     */
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to);

    /**
     * This method take date from and date to and calls to a model method that get from the DB all the incomes between the dates.
     * @param from The first date we want to see.
     * @param to The last date we want to see.
     * @return Array list with all Incomes between the dates.
     */
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from,Date to);

    /**
     *This method take date "from" and date "to" and calls to a model method that get from the DB all the costs and incomes between the dates.
     * @param from The first date we want to see.
     * @param to The last date we want to see.
     * @return Array list with all costs and incomes between the dates.
     */
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from,Date to);

    /**
     * This method calls to a model method that calculate the balance in my DB.
     * @return The balance in my DB.
     */
    public double getTheBalance();

    /**
     *This method calls to a model method that get from the DB all the costs and incomes.
     * @return Array list with all costs and incomes.
     */
    public ArrayList<CostOrIncome> getAllCostsAndIncomes();

    /**
     *This method calls to a model method that get from the DB all the categories.
     * @return Array list with all categories in my DB.
     */
    public ArrayList<Category> getAllCategories();
}
