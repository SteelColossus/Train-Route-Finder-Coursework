package train;

public class Duration
{
	private int hours;
	private int minutes;
	
	public Duration(int h, int m)
	{
		setDuration(h, m);
	}
	
	public Duration(int tm)
	{
		setDuration(tm);
	}
	
	public void setDuration(int h, int m)
	{
		h += (int)(m / 60);
		m %= 60;
		
		hours = h;
		minutes = m;
	}
	
	public void setDuration(int tm)
	{
		setDuration((int)(tm / 60), tm % 60);
	}

	public int getHours()
	{
		return hours;
	}

	public void setHours(int h)
	{
		hours = h;
	}

	public int getMinutes()
	{
		return minutes;
	}

	public void setMinutes(int m)
	{
		minutes = m;
	}
	
	public int getTotalMinutes()
	{
		return (hours * 60) + minutes;
	}
	
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
