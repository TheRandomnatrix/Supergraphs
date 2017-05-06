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
public class Supergraph
	{
	private HashMap<String,Graph> NametoGraph; //converts names to graph pointers
	private HashMap<String,Graph> SavedQueryResults; //holds lists of names of nodes for loading/unloading from files
	//private ArrayList<Graph> queryResults;
	Supergraph() //constructor
		{
		NametoGraph = new HashMap<String,Graph>();
		SavedQueryResults = new HashMap<String,ArrayList<String>>(); 
		}
		
	public void addGraph(Graph graph)
		{
		NametoGraph.put(graph.getName(),graph);
		}
	
	public static String filterInput(String input)
		{
		String s = input;
		s = input.replace("/pr-r","/printresults");
		s = s.replace("/pr-n","/printnetwork");
		s = s.replace("/pr-ac","/printallconnections");
		s = s.replace("/c-c","/clearconnections");
		s = s.replace("/c-q","/clearquery");
		s = s.replace("/a-nd","/addnodedata");
		s = s.replace("/a-n","/addnode");
		s = s.replace("/a-cgr","/addconnectiongroupreversed");
		s = s.replace("/a-cg","/addconnectiongroup");
		s = s.replace("/a-c","/addconnection");
		s = s.replace("/a-t","/addtraffic");
		s = s.replace("/r-nd","/removenodedata");
		s = s.replace("/r-n","/removenodes");
		s = s.replace("/ff","/fromfile");
		s = s.replace("/ftf","/fromtextfile");
		s = s.replace("/q-n","/nodequery");
		s = s.replace("/q-c","/connectionquery");
		s = s.replace("/q-p","/pathquery");
		s = s.replace("/run","/runfromfile");
		s = s.replace("/w-g","/writegraph");
		s = s.replace("/w-r","/writeresults");
		//s = s.replace("/pr","/printresults");	
		return s;
		}
		
	public void runCommand(String input, Graph graph) throws FileNotFoundException
		{
		String commandinput = filterInput(input);
		String[] command = commandinput.split(" ");
		System.out.println("command: "+command[0]);
		if(command.length == 0)
			return;
		
		if (command[0].equals("/clearconnections"))	//clears all connections from this graph
				{
				System.out.println("Clearing connections...");
				graph.clearGraphConnections();
				}
		if (command[0].equals("/clear"))	//clears all connections from this graph
				{
				System.out.println("Clearing all nodes and connections...");
				graph.clearGraph();
				}
		if (command[0].equals("/printnetwork")) //print the entire network
				{
				System.out.println("Printing network...");
				graph.printNetwork();
				}
		if (command[0].equals("/printallconnections")) //print the connections for all nodes
				{
				System.out.println("Printing network...");
				graph.printNetworkConnections();
				}
		if (command[0].equals("/addnode"))	//Add a new node to the graph
				{
				for(int i = 1; i < command.length; i++)
					{
					String Name1 = 	command[i];
					System.out.println("Adding "+Name1+"...");
					Node newnode = new Node(Name1,graph);
					graph.addNode(Name1,newnode);
					}
				}
		if (command[0].equals("/removenode"))	//Add a new node to the graph
				{
				if(command.length > 1)
					{
					String Name1 = 	command[1];
					System.out.println("Removing "+Name1+"...");
					graph.removeNode(Name1);
					}
				}
		if (command[0].equals("/removenodes"))	//Add a new node to the graph
				{
				graph.removeNodes(graph.getLastQueryResults());
				}
				
		try	{
			if (command[0].equals("/fromfile"))	//Create graph from file
				{
				if(command.length > 1)
					{
					String filename = command[1];
					System.out.println("Creating from file: "+filename+"...");
					graph.graphFromFile(filename);
					}
				}
			}
		catch (FileNotFoundException e)
		{}
		
		try	{
			if (command[0].equals("/fromtextfile"))	//Create graph from file
				{
				if(command.length > 1)
					{
					String filename = command[1];
					System.out.println("Creating from text file: "+filename+"...");
					graph.graphFromTextFile(filename);
					}
				}
			}
		catch (FileNotFoundException e)
		{}
		
		if (command[0].equals("/addtraffic")) //addTraffic Name1 Name2 traffic verb
				{
				if(command.length > 4)
					{
					String Name1 = 	command[1];
					String Name2 = 	command[2];
					int traffic = 			Integer.parseInt(command[3]);		//get traffic value
					String verb = 		command[4];
					System.out.println("Adding traffic: "+Name1+"--> "+Name2+" traffic: "+traffic+" verb: "+verb);
					Node newNode = graph.getNode(Name1);
					if(newNode != null)
						{
						newNode.addTrafficConnection(Name2,traffic,verb);	//add traffic from node 8 to node 2 by 10
						}
					}
				}
				
		if (command[0].equals("/addconnectiongroup")) //addTraffic Name1 Name2 traffic verb
				{
				if(command.length == 4)
					{
					String Name1 = 	command[1];
					int traffic = 			Integer.parseInt(command[2]);		//get traffic value
					String verb = 		command[3];
					System.out.println("Adding group traffic...");
					Node newNode = graph.getNode(Name1);
					graph.addConnectionGroup(graph.getLastQueryResults(),newNode,traffic,verb,true);
					}
				}
		if (command[0].equals("/addconnectiongroupreversed")) //addTraffic Name1 Name2 traffic verb
				{
				if(command.length == 4)
					{
					String Name1 = 	command[1];
					int traffic = 			Integer.parseInt(command[2]);		//get traffic value
					String verb = 		command[3];
					System.out.println("Adding group traffic reversed...");
					Node newNode = graph.getNode(Name1);
					graph.addConnectionGroup(graph.getLastQueryResults(),newNode,traffic,verb,false);
					}
				}		
		if (command[0].equals("/addnodedata")) //addTraffic Name1 Name2 traffic verb
				{
				if(command.length >= 3)
					{
					String variablename 	= command[1];
					String nodedata 		= command[2];
					System.out.println("Adding node data:...");
					graph.addNodeData(graph.getLastQueryResults(),variablename,nodedata,true);
					}
				}
		if (command[0].equals("/removenodedata")) //addTraffic Name1 Name2 traffic verb
				{
				if(command.length == 2)
					{
					String variablename 	= command[1];
					System.out.println("Removing node data:...");
					graph.removeNodeData(graph.getLastQueryResults(),variablename);
					}
				}		
			if (command[0].equals("/pathquery")) //addTraffic Name1 Name2 traffic verb
				{
				if(command.length == 3)
					{
					String Name1 = 	command[1];
					String Name2 = 	command[2];
					System.out.println("Getting path over query between: "+Name1+" "+Name2+"...");
					graph.runPathQuery(graph.getLastQueryResults(),Name1,Name2);
					}
				}		
		if (command[0].equals("/clearquery")) //Clears the query state of the graph
				{
				System.out.println("clearing results:...");
				graph.resetQueryState();
				}					
		if (command[0].equals("/nodequery")) //runs a nodequery on the initial graph and saves result to memory.
				{
				System.out.println("Running node query:...");
				String query = 	commandinput.substring(11);
				graph.runNodeQuery(query);
				}		
		if (command[0].equals("/connectionquery")) //runs a nodequery on the initial graph and saves result to memory.
				{
				System.out.println("Running connection query:...");
				String query = 	commandinput.substring(17);
				graph.runConnectionQuery(query);
				}		
		if (command[0].equals("/writegraph")) //runs a nodequery on the initial graph and saves result to memory.
				{
				if(command.length > 1)
					{
					System.out.println("Writing to file: "+command[1]+"...");
					graph.writeNetwork(graph.getNodeArrayList(),command[1]);
					}
				}
		if (command[0].equals("/writeresults")) //runs a nodequery on the initial graph and saves result to memory.
				{
				if(command.length > 1)
					{
					graph.writeNetwork(graph.getLastQueryResults(),command[1]);
					}
				}	
		if (command[0].equals("/printresults")) //runs a nodequery on the initial graph and saves result to memory.
				{
				System.out.println("printing results:...");
				graph.printLastQueryResult();
				}
		if (command[0].equals("/runfromfile")) //runs a nodequery on the initial graph and saves result to memory.
				{
				if(command.length > 1)
					{
					System.out.println("Running commands from: "+command[1]+"...");
					ArrayList<String> filecommands = FileReader.getFileLines(command[1]);
					for(int i = 0; i < filecommands.size(); i++)
						{
						runCommand(filecommands.get(i),graph);
						}
					}
				}
		}
	}
