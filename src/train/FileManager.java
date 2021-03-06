package train;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * A helper class which saves and loads to/from files.
 *
 * @author SteelColossus
 */
public final class FileManager
{
    /**
     * Write to a file at the specified path with the given information about
     * routes.
     *
     * @param path the path of the file
     * @param routes the routes information
     * @return whether the write operation was successful
     */
    public static boolean writeToFile(String path, ArrayList<Route> routes)
    {
        try (FileWriter fw = new FileWriter(path))
        {
            for (Route r : routes)
            {
                StringBuilder newLine = new StringBuilder("[");

                for (int si = 0; si < r.getNumStations(); si++)
                {
                    newLine.append(r.getStation(si).getName());
                    if (r.getStation(si).isMain()) newLine.append("|m");
                    if (si != r.getNumStations() - 1) newLine.append(",");
                }

                newLine.append("],").append(r.getDuration().getTotalMinutes()).append(",")
                        .append(r.getSinglePrice().getPounds()).append(",")
                        .append(r.getSinglePrice().getPennies()).append(",")
                        .append(r.getReturnPrice().getPounds()).append(",")
                        .append(r.getReturnPrice().getPennies())
                        .append(System.getProperty("line.separator"));

                fw.write(newLine.toString());
            }

            fw.close();

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Reads the route information from a file at the specified path.
     *
     * @param path the path of the file
     * @return a list of routes that were interpreted from the file
     */
    public static ArrayList<Route> readFromFile(String path)
    {
        try
        {
            ArrayList<Route> routes = new ArrayList<>();

            for (String routeStr : Files.readAllLines(Paths.get(path)))
            {
                int startIndex = routeStr.indexOf("[");
                int finalIndex = routeStr.indexOf("]");

                if (startIndex == -1 || finalIndex == -1 || startIndex >= finalIndex
                    || routeStr.chars().filter(c -> c == ',').count() < 5)
                {
                    return null;
                }

                String stationsStr = routeStr.substring(startIndex + 1, finalIndex);
                String[] stationStrArray = stationsStr.split(",");
                Station[] stationArray = new Station[stationStrArray.length];

                int snum = 0;

                for (String stationStr : stationStrArray)
                {
                    boolean isMain = stationStr.contains("|m");

                    stationArray[snum] = new Station(stationStr.substring(0,
                            stationStr.length() - ((isMain) ? "|m".length() : 0)).trim(), isMain);

                    snum++;
                }

                int pnum = 0;
                int[] props = new int[5];

                for (String propStr : routeStr.substring(stationsStr.length() + "[],".length()).split(","))
                {
                    props[pnum] = Integer.parseInt(propStr);
                    pnum++;
                }

                routes.add(new Route(stationArray, props[0], props[1], props[2], props[3], props[4]));
            }

            return routes;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
