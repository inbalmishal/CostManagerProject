package il.ac.hit.viewModel;

import il.ac.hit.model.*;
import il.ac.hit.view.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {
    private IModel model;
    private IView view;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void showMessage(String text) { }

    @Override
    public void addCostOrIncome(CostOrIncome item){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCostOrIncome(item);
                   // view.showMassage("the item was added successfully");
                }catch(CostManagerException e){
                   // view.showMassage(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteCostOrIncome(int id) {

    }

    @Override
    public void addNewCategory(Category item){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addNewCategory(item);
                    view.showGoodMassage("the category was added successfully");
                }catch(CostManagerException e){
                    view.showBadMassage(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteCategory(Category item) {

    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to) {
        return null;
    }

    @Override
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from, Date to)  {
        return null;
    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from, Date to)  {
        return null;
    }

    @Override
    public double getTheBalance()  {
        return 0;
    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsAndIncomes()  {
        return null;
    }

    @Override
    public ArrayList<Category> getAllCategories() {
        return null;
    }

    @Override
    public void createDB(){

    }

}
