package train;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FileManager
{	
	public static boolean writeToFile(String path, ArrayList<Route> routes)
	{
		try
		{
			FileWriter fw = new FileWriter(path);
			
			for (Route r : routes)
			{
				fw.write("[");
				
				for (int si = 0; si < r.getNumStations(); si++)
				{
					fw.write(r.getStation(si).getName());
					if (r.getStation(si).isMain()) fw.write("|m");
					if (si != r.getNumStations() - 1) fw.write(",");
				}
				
				fw.write("],");
				fw.write(r.getDuration().getTotalMinutes() + "," + r.getSinglePrice().getPounds() + "," + r.getSinglePrice().getPennies() + "," + r.getReturnPrice().getPounds() + "," + r.getReturnPrice().getPennies());
				fw.write("\n");
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
	
	public static List<String> readFromFile(String path)
	{		
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(path));
			
			return lines;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
