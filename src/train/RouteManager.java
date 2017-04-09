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
		routes = new ArrayList<Route>();
		
		this.addRoute(leicester, loughborough, 10, 2, 50, 4, 0);
		this.addRoute(leicester, nottingham, 30, 3, 50, 6, 20);
		this.addRoute(leicester, derby, 48, 3, 70, 7, 0);
		this.addRoute(leicester, york, 65, 23, 50, 25, 0);
		this.addRoute(loughborough, leicester, 10, 2, 50, 4, 0);
		this.addRoute(loughborough, nottingham, 15, 1, 50, 2, 50);
		this.addRoute(loughborough, derby, 23, 1, 25, 2, 50);
		this.addRoute(loughborough, york, 60, 11, 50, 20, 0);
		this.addRoute(nottingham, leicester, 30, 3, 50, 6, 20);
		this.addRoute(nottingham, loughborough, 15, 1, 50, 2, 50);
		this.addRoute(nottingham, derby, 12, 2, 50, 3, 0);
		this.addRoute(nottingham, york, 40, 11, 50, 16, 0);
		this.addRoute(derby, leicester, 48, 3, 70, 7, 0);
		this.addRoute(derby, loughborough, 25, 2, 0, 2, 50);
		this.addRoute(derby, nottingham, 10, 1, 50, 3, 0);
		this.addRoute(derby, york, 85, 7, 20, 16, 0);
		this.addRoute(york, leicester, 70, 12, 20, 25, 0);
		this.addRoute(york, loughborough, 60, 12, 0, 20, 0);
		this.addRoute(york, nottingham, 40, 8, 20, 16, 0);
		this.addRoute(york, derby, 75, 11, 20, 16, 0);
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
	
	public void addStation(String name)
	{
		this.addStation(name, false);
	}
	
	public void addStation(String name, boolean main)
	{
		Station s = new Station(name, main);
		
		this.addStation(s);
	}
	
	public void addStation(Station s)
	{
		if (!stations.contains(s))
		{
			stations.add(s);
		}
	}
	
	public void addRoute(Station start, Station end, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		this.addRoute(start, end, new Duration((int)(totalMins / 60), totalMins % 60), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	public void addRoute(Station start, Station end, Duration time, Money singlePrice, Money returnPrice)
	{		
		Route r = new Route(start, end, time, singlePrice, returnPrice);
		
		this.addRoute(r);
	}
	
	public void addRoute(Route r)
	{
		for (int i = 0; i < r.getNumStations(); i++)
		{
			this.addStation(r.getStation(i));
		}
		
		if (!routes.contains(r))
		{
			routes.add(r);
		}
	}
}
