package il.ac.hit.view;

/**
 * Stores the pie chart data according to the category name.
 *  @author Inbal mishal and Tal levi
 */
public class DataForPie {
    private String categoryName;
    private double moneySum = 0;

    /**
     * Create DataForPie object.
     * @param categoryName Represent the name of the category that stored.
     */
    public DataForPie(String categoryName) {
        setCategoryName(categoryName);
    }

    /**
     * Get the name of the category that stored.
     * @return the name of the category that stored.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set the name of the category that stored.
     * @param categoryName Represent the name of the category that stored
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get the moneySum parameter.
     * @return the sum of the money that saved or wasted in this category.
     */
    public double getMoneySum() {
        return moneySum;
    }

    /**
     * Set the moneySum parameter.
     * @param moneySum Represent the sum of the money that saved or wasted in this category.
     */
    public void setMoneySum(double moneySum) {
        this.moneySum = moneySum;
    }
}
