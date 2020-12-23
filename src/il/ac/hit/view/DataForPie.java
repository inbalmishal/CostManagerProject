package il.ac.hit.view;

public class DataForPie {
    private String name;
    private double count = 0;

    public DataForPie(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
