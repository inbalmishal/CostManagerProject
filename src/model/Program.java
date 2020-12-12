package model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Program {
    public static void main(String arg[]) throws CostManagerException {
        DerbyDB db = new DerbyDB();
        Date date = new Date(100,4,12);
        Date date2 = new Date(130,8,12);
        Category category = new Category("sport");
        CostOrIncome item = new CostOrIncome(5, "sport", 345, date, category);

      // db.addNewCategory(category);
        //db.addCostOrIncome(item);
        //db.deleteCategory(category);
        //db.deleteCostOrIncome(item);
        ArrayList<CostOrIncome> categories = db.getAllCostsBetweenDates(date, date2);
        for(CostOrIncome c:categories)
        {
            System.out.println(c.toString());
        }
        //System.out.println(db.getTheBalance());
    }
}
