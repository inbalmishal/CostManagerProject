package il.ac.hit.model;
import java.sql.*;

public class CreateDBModel {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:CostDB;create=true";

    public static void main(String[] arg) { //right now there is no table
         //createTableCategory();
        // createTableInOutCome();
        // setAndCheckInOutCome();
        //setAndCheckCategory();
       // close("Category");
       // close("InOutCome");
         checkCategory();
        checkInOutCome();
    }

    public static void close(String TableName){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            statement.execute("DROP TABLE "+TableName);

            System.out.println("Table dropped");
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    public static void createTableCategory() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            statement.execute("create table category(name VARCHAR(255))");
            System.out.println("Table created");
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    public static void createTableInOutCome(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            statement.execute("create table InOutCome(id int, description VARCHAR(255), cost DOUBLE, date DATE, category VARCHAR(255))");

            System.out.println("Table created");
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    public static void setAndCheckCategory(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            statement.execute("insert into Category values ('clothes')");
            statement.execute("insert into Category values ('food')");

            rs = statement.executeQuery(
                    "SELECT name FROM Category");
            while(rs.next())
            {
                System.out.println("Category="+rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    public static void setAndCheckInOutCome(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            statement.execute("insert into InOutCome values (1, 'pink shirt from adika', -20, '1993-09-01' , 'clothes')");

            rs = statement.executeQuery(
                    "SELECT * FROM InOutCome");
            while(rs.next())
            {
                System.out.println("id= "+rs.getInt("id")+
                        ", description= "+rs.getString("description")+
                        ", cost= "+rs.getDouble("cost")+
                        ", date= "+rs.getDate("date")+
                         ", category= "+rs.getString("category"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    public static void checkCategory(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            rs = statement.executeQuery(
                    "SELECT name FROM Category");
            while(rs.next())
            {
                System.out.println("Category="+rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    public static void checkInOutCome(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();

            rs = statement.executeQuery(
                    "SELECT * FROM InOutCome");
            while(rs.next())
            {
                System.out.println("id= "+rs.getInt("id")+
                        ", description= "+rs.getString("description")+
                        ", cost= "+rs.getDouble("cost")+
                        ", date= "+rs.getDate("date")+
                        ", category= "+rs.getString("category"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }
}

