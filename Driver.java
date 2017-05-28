/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import java.io.PrintWriter;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.*;
/*import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Object;
import java.util.Random;
*/
public class Driver
{
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
	{ 
	
	//Data file variables
		
	String datafilename = "results.txt";
	String textfilename = "CiaphasCaneEdited.txt";
	String outputfilename = "NodeDataOutput.txt";
	Supergraph supergraph = new Supergraph();
	Graph graph = new Graph("graphythegraph");
	supergraph.addGraph(graph);

		/*(try
	{
	graph.graphFromFile(filename);
	}
	catch (FileNotFoundException e)
	{}*/
	
	
	/*
	Scanner sc = new Scanner(System.in);
	String input = sc.nextLine();
	
	while(!input.equals("/exit"))
	{
	supergraph.runCommand(input,graph);
	//System.out.println(input);
	input = sc.nextLine();
	} */

	
	supergraph.runCommand("~run "+"Scripts\\DatabaseCommands10.txt",graph,false); //
	
	/*supergraph.runCommand("/fromfile "+datafilename,graph);
	//supergraph.runCommand("/pr-n",graph);
	Node start = graph.getNode("luggage");
	Node end = graph.getNode("emperors");
	System.out.println("Start: "+start.getName() + " End: " + end.getName());
	System.out.println(PathFinder.getPath(start,end,graph.getNodeArrayList()));  */
	}
}
