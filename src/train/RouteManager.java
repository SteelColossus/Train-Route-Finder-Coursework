package train;

import java.util.ArrayList;
import java.util.List;

public class RouteManager
{
	private ArrayList<Station> stations;
	private ArrayList<Route> routes;
	
	public RouteManager()
	{
		setup();
	}
	
	private void setup()
	{
		stations = new ArrayList<Station>();
		routes = new ArrayList<Route>();
		
		Station leicester = new Station("Leicester", true);
		Station loughborough = new Station("Loughborough", true);
		Station nottingham = new Station("Nottingham", true);
		Station derby = new Station("Derby", true);
		Station york = new Station("York", true);
		
		addRoute(leicester, loughborough, 10, 2, 50, 4, 0);
		addRoute(leicester, nottingham, 30, 3, 50, 6, 20);
		addRoute(leicester, derby, 48, 3, 70, 7, 0);
		addRoute(leicester, york, 65, 23, 50, 25, 0);
		addRoute(loughborough, leicester, 10, 2, 50, 4, 0);
		addRoute(loughborough, nottingham, 15, 1, 50, 2, 50);
		addRoute(loughborough, derby, 23, 1, 25, 2, 50);
		addRoute(loughborough, york, 60, 11, 50, 20, 0);
		addRoute(nottingham, leicester, 30, 3, 50, 6, 20);
		addRoute(nottingham, loughborough, 15, 1, 50, 2, 50);
		addRoute(nottingham, derby, 12, 2, 50, 3, 0);
		addRoute(nottingham, york, 40, 11, 50, 16, 0);
		addRoute(derby, leicester, 48, 3, 70, 7, 0);
		addRoute(derby, loughborough, 25, 2, 0, 2, 50);
		addRoute(derby, nottingham, 10, 1, 50, 3, 0);
		addRoute(derby, york, 85, 7, 20, 16, 0);
		addRoute(york, leicester, 70, 12, 20, 25, 0);
		addRoute(york, loughborough, 60, 12, 0, 20, 0);
		addRoute(york, nottingham, 40, 8, 20, 16, 0);
		addRoute(york, derby, 75, 11, 20, 16, 0);
	}
	
	public ArrayList<Station> getAllStations()
	{
		return stations;
	}
	
	public Station getStation(Station station)
	{
		return stations.stream().filter(x -> x.areSame(station)).findFirst().orElse(null);
	}
	
	public Station getStation(String station)
	{
		return stations.stream().filter(x -> x.getName().equals(station)).findFirst().orElse(null);
	}
	
	public Route getRoute(Route route)
	{
		return routes.stream().filter(x -> x.areSame(route)).findFirst().orElse(null);
	}
	
	public Route getRoute(Station start, Station end)
	{
		return routes.stream().filter(x -> x.getStartStation().areSame(start) && x.getEndStation().areSame(end)).findFirst().orElse(null);
	}
	
	public Route getRoute(String start, String end)
	{		
		return getRoute(getStation(start), getStation(end));
	}
	
	public Station addStation(Station s)
	{		
		if (!stations.stream().anyMatch(x -> x.areSame(s)))
		{
			stations.add(s);
		}
		
		return s;
	}
	
	public Station addStation(String name)
	{
		return addStation(name, false);
	}
	
	public Station addStation(String name, boolean main)
	{
		Station s = new Station(name, main);
		
		return addStation(s);
	}
	
	public Route addRoute(Route r)
	{
		for (int i = 0; i < r.getNumStations(); i++)
		{
			addStation(r.getStation(i));
		}
		
		if (!routes.stream().anyMatch(x -> x.areSame(r)))
		{
			routes.add(r);
		}
		
		return r;
	}
	
	public Route addRoute(Station start, Station end, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		return addRoute(start, end, new Duration((int)(totalMins / 60), totalMins % 60), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	public Route addRoute(Station start, Station end, Duration time, Money singlePrice, Money returnPrice)
	{		
		Route r = new Route(start, end, time, singlePrice, returnPrice);
		
		return addRoute(r);
	}
	
	public void addRouteStop(Route r, Station s)
	{
		addStation(s);
		
		Route internalRoute = getRoute(r);
		
		if (internalRoute != null)
		{
			internalRoute.addStop(s);
		}
		else
		{
			r.addStop(s);
			addRoute(r);
		}
	}
	
	public boolean updateFile(String path)
	{
		return FileManager.writeToFile(path, routes);
	}
	
	public boolean updateSystemFromFile(String path)
	{
		List<String> lines = FileManager.readFromFile(path);
		
		if (lines != null)
		{
			stations.clear();
			routes.clear();
			
			for (String routeStr : lines)
			{
				String stationsStr = routeStr.substring(routeStr.indexOf("[") + 1, routeStr.indexOf("]"));
				String[] stationStrArray = stationsStr.split(",");
				Station[] stationArray = new Station[stationStrArray.length];
				
				int snum = 0;
				
				for (String stationStr : stationStrArray)
				{
					boolean isMain = (stationStr.indexOf("|m") != -1);
					
					if (isMain)
					{
						stationArray[snum] = new Station(stationStr.substring(0, stationStr.length() - "|m".length()), true);
					}
					else
					{
						stationArray[snum] = new Station(stationStr, false);
					}
					
					snum++;
				}
				
				int pnum = 0;
				int[] props = new int[5];
				
				for (String propStr : routeStr.substring(stationsStr.length() + "[],".length()).split(","))
				{
					props[pnum] = Integer.parseInt(propStr);		
					pnum++;
				}
				
				Route r = new Route(stationArray, props[0], props[1], props[2], props[3], props[4]);
				
				addRoute(r);
			}
			
			return true;
		}
		
		return false;
	}
}
