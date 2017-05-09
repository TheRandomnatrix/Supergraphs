import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;

public class FileWriter
	{
	public static void writeLines(ArrayList<String> linelist, String filename) //prints the network for all nodes in a specified list to a formatted file
		{
		try
			{
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			for(int i=0; i < linelist.size(); i++)
				{
				writer.println(linelist.get(i));
				}
			writer.close();
			} catch (IOException e) 
				{}
		}	
	}