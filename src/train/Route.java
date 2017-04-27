package train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Route
{	
	private ArrayList<Station> stations;
	private Duration time;
	private Money singlePrice;
	private Money returnPrice;
	
	public Route(Station[] stations, Duration time, Money singlePrice, Money returnPrice)
	{
		this.stations = new ArrayList<Station>(Arrays.asList(stations));
		this.time = time;
		this.singlePrice = singlePrice;
		this.returnPrice = returnPrice;
	}
	
	public Route(Station start, Station end, Duration time, Money singlePrice, Money returnPrice)
	{
		this(new Station[]{start, end}, time, singlePrice, returnPrice);
	}
	
	public Route(Station[] stations, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		this(stations, new Duration(totalMins), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	public Route(Station start, Station end, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		this(start, end, new Duration(totalMins), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	public Station getStation(int index)
	{
		return index >= getNumStations() ? null : stations.get(index);
	}
	
	public Station getStartStation()
	{
		return getStation(0);
	}
	
	public Station getEndStation()
	{
		return getStation(stations.size() - 1);
	}
	
	public Station getStop(int index)
	{
		return index >= getNumStops() ? null : stations.stream().filter(x -> !x.isMain()).collect(Collectors.toList()).get(index);
	}
	
	public int getNumStations()
	{
		return stations.size();
	}
	
	public int getNumStops()
	{
		return (int) stations.stream().filter(x -> !x.isMain()).count();
	}
	
	public void addStation(Station s, int i)
	{
		stations.add(i, s);
	}
	
	public void addStop(Station s)
	{
		addStation(s, stations.size() - 1);
	}
	
	public void removeStation(Station s)
	{
		stations.remove(s);
	}
	
	public Duration getDuration()
	{
		return time;
	}
	
	public Money getSinglePrice()
	{
		return singlePrice;
	}
	
	public Money getReturnPrice()
	{
		return returnPrice;
	}
	
	public boolean areSame(Route other)
	{
		return (getStartStation().equals(other.getStartStation()) && getEndStation().equals(other.getEndStation()));
	}
}
