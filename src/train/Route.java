package train;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An object representing a route that contains many stations.
 * @author Michael
 */
public class Route
{	
	private ArrayList<Station> stations;
	private Duration time;
	private Money singlePrice;
	private Money returnPrice;
	
	/**
	 * Constructor.
	 * @param stations		the list of stations to add to this route
	 * @param time			the time duration that this route takes
	 * @param singlePrice	the price of a single ticket for this route
	 * @param returnPrice	the price of a return ticket for this route
	 */
	public Route(Station[] stations, Duration time, Money singlePrice, Money returnPrice)
	{
		this.stations = new ArrayList<Station>(Arrays.asList(stations));
		this.time = time;
		this.singlePrice = singlePrice;
		this.returnPrice = returnPrice;
	}
	
	/**
	 * Constructor.
	 * @param start			the starting station to add to this route
	 * @param end			the ending station to add to this route
	 * @param time			the time duration that this route takes
	 * @param singlePrice	the price of a single ticket for this route
	 * @param returnPrice	the price of a return ticket for this route
	 */
	public Route(Station start, Station end, Duration time, Money singlePrice, Money returnPrice)
	{
		this(new Station[]{start, end}, time, singlePrice, returnPrice);
	}
	
	/**
	 * Constructor.
	 * @param stations		the list of stations to add to this route
	 * @param totalMins		the total number of minutes this route takes
	 * @param singlePounds	the number of pounds only for a single ticket for this route
	 * @param singlePennies	the number of pennies only for a single ticket for this route
	 * @param returnPounds	the number of pounds only for a return ticket for this route
	 * @param returnPennies	the number of pennies only for a return ticket for this route
	 */
	public Route(Station[] stations, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		this(stations, new Duration(totalMins), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	/**
	 * 
	 * @param start			the starting station to add to this route
	 * @param end			the ending station to add to this route
	 * @param totalMins		the total number of minutes this route takes
	 * @param singlePounds	the number of pounds only for a single ticket for this route
	 * @param singlePennies	the number of pennies only for a single ticket for this route
	 * @param returnPounds	the number of pounds only for a return ticket for this route
	 * @param returnPennies	the number of pennies only for a return ticket for this route
	 */
	public Route(Station start, Station end, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		this(start, end, new Duration(totalMins), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	/**
	 * Get the station in this route with a given index.
	 * @param index	the index of the station in the route
	 * @return	the station in the route with the given index
	 */
	public Station getStation(int index)
	{
		return index >= 0 && index < getNumStations() ? stations.get(index) : null;
	}
	
	/**
	 * Get the starting station of this route.
	 * @return	the starting station of this route
	 */
	public Station getStartStation()
	{
		return getStation(0);
	}
	
	/**
	 * Get the ending station of this route.
	 * @return	the ending station of this route
	 */
	public Station getEndStation()
	{
		return getStation(stations.size() - 1);
	}
	
	/**
	 * Get the stop in this route with the given index.
	 * A stop ignores the starting and ending stations.
	 * @param index	the index of the stop in the route
	 * @return	the stop in the route with the given index
	 */
	public Station getStop(int index)
	{
		return index >= 0 && index < getNumStops() ? getStation(index + 1) : null;
	}
	
	/**
	 * Get the number of stations in this route.
	 * @return	the number of stations in this route
	 */
	public int getNumStations()
	{
		return stations.size();
	}
	
	/**
	 * Get the number of stops on this route.
	 * @return	the number of stops on this route
	 */
	public int getNumStops()
	{
		return getNumStations() - 2;
	}
	
	/**
	 * Add a station to this route at the given index.
	 * @param station	the station to add
	 * @param index	the index to add the station add in this route
	 */
	public void addStation(Station station, int index)
	{
		stations.add(index, station);
	}
	
	/**
	 * Add a stop to this route.
	 * @param station	the station to add
	 */
	public void addStop(Station station)
	{
		addStation(station, getNumStops() + 1);
	}
	
	/**
	 * Remove the station at the specified index from this route.
	 * @param index	the index of the station to remove
	 */
	public void removeStation(int index)
	{
		stations.remove(index);
	}
	
	/**
	 * Remove a station from this route.
	 * @param station	the station to remove
	 */
	public void removeStation(Station station)
	{
		stations.remove(station);
	}
	
	/**
	 * Get the duration that this route lasts for.
	 * @return	the duration that this route lasts for
	 */
	public Duration getDuration()
	{
		return time;
	}
	
	/**
	 * Get the price of a single ticket for this route.
	 * @return	the price of a single ticket for this route
	 */
	public Money getSinglePrice()
	{
		return singlePrice;
	}
	
	/**
	 * Get the price of a return ticket for this route.
	 * @return	the price of a return ticket for this route
	 */
	public Money getReturnPrice()
	{
		return returnPrice;
	}
	
	/**
	 * Whether this route is fundamentally the same as another given route.
	 * @param other	the other route to compare to this route
	 * @return	whether the given route is the same as this route
	 */
	public boolean areSame(Route other)
	{
		return (getStartStation().equals(other.getStartStation()) && getEndStation().equals(other.getEndStation()));
	}
}
