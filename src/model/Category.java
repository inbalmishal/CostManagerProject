package model;

public class Category {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String categoryName) {
        setCategoryName(categoryName);
    }

    @Override
    public String toString() {
        return categoryName;
    }
}