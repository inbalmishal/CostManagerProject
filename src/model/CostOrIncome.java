package model;
import java.util.Date;

public class CostOrIncome {
    private int id;
    private String description;
    private double cost;
    private Date date;
    private Category category;


    public CostOrIncome(int id, String description, double cost, Date date, Category category) {
        setId(id);
        setDescription(description);
        setCost(cost);
        setDate(date);
        setCategory(category);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

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
