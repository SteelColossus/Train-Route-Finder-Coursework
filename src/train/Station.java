package train;

/**
 * An object representing a station.
 * 
 * @author Michael
 */
public class Station
{
    private String name;
    private boolean main;

    /**
     * Constructor for a non-main station.
     * 
     * @param name the name of the station
     */
    public Station(String name)
    {
        this(name, false);
    }

    /**
     * Constructor.
     * 
     * @param name the name of the station
     * @param main whether the station is a main station
     */
    public Station(String name, boolean main)
    {
        this.name = name;
        this.main = main;
    }

    /**
     * Get the name of this station.
     * 
     * @return the name of this station
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the name of this station.
     * 
     * @param name the new name to be given to this station
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns whether this station is a main station. A main station is one of
     * the original given stations.
     * 
     * @return whether this station is a main station
     */
    public boolean isMain()
    {
        return main;
    }

    /**
     * Sets whether this station is a main station.
     * 
     * @param main whether this station is to be set as a main station
     */
    public void setMain(boolean main)
    {
        this.main = main;
    }

    /**
     * Whether this station is fundamentally the same as another given station.
     * 
     * @param other the other station to compare to this station
     * @return whether the given station is the same as this station
     */
    public boolean areSame(Station other)
    {
        return name.equals(other.getName());
    }
}
