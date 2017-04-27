package train;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager
{
	private File saveFile;
	
	public FileManager()
	{
		setup();
	}
	
	private void setup()
	{
		saveFile = new File("routes.trm");
	}
	
	public boolean fileExists()
	{
		return saveFile.isFile();
	}
	
	public boolean writeToFile(ArrayList<Route> routes)
	{
		try
		{
			FileWriter fw = new FileWriter(saveFile);
			
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
	
	public List<String> readFromFile()
	{		
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(saveFile.getAbsolutePath()));
			
			return lines;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
