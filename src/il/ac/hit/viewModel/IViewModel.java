package il.ac.hit.viewModel;

import il.ac.hit.model.Category;
import il.ac.hit.model.CostManagerException;
import il.ac.hit.model.CostOrIncome;
import il.ac.hit.model.IModel;
import il.ac.hit.view.IView;

import java.util.ArrayList;
import java.util.Date;

public interface IViewModel {
    public void setView(IView view);
    public void setModel(IModel model);
    public void showMessage(String text); // he did it but i think we don't need it

    public void addCostOrIncome(CostOrIncome item);
    public void deleteCostOrIncome(int id);
    public void addNewCategory(Category item);
    public void deleteCategory(Category item );
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to);
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from,Date to);
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from,Date to);
    public double getTheBalance();
    public ArrayList<CostOrIncome> getAllCostsAndIncomes();
    public ArrayList<Category> getAllCategories();
    public void createDB();
}
