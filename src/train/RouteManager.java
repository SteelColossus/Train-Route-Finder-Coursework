package train;

import java.util.ArrayList;

/**
 * An object which stores all the current stations and routes.
 * @author Michael
 */
public class RouteManager
{
	private ArrayList<Station> stations;
	private ArrayList<Route> routes;
	
	/**
	 * Default constructor.
	 */
	public RouteManager()
	{
		stations = new ArrayList<Station>();
		routes = new ArrayList<Route>();
		
		defaultSetup();
	}
	
	/**
	 * Performs a default setup for the route manager when it first starts up.
	 */
	private void defaultSetup()
	{		
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
	
	/**
	 * Gets the number of stations stored for the route manager.
	 * @return	the number of stations stored for the route manager
	 */
	public int getNumStations()
	{
		return stations.size();
	}
	
	/**
	 * Get the station with the specified index in the station array.
	 * @param index	the index of the station to get
	 * @return	the station at that index
	 */
	public Station getStation(int index)
	{
		return stations.get(index);
	}
	
	/**
	 * Get the station in the array that is the same as the given station.
	 * @param station	the station to compare against
	 * @return	the station in the array, or null if that station was not found
	 */
	public Station getStation(Station station)
	{
		return stations.stream().filter(x -> x.areSame(station)).findFirst().orElse(null);
	}
	
	/**
	 * Get the station in the array that has the same name as the given name.
	 * @param station	the name to check for
	 * @return	the station in the array, or null if that station was not found
	 */
	public Station getStation(String station)
	{
		return stations.stream().filter(x -> x.getName().equals(station)).findFirst().orElse(null);
	}
	
	/**
	 * Get the route in the array that is the same as the given route.
	 * @param route	the route to compare against
	 * @return	the route in the array, or null if that route was not found
	 */
	public Route getRoute(Route route)
	{
		return routes.stream().filter(x -> x.areSame(route)).findFirst().orElse(null);
	}
	
	/**
	 * Get the route in the array with the given starting and ending stations.
	 * @param start	the starting station
	 * @param end	the ending station
	 * @return	the route in the array, or null if that route was not found
	 */
	public Route getRoute(Station start, Station end)
	{
		return routes.stream().filter(x -> x.getStartStation().areSame(start) && x.getEndStation().areSame(end)).findFirst().orElse(null);
	}
	
	/**
	 * Get the route in the array with the given starting and ending station names.
	 * @param start	the name of the starting station
	 * @param end	the name of the ending station
	 * @return	the route in the array, or null if that route was not found
	 */
	public Route getRoute(String start, String end)
	{		
		return getRoute(getStation(start), getStation(end));
	}
	
	/**
	 * Add the given station to the station array.
	 * @param s	the station to add
	 * @return	the station that was added
	 */
	public Station addStation(Station s)
	{		
		if (!stations.stream().anyMatch(x -> x.areSame(s)))
		{
			stations.add(s);
		}
		
		return s;
	}
	
	/**
	 * Add a new non-main station with the given name into the station array.
	 * @param name	the name of the station
	 * @return	the station that was added
	 */
	public Station addStation(String name)
	{
		return addStation(name, false);
	}
	
	/**
	 * Add a new station with the given name and main value into the station array.
	 * @param name	the name of the station
	 * @param main	whether or not it is a main station
	 * @return	the station that was added
	 */
	public Station addStation(String name, boolean main)
	{
		Station s = new Station(name, main);
		
		return addStation(s);
	}
	
	/**
	 * Add a new route to the route array.
	 * @param r	the route to add
	 * @return	the route that was added
	 */
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
	
	/**
	 * Add a new route to the route array given the properties of the route.
	 * @param start	the starting station
	 * @param end	the ending station
	 * @param totalMins	the total number of minutes this route takes
	 * @param singlePounds	the number of pounds only for a single ticket for this route
	 * @param singlePennies	the number of pennies only for a single ticket for this route
	 * @param returnPounds	the number of pounds only for a return ticket for this route
	 * @param returnPennies	the number of pennies only for a return ticket for this route
	 * @return	the route that was added
	 */
	public Route addRoute(Station start, Station end, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		return addRoute(start, end, new Duration((int)(totalMins / 60), totalMins % 60), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	/**
	 * Add a new route to the route array given the properties of the route.
	 * @param start			the starting station
	 * @param end			the ending station
	 * @param time			the time duration that this route takes
	 * @param singlePrice	the price of a single ticket for this route
	 * @param returnPrice	the price of a return ticket for this route
	 * @return	the route that was added
	 */
	public Route addRoute(Station start, Station end, Duration time, Money singlePrice, Money returnPrice)
	{		
		Route r = new Route(start, end, time, singlePrice, returnPrice);
		
		return addRoute(r);
	}
	
	/**
	 * Add a stop to a route.
	 * @param r	the route to add the stop to
	 * @param s	the station to add on as a stop
	 */
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
	
	/**
	 * Updates the file at a given path to contain the current data contained within RouteManager.
	 * @param path	the path of the file to update
	 * @return	whether the writing of the file was successful
	 */
	public boolean updateFile(String path)
	{
		return FileManager.writeToFile(path, routes);
	}
	
	/**
	 * Updates the RouteManager data to the route data loaded from a file at a given path
	 * @param path	the path of the file to load from
	 * @return	whether the updating of the system was successful
	 */
	public boolean updateSystemFromFile(String path)
	{
		ArrayList<Route> newRoutes = FileManager.readFromFile(path);
		
		if (newRoutes != null)
		{
			stations.clear();
			routes.clear();
			
			for (Route nRoute : newRoutes)
			{
				addRoute(nRoute);
			}
			
			return true;
		}
		
		return false;
	}
}
