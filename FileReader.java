import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;

public class FileReader
	{

	public static ArrayList<String> getFileLines(String filename) throws FileNotFoundException
		{
		File file1 = new File(filename);
        Scanner inFile1 = new Scanner(file1);
        
        ArrayList<String> Output = new ArrayList<String>();
       
		//read from data file
        while(inFile1.hasNextLine())
			{
			String line = inFile1.nextLine();
			Output.add(line);
			}
        
        System.out.println("Data file read successful...");
        inFile1.close();
		return Output;
		}	
	}