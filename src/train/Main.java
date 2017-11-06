package train;

/**
 * The main class.
 * 
 * @author SteelColossus
 */
public class Main
{
    /**
     * Default main constructor.
     * 
     * @param args any command line arguments to take
     */
    public static void main(String[] args)
    {
        RouteManager rm = new RouteManager();
        MainMenu menu = new MainMenu(rm);
        menu.show();
    }
}
