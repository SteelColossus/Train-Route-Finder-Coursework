package train;

public class Duration
{
	private int hours;
	private int minutes;
	
	public Duration(int h, int m)
	{
		this.hours = h;
		this.minutes = m;
	}

	public int getHours()
	{
		return hours;
	}

	public void setHours(int hours)
	{
		this.hours = hours;
	}

	public int getMinutes()
	{
		return minutes;
	}

	public void setMinutes(int minutes)
	{
		this.minutes = minutes;
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
