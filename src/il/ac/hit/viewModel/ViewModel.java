package il.ac.hit.viewModel;

import il.ac.hit.model.*;
import il.ac.hit.view.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

/**
 * This Class included all of the methods that connect between the model and the view.
 * @author Inbal mishal and Tal levi
 */
public class ViewModel implements IViewModel {
    private IModel model;
    private IView view;
    private ExecutorService pool;

    /**
     * Create pool of threads.
     */
    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGoodMessage(String text) {
        view.showGoodMessage(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBadMessage(String text) {
        view.showBadMessage(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCostOrIncome(CostOrIncome item){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCostOrIncome(item);
                    showGoodMessage("The object has been added");
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }

            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCostOrIncome(int id) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deleteCostOrIncome(id);
                    showGoodMessage("The object has been deleted");
                } catch (CostManagerException e) {
                    showBadMessage(e.getMessage());
                }
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewCategory(Category item){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addNewCategory(item);
                    showGoodMessage("The category was added successfully");
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deleteCategory(category);
                    showGoodMessage("The category has been deleted");
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to) {
        final ArrayList<CostOrIncome>[] item = new ArrayList[1];
        Future<ArrayList<CostOrIncome>> f = pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    item[0] = model.getAllCostsBetweenDates(from,to);
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        }, item[0]);
        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return item[0];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from, Date to)  {
        final ArrayList<CostOrIncome>[] item = new ArrayList[1];
        Future<ArrayList<CostOrIncome>> f = pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    item[0] = model.getAllIncomesBetweenDates(from,to);
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        }, item[0]);
        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return item[0];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from, Date to)  {
        final ArrayList<CostOrIncome>[] item = new ArrayList[1];
        Future<ArrayList<CostOrIncome>> f = pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    item[0] = model.getAllCostsAndIncomesBetweenDates(from,to);
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        }, item[0]);
        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return item[0];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTheBalance()  {
        final double[] balance = new double[1];
        Future<Double> f = pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    balance[0] = model.getTheBalance();
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        }, balance[0]);
        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return balance[0];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<CostOrIncome> getAllCostsAndIncomes(){
    final ArrayList<CostOrIncome>[] item = new ArrayList[1];
    Future<ArrayList<CostOrIncome>> f = pool.submit(new Runnable() {
        @Override
        public void run() {
            try {
                item[0] = model.getAllCostsAndIncomes();
            }catch(CostManagerException e){
                showBadMessage(e.getMessage());
            }
        }
    }, item[0]);
        try {
        f.get();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
        return item[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Category> getAllCategories() {
        final ArrayList<Category>[] categories = new ArrayList[1];
        Future<ArrayList<Category>> f = pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    categories[0] = model.getAllCategories();
                }catch(CostManagerException e){
                    showBadMessage(e.getMessage());
                }
            }
        }, categories[0]);
        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return categories[0];
    }
}
