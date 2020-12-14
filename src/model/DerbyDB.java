package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DerbyDB  implements IModel{
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:CostDB;create=true";

    @Override
    public void addCostOrIncome(CostOrIncome item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            statement.execute("insert into InOutCome(id, description, cost, date, category) values ("+ item.getId()+", '"+
                    item.getDescription()+"', "+item.getCost()+", '"+ item.getDate() +"', '"+item.getCategory().getCategoryName()+"')");
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    @Override
    public void deleteCostOrIncome(CostOrIncome item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="delete from InOutCome where id = "+item.getId();
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    @Override
    public  void addNewCategory(Category item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        if(checkIfCategoryExists(item.getCategoryName())){
            CostManagerException e=new CostManagerException("This category already exists");
            throw e;}
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="insert into category values ('"+item.getCategoryName()+"')";
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    private boolean checkIfCategoryExists(String categoryName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="SELECT * FROM category WHERE name = '"+categoryName+"'";
            rs = statement.executeQuery(sql);
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
        return true;

    }

    @Override
    public void deleteCategory(Category item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="delete from category where name = '"+item.getCategoryName()+"'";
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to) throws CostManagerException {
        ArrayList<CostOrIncome> items = getAllCostsAndIncomesBetweenDates(from, to);
        ArrayList<CostOrIncome> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CostOrIncome item = items.get(i);
            if (item.getCost()<0) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public ArrayList<CostOrIncome> getAllIncomesBetweenDates(Date from, Date to) throws CostManagerException {
        ArrayList<CostOrIncome> items = getAllCostsAndIncomesBetweenDates(from, to);
        ArrayList<CostOrIncome> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CostOrIncome item = items.get(i);
            if (item.getCost()>=0) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsAndIncomesBetweenDates(Date from, Date to) throws CostManagerException {
        ArrayList<CostOrIncome> items = getAllCostsAndIncomes();
        ArrayList<CostOrIncome> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CostOrIncome item = items.get(i);
            if (item.getDate().compareTo(from)>=0 && item.getDate().compareTo(to)<=0) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public double getTheBalance() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        double balance = 0;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            String sql = "SELECT cost FROM InOutCome";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                balance += rs.getDouble("cost");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
        return balance;
    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsAndIncomes() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<CostOrIncome> items = null;

        try {
            items = new ArrayList<>();
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            rs = statement.executeQuery(
                    "SELECT * FROM InOutCome");
            while(rs.next())
            {
                //int id, String description, double cost, Date date, Category category
                Category category = new Category(rs.getString("category"));
                CostOrIncome item = new CostOrIncome(rs.getInt("id"), rs.getString("description"), rs.getDouble("cost"), rs.getDate("date"),category);
                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
        return items;
    }

    @Override
    public ArrayList<Category> getAllCategories() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Category> categories = null;

        try {
            categories = new ArrayList<>();
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT * FROM Category");

            while(rs.next())
            {
                Category category = new Category(rs.getString("name"));
                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
        return categories;
    }
}