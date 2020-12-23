package il.ac.hit.model;

import java.sql.Date;

public class Program {
    public static void main(String arg[]) throws CostManagerException {
        DerbyDBModel db = new DerbyDBModel();
        db.createDB();
        Date date = new Date(100,4,12);
        Date date2 = new Date(130,8,12);
        Category category = new Category("sport");
        CostOrIncome item = new CostOrIncome("sport", -345, date, category);
        db.addCostOrIncome(item);
       // db.addNewCategory(category);
      //  db.deleteCategory(new Category("lalala"));


    }
}
