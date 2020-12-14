package model;
import java.util.ArrayList;
import java.util.Date;

public interface IModel {
    public void addCostOrIncome(CostOrIncome item ) throws CostManagerException;
    public void deleteCostOrIncome(CostOrIncome item) throws CostManagerException;
    public void addNewCategory(Category item) throws CostManagerException;
    public void deleteCategory(Category item ) throws CostManagerException;
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from,Date to) throws CostManagerException;
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from,Date to) throws CostManagerException;
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from,Date to) throws CostManagerException;
    public double getTheBalance() throws CostManagerException;
    public ArrayList<CostOrIncome> getAllCostsAndIncomes() throws CostManagerException;
    public ArrayList<Category> getAllCategories() throws CostManagerException;
    public void createDB()throws CostManagerException;
}