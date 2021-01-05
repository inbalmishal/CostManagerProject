package il.ac.hit.model;

/**
 * This class represents the object of category.
 */
public class Category {
    private String categoryName;

    /**
     * Get the name of the category.
     * @return The name of the category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set the name of the category.
     * @param categoryName The name of the category.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * This constructor create the category object.
     * @param categoryName The name of the category.
     */
    public Category(String categoryName) {
        setCategoryName(categoryName);
    }
    /**
     * Representation of the category as a string.
     * @return String with the name of the category.
     */
    @Override
    public String toString() {
        return categoryName;
    }
}