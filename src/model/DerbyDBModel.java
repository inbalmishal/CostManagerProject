package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DerbyDBModel implements IModel{
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:CostDB;create=true";

    public DerbyDBModel() throws CostManagerException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
           throw new CostManagerException(e.getMessage());
        }
    }

    @Override
    public void addCostOrIncome(CostOrIncome item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            //check what is the max id in the table and set this id for the new item
            item.setId(maxId()+1);
            //Insert cost or income to the DB.
            statement.execute("insert into InOutCome(id, description, cost, date, category) values ("+ item.getId()+", '"+
                    item.getDescription()+"', "+item.getCost()+", '"+ item.getDate() +"', '"+item.getCategory().getCategoryName()+"')");
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        } finally
        {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    private int maxId() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int max=0;
        try {
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            //check what is the max id in the table
            rs = statement.executeQuery("select COALESCE(max(id),0) AS MaxID from InOutCome");
            rs.next();
            max = rs.getInt("MaxID");
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        } finally
        {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
        return max;
    }

    @Override
    public void deleteCostOrIncome(CostOrIncome item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="delete from InOutCome where id = "+item.getId();
            //Delete cost or income from the DB.
            statement.execute(sql);
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
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
        //Check if the category already exist.
        if(checkIfCategoryExists(item.getCategoryName())){
            CostManagerException e=new CostManagerException("This category already exists");
            throw e; }
        if(item.getCategoryName().length()==0)
            throw new CostManagerException("category must have at least one letter");
        try {
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="insert into category values ('"+item.getCategoryName()+"')";
            //Insert new category to the table category in the DB.
            statement.execute(sql);
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    private boolean checkIfCategoryExists(String categoryName) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="SELECT * FROM category WHERE name = '"+categoryName+"'";
            //Search if this category name exist in the table category in the DB.
            rs = statement.executeQuery(sql);
            if(rs.next())
                return true;
            else
                return false;
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    @Override
    public void deleteCategory(Category item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        //Check if the category doesn't exist.
        if(!checkIfCategoryExists(item.getCategoryName())){
            CostManagerException e=new CostManagerException("This category doesn't exist");
            throw e;
        }
        try {
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            String sql="delete from category where name = '"+item.getCategoryName()+"'";
            //Delete category from the table category in the DB.
            statement.execute(sql);
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    @Override
    public ArrayList<CostOrIncome> getAllCostsBetweenDates(Date from, Date to) throws CostManagerException {
        if(from.after(to)) {
            CostManagerException e=new CostManagerException("The date 'from' come after the date 'to'");
            throw e;
        }
        ArrayList<CostOrIncome> items = getAllCostsAndIncomesBetweenDates(from, to);
        ArrayList<CostOrIncome> result = new ArrayList<>();
        //Insert to array result every cost from the items array.
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
        if(from.after(to)) {
            CostManagerException e=new CostManagerException("The date 'from' come after the date 'to'");
            throw e;
        }
        ArrayList<CostOrIncome> items = getAllCostsAndIncomesBetweenDates(from, to);
        ArrayList<CostOrIncome> result = new ArrayList<>();
        //Insert to array result every income from the items array.
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
        if(from.after(to)) {
            CostManagerException e=new CostManagerException("The date 'from' come after the date 'to'");
            throw e;
        }
        ArrayList<CostOrIncome> items = getAllCostsAndIncomes();
        ArrayList<CostOrIncome> result = new ArrayList<>();
        //Check every item in items array if the date between from and to, if yes put inside the result array.
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
            //Connect to the DB.
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            String sql = "SELECT cost FROM InOutCome";
            //Get all cost and income from the DB.
            rs = statement.executeQuery(sql);
            //Sum up all cost and income.
            while (rs.next()) {
                balance += rs.getDouble("cost");
            }
        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        } finally {
            //Disconnect from the DB.
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
            //Connect to the DB.
            items = new ArrayList<>();
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            //Get all details from cost and income table in the DB.
            rs = statement.executeQuery("SELECT * FROM InOutCome");
            //Put inside items array all the cost and income.
            while(rs.next())
            {
                //int id, String description, double cost, Date date, Category category
                Category category = new Category(rs.getString("category"));
                CostOrIncome item = new CostOrIncome(rs.getInt("id"), rs.getString("description"), rs.getDouble("cost"), rs.getDate("date"),category);
                items.add(item);
            }

        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
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
            //Connect to the DB.
            categories = new ArrayList<>();
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            //Get all categories from category table in the DB.
            rs = statement.executeQuery("SELECT * FROM Category");
            //Put inside array categories all the categories from DB.
            while(rs.next())
            {
                Category category = new Category(rs.getString("name"));
                categories.add(category);
            }

        } catch (Exception e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
        return categories;
    }

    @Override
    public void createDB() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            try{
                //Create the tables.
                statement.execute("create table category(name VARCHAR(255))");
                statement.execute("create table InOutCome(id int, description VARCHAR(255), cost DOUBLE, date DATE, category VARCHAR(255))");
                statement.execute("insert into Category values ('Clothes')");
                statement.execute("insert into Category values ('Food')");
                statement.execute("insert into Category values ('Maintenance')");
            } catch (SQLException e) {
                //Check if the exception throw because the tables already exist or because something else.
                if (!e.getSQLState().equals("X0Y32")){
                     throw e;
                }
            }
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        }
        finally {
            //Disconnect from the DB.
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }
}