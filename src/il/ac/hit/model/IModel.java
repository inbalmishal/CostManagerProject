package il.ac.hit.model;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is the interface for all model method.
 * @author Inbal mishal and Tal levi.
 */
public interface IModel {
    /**
     * This method take CostOrIncome type parameter and adds it to the DB.
     * @param item CostOrIncome type parameter.
     * @throws CostManagerException Problem with the DB.
     */
    void addCostOrIncome(CostOrIncome item) throws CostManagerException;

    /**
     * This method take id of CostOrIncome type parameter and deletes it from the DB.
     * @param id The id of CostOrIncome type parameter.
     * @throws CostManagerException The id not exist or problem with the DB.
     */
    void deleteCostOrIncome(int id) throws CostManagerException;

    /**
     * This method take Category type parameter and adds it to the DB.
     * @param item Category type parameter.
     * @throws CostManagerException The category already exist or problem with the DB.
     */
    void addNewCategory(Category item) throws CostManagerException;

    /**
     * This method take Category type parameter and deletes it from the DB.
     * @param item Category type parameter.
     * @throws CostManagerException This category doesn't exist or problem with the DB.
     */
    void deleteCategory(Category item) throws CostManagerException;

    /**
     * This method take date from and date to and get from the DB all the costs between the dates.
     * @param from The first date we want to see.
     * @param to The last date we want to see.
     * @return Array list with all costs between the dates.
     * @throws CostManagerException The date 'from' come after the date 'to'.
     */
    ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to) throws CostManagerException;
    /**
     * This method take date from and date to and get from the DB all the incomes between the dates.
     * @param from The first date we want to see.
     * @param to The last date we want to see.
     * @return Array list with all incomes between the dates.
     * @throws CostManagerException The date 'from' come after the date 'to'.
     */
    ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from, Date to) throws CostManagerException;
    /**
     * This method take date "from" and date "to" and get from the DB all the costs and incomes between the dates.
     * @param from The first date we want to see.
     * @param to The last date we want to see.
     * @return Array list with all costs and incomes between the dates.
     * @throws CostManagerException The date 'from' come after the date 'to'
     */
    ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from, Date to) throws CostManagerException;

    /**
     * This method calculate the balance in my DB.
     * @return The balance in my DB.
     * @throws CostManagerException Problem with the DB.
     */
    double getTheBalance() throws CostManagerException;
    /**
     * This method get from the DB all the costs and incomes.
     * @return Array list with all costs and incomes in my DB.
     * @throws CostManagerException Problem with the DB.
     */
    ArrayList<CostOrIncome> getAllCostsAndIncomes() throws CostManagerException;

    /**
     * This method get from the DB all the categories.
     * @return Array list with all categories in my DB.
     * @throws CostManagerException Problem with the DB.
     */
    ArrayList<Category> getAllCategories() throws CostManagerException;

    /**
     * This method open at the beginning of the application running and create the tables and default categories,
     * but only if the tables and categories not exists before.
     * @throws CostManagerException Problem with the DB.
     */
    void createDB()throws CostManagerException;
}