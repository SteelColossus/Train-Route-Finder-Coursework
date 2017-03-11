package train;

public class Main
{
	public static void main(String[] args)
	{
		RouteManager rm = new RouteManager();
		Menu menu = new Menu(rm);
		menu.start();
	}
}
