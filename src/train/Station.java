package train;

public class Station
{
	private String name;
	private boolean main;

	public Station(String name)
	{
		this(name, false);
	}
	
	public Station(String name, boolean main)
	{
		this.name = name;
		this.main = main;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}
}
