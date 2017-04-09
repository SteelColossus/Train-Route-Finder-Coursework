package train;

public class Main
{
	public static void main(String[] args)
	{
		RouteManager rm = new RouteManager();
		MainMenu menu = new MainMenu(rm);
		menu.show();
	}
}
