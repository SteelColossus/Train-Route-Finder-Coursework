package train;

public class Money
{
	private int wholePounds;
	private int wholePennies;
	
	public Money(int pounds, int pennies)
	{
		this.wholePounds = pounds;
		this.wholePennies = pennies;
	}

	public int getPounds()
	{
		return wholePounds;
	}

	public void setPounds(int pounds)
	{
		this.wholePounds = pounds;
	}

	public int getPennies()
	{
		return wholePennies;
	}

	public void setPennies(int pennies)
	{
		this.wholePennies = pennies;
	}
	
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
	
	public void applyDiscount(int percent)
	{
		int totalPennies = (wholePounds * 100 + wholePennies);
		totalPennies = (int)Math.ceil(totalPennies * (1 - ((double)percent / 100)));
		wholePounds = (int)(totalPennies / 100);
		wholePennies = totalPennies % 100;
	}
}
