package train;

import java.util.ArrayList;

public class Route
{	
	private ArrayList<Station> stations;
	private Duration time;
	private Money singlePrice;
	private Money returnPrice;
	
	public Route(Station start, Station end, Duration time, Money singlePrice, Money returnPrice)
	{
		stations = new ArrayList<Station>();
		
		stations.add(start);
		stations.add(end);
		this.time = time;
		this.singlePrice = singlePrice;
		this.returnPrice = returnPrice;
	}
	
	public Route(Station start, Station end, int totalMins, int singlePounds, int singlePennies, int returnPounds, int returnPennies)
	{
		this(start, end, new Duration((int)(totalMins / 60), totalMins % 60), new Money(singlePounds, singlePennies), new Money(returnPounds, returnPennies));
	}
	
	public Station getStation(int index)
	{
		return index >= stations.size() ? null : stations.get(index);
	}
	
	public Station getStartStation()
	{
		return getStation(0);
	}
	
	public Station getEndStation()
	{
		return getStation(stations.size() - 1);
	}
	
	public int getNumStops()
	{
		return stations.size();
	}
	
	public void addStation(Station s, int i)
	{
		stations.add(i, s);
	}
	
	public void addStop(Station s)
	{
		addStation(s, stations.size() - 1);
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
}
