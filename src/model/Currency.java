package model;

public enum Currency {
    ILS,USD,EUR;
    private double exchangeRate;
    public double convertToShekels(double sum){
        return sum*getExchangeRate();
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
