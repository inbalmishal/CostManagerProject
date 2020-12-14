package model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Program {
    public static void main(String arg[]) throws CostManagerException {
        DerbyDB db = new DerbyDB();
        Date date = new Date(100,4,12);
        Date date2 = new Date(130,8,12);
        Category category = new Category("food");
        CostOrIncome item = new CostOrIncome(5, "sport", 345, date, category);
        CreateDB cdb=new CreateDB();


    }
}
