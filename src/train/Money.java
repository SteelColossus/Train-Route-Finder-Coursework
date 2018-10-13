package train;

/**
 * An object which stores an amount of money in pounds and pennies.
 * 
 * @author SteelColossus
 */
public class Money
{
    private int wholePounds;
    private int wholePennies;

    /**
     * Constructor taking separate pounds and pennies.
     * 
     * @param pounds number of whole pounds
     * @param pennies number of whole pennies
     */
    public Money(int pounds, int pennies)
    {
        wholePounds = pounds;
        wholePennies = pennies;
    }

    /**
     * Gets the number of whole pounds.
     * 
     * @return number of whole pounds
     */
    public int getPounds()
    {
        return wholePounds;
    }

    /**
     * Sets the number of whole pounds.
     * 
     * @param pounds number of whole pounds
     */
    public void setPounds(int pounds)
    {
        wholePounds = pounds;
    }

    /**
     * Gets the number of whole pennies.
     * 
     * @return number of whole pennies
     */
    public int getPennies()
    {
        return wholePennies;
    }

    /**
     * Sets the number of whole pennies.
     * 
     * @param pennies number of whole pennies
     */
    public void setPennies(int pennies)
    {
        wholePennies = pennies;
    }

    /**
     * Formats the money as a string.
     * 
     * @return the money as a formatted string
     */
    public String formatCurrency()
    {
        String moneyStr = "";

        if (wholePounds > 0)
        {
            moneyStr += "£" + Integer.toString(wholePounds) + ".";

            if (wholePennies < 10) moneyStr += "0";

            moneyStr += Integer.toString(wholePennies);
        }
        else if (wholePennies > 0)
        {
            moneyStr += Integer.toString(wholePennies) + ".";
        }

        return moneyStr;
    }

    /**
     * Applies a discount of a given percent to this money object.
     * 
     * @param percent the percentage discount to apply
     */
    public void applyDiscount(int percent)
    {
        int totalPennies = (wholePounds * 100 + wholePennies);
        totalPennies = (int) Math.ceil(totalPennies * (1 - ((double) percent / 100)));
        wholePounds = totalPennies / 100;
        wholePennies = totalPennies % 100;
    }
}
