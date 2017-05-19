package train;

/**
 * An object which stores a length of time.
 * @author Michael
 */
public class Duration
{
	private int hours;
	private int minutes;
	
	/**
	 * Constructor taking separate hours and minutes.
	 * @param h	number of whole hours
	 * @param m	number of whole minutes
	 */
	public Duration(int h, int m)
	{
		setDuration(h, m);
	}
	
	/**
	 * Constructor taking total number of minutes.
	 * @param tm	number of total minutes
	 */
	public Duration(int tm)
	{
		setDuration(tm);
	}
	
	/**
	 * Sets the hour and minutes values of this duration.
	 * @param h	number of whole hours
	 * @param m	number of whole minutes
	 */
	public void setDuration(int h, int m)
	{
		h += (int)(m / 60);
		m %= 60;
		
		hours = h;
		minutes = m;
	}
	
	/**
	 * Sets the hour and minutes values of this duration.
	 * @param tm	total number of minutes
	 */
	public void setDuration(int tm)
	{
		setDuration((int)(tm / 60), tm % 60);
	}

	/**
	 * Gets the number of whole hours.
	 * @return	number of whole hours
	 */
	public int getHours()
	{
		return hours;
	}

	/**
	 * Sets the number of whole hours.
	 * @param number of whole hours
	 */
	public void setHours(int h)
	{
		hours = h;
	}

	/**
	 * Gets the number of whole minutes.
	 * @return	number of whole minutes
	 */
	public int getMinutes()
	{
		return minutes;
	}

	/**
	 * Sets the number of whole minutes.
	 * @param m	number of whole minutes
	 */
	public void setMinutes(int m)
	{
		minutes = m;
	}
	
	/**
	 * Gets the total number of minutes.
	 * @return	the total number of minutes.
	 */
	public int getTotalMinutes()
	{
		return (hours * 60) + minutes;
	}
	
	/**
	 * Formats the duration as a string of words.
	 * @return	the duration as a string of words
	 */
	public String formatAsWords()
	{
		String timeStr = "";
		
		if (hours > 0)
		{
			timeStr += Integer.toString(hours) + " hour";
		}
		
		if (hours > 1) timeStr += "s";
		
		if (hours > 0) timeStr += " ";
		
		if (minutes > 0)
		{
			timeStr += Integer.toString(minutes) + " min";
		}
		
		if (minutes > 1) timeStr += "s";
		
		return timeStr;
	}
}
