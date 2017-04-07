package train;

import java.util.ArrayList;

public class RouteManager
{
	private ArrayList<Station> stations;
	private ArrayList<Route> routes;
	
	public RouteManager()
	{
		this.setup();
	}
	
	private void setup()
	{
		Station leicester = new Station("Leicester", true);
		Station loughborough = new Station("Loughborough", true);
		Station nottingham = new Station("Nottingham", true);
		Station derby = new Station("Derby", true);
		Station york = new Station("York", true);
		
		stations = new ArrayList<Station>();
		
		stations.add(leicester);
		stations.add(loughborough);
		stations.add(nottingham);
		stations.add(derby);
		stations.add(york);
		
		routes = new ArrayList<Route>();
		
		routes.add(new Route(leicester, loughborough, 10, 2, 50, 4, 0));
		routes.add(new Route(leicester, nottingham, 30, 3, 50, 6, 20));
		routes.add(new Route(leicester, derby, 48, 3, 70, 7, 0));
		routes.add(new Route(leicester, york, 65, 23, 50, 25, 0));
		routes.add(new Route(loughborough, leicester, 10, 2, 50, 4, 0));
		routes.add(new Route(loughborough, nottingham, 15, 1, 50, 2, 50));
		routes.add(new Route(loughborough, derby, 23, 1, 25, 2, 50));
		routes.add(new Route(loughborough, york, 60, 11, 50, 20, 0));
		routes.add(new Route(nottingham, leicester, 30, 3, 50, 6, 20));
		routes.add(new Route(nottingham, loughborough, 15, 1, 50, 2, 50));
		routes.add(new Route(nottingham, derby, 12, 2, 50, 3, 0));
		routes.add(new Route(nottingham, york, 40, 11, 50, 16, 0));
		routes.add(new Route(derby, leicester, 48, 3, 70, 7, 0));
		routes.add(new Route(derby, loughborough, 25, 2, 0, 2, 50));
		routes.add(new Route(derby, nottingham, 10, 1, 50, 3, 0));
		routes.add(new Route(derby, york, 85, 7, 20, 16, 0));
		routes.add(new Route(york, leicester, 70, 12, 20, 25, 0));
		routes.add(new Route(york, loughborough, 60, 12, 0, 20, 0));
		routes.add(new Route(york, nottingham, 40, 8, 20, 16, 0));
		routes.add(new Route(york, derby, 75, 11, 20, 16, 0));
	}
	
	public ArrayList<Station> getAllStations()
	{
		return stations;
	}
	
	public Station getStation(String station)
	{
		return stations.stream().filter(x -> x.getName() == station).findFirst().orElse(null);
	}
	
	public Route getRoute(Station start, Station end)
	{
		return routes.stream().filter(x -> x.getStartStation() == start && x.getEndStation() == end).findFirst().orElse(null);
	}
	
	public Route getRoute(String start, String end)
	{		
		return getRoute(getStation(start), getStation(end));
	}
}
