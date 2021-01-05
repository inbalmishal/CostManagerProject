package il.ac.hit.view;

/**
 * This enum represent the currency type of the inserted money.
 */
public enum Currency {
    ILS, USD, EUR;

    private double exchangeRate;

    /**
     * Convert the money to shekels according to the exchange rate.
     * @param sum The money value that we want to convert to ILS.
     * @return The value of sum in ILS.
     */
    public double convertToShekels(double sum){
        return sum*getExchangeRate();
    }

    /**
     * Get the exchange rate of this currency.
     * @return The value of this currency exchange rate.
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Set the exchange rate of this currency.
     * @param exchangeRate Represent the value of this currency exchange rate.
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
