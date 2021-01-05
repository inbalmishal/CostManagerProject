package il.ac.hit.model;
import java.util.Date;

/**
 * This class represents the object of costs or incomes.
 */
public class CostOrIncome {
    private int id;
    private String description;
    private double cost;
    private Date date;
    private Category category;

    /**
     * This constructor create the cost or income object with all parameters.
     * @param id The id of the object.
     * @param description The description of the object.
     * @param cost The cost of the object.
     * @param date The date when the expenditure or income was made.
     * @param category The category of the object.
     */
    public CostOrIncome(int id, String description, double cost, Date date, Category category) {
        setId(id);
        setDescription(description);
        setCost(cost);
        setDate(date);
        setCategory(category);
    }

    /**
     * This constructor create the cost or income object without parameter id because the id will be determined to
     * the max id in the table when the object will be added to the table in the DB.
     * @param description The description of the object.
     * @param cost The cost of the object.
     * @param date The date when the expenditure or income was made.
     * @param category The category of the object.
     */
    public CostOrIncome(String description, double cost, Date date, Category category) {
        setId(0);
        setDescription(description);
        setCost(cost);
        setDate(date);
        setCategory(category);
    }

    /**
     * Get the id value of the object.
     * @return The id value of the object.
     */
    public int getId() {
        return id;
    }
    /**
     * Set the id value of the object.
     * @param id The id of the object.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Get the description of the object.
     * @return The description of the object.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set the description of the object.
     * @param description The description of the object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the cost of the object.
     * @return The cost of the object.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Set the cost of the object.
     * @param cost The cost of the object.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Get the date of the object.
     * @return The date when the object was made.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date of the object.
     * @param date The date when the object was made.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the category of the object.
     * @return Category of the object.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set category of the object.
     * @param category The category of the object.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Create a string with all details of the object.
     * @return String with all details of the object.
     */
    @Override
    public String toString() {
        return "CostOrIncome{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", category=" + category.getCategoryName() +
                '}';
    }
}
