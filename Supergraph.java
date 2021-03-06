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
	private HashMap<String,ArrayList<String>> SavedQueryResults; //holds lists of names of nodes for loading/unloading from files
	private HashMap<String,ArrayList<String>> SavedComputationResults; //holds lists of names of nodes for loading/unloading from files
	private String lastresult;
	private static final String ComputedSetTag = "##";
	//private ArrayList<Graph> queryResults;
	Supergraph() //constructor
		{
		NametoGraph = new HashMap<String,Graph>();
		SavedQueryResults = new HashMap<String,ArrayList<String>>();
		SavedComputationResults = new HashMap<String,ArrayList<String>>();
		lastresult = "LASTRESULT";
		SavedQueryResults.put(lastresult, new ArrayList<String>());
		SavedComputationResults.put(lastresult, new ArrayList<String>());
		}
		
	public void addGraph(Graph graph)
		{
		NametoGraph.put(graph.getName(),graph);
		}
		
	private String filterQuerySets(String rawquery) //returns string replacing keywords with their actual values from
		{
		String q = rawquery;
		
		String[] dataList = this.SavedComputationResults.keySet().toArray(new String[0]); //returns list of variables in the node
		for(int i = 0; i < dataList.length; i++) //iterate through query, replacing all variables with their actual value if it exists
			{
			ArrayList<String> set = this.SavedComputationResults.get(dataList[i]);
			String str = "";
			for(int k = 0; k < set.size(); k++) //iterate through query, replacing all variables with their actual value if it exists
				{
				str += set.get(k);
				if(k != (set.size()-1))
					{
					str += " ";
					}
				}
			q = q.replaceAll(ComputedSetTag+dataList[i],str);
			}
		return q;
		}
		
	public static String filterInput(String input)
		{
		String s = input;
		s = input.replace("~pr-r","~printresults");
		s = s.replace("~pr-n","~printnetwork");
		s = s.replace("~pr-ac","~printallconnections");
		s = s.replace("~c-c","~clearconnections");
		s = s.replace("~cu-nd","~computenodedata");
		s = s.replace("~cu-ds","~computedataset");
	
		s = s.replace("~a-nd","~addnodedata");
		s = s.replace("~a-n","~addnode");
		s = s.replace("~a-cgr","~addconnectiongroupreversed");
		s = s.replace("~a-cg","~addconnectiongroup");
		s = s.replace("~a-c","~addconnection");
		s = s.replace("~a-t","~addtraffic");
		s = s.replace("~r-nd","~removenodedata");
		s = s.replace("~r-n","~removenodes");
		s = s.replace("~ff","~fromfile");
		s = s.replace("~ftf","~fromtextfile");
		s = s.replace("~g-an","~getallnodes");
		s = s.replace("~q-n","~nodequery");
		s = s.replace("~q-c","~connectionquery");
		s = s.replace("~q-p","~pathquery");
		s = s.replace("~run","~runfromfile");
		s = s.replace("~l-r","~loadresults"); 
		s = s.replace("~l-sff","~loaddatasetfromfile");
		s = s.replace("~l-rff","~loadresultsfromfile");
		s = s.replace("~pr-ds","~printdatasets");
		s = s.replace("~ev-ds","~evaluatedataset");
		s = s.replace("~s-ds","~savedataset");
		s = s.replace("~w-ds","~writedataset");
		s = s.replace("~s-r","~saveresults");
		s = s.replace("~w-g","~writegraph");
		s = s.replace("~OMGPLSTOHALP","~help");
		s = s.replace("~w-rf","~writeresultsformatted");
		s = s.replace("~w-r","~writeresults");
		s = s.replace("~j","~join");
		s = s.replace("~ui","~getuserinput");
		s = s.replace("~time","~gettimestamp");
		s = s.replace("~a-dg","~adddatagroup");
		s = s.replace("~i-dg","~inheritdatagroup");
		//s = s.replace("/pr","/printresults");	
		return s;
		}
	
	public void runCommandList(String input, Graph graph) throws FileNotFoundException
		{
		if (input.length() > 0)
			{
			if (input.charAt(0) == '~')
				{
				String[] commands= input.split("~");
				boolean chained = false;
				for(int i = 0; i < commands.length; i++)
					{
					runCommand("~"+commands[i],graph,chained);
					chained = true;
					}
				}
			}
		}
		
	public boolean runCommand(String input, Graph graph,boolean chained) throws FileNotFoundException
		{
		String commandinput = filterInput(input);
		String[] command = commandinput.split(" ");
		//System.out.println("command: "+command[0]);
		ArrayList<Node> lastqueryresults = graph.convertNamesToNodes(SavedQueryResults.get(lastresult));
			
		if(command.length == 0)
			return false;
		
		if (command[0].equals("~clearconnections"))	//clears all connections from this graph
			{
			System.out.println("Clearing connections...");
			graph.clearGraphConnections();
			return true;
			}
		if (command[0].equals("~clear"))	//clears all connections from this graph
			{
			System.out.println("Clearing all nodes and connections...");
			graph.clearGraph();
			return true;
			}
		if (command[0].equals("~printnetwork")) //print the entire network
			{
			System.out.println("Printing network...");
			graph.printNetwork();
			return true;
			}
		if (command[0].equals("~printallconnections")) //print the connections for all nodes
			{
			System.out.println("Printing network...");
			graph.printNetworkConnections();
			return true;
			}
		if (command[0].equals("~addnode"))	//Add a new node to the graph
			{
			for(int i = 1; i < command.length; i++)
				{
				String Name1 = 	command[i];
				System.out.println("Adding "+Name1+"...");
				Node newnode = new Node(Name1,graph);
				graph.addNode(Name1,newnode);
				}
			return true;
			}
		if (command[0].equals("~removenode"))	//Add a new node to the graph
			{
			if(command.length > 1)
				{
				String Name1 = 	command[1];
				System.out.println("Removing "+Name1+"...");
				graph.removeNode(Name1);
				return true;
				}
			}
		if (command[0].equals("~removenodes"))	//Add a new node to the graph
			{
			graph.removeNodes(lastqueryresults);//graph.getLastQueryResults()
			return true;
			}
				
		try	{
			if (command[0].equals("~fromfile"))	//Create graph from file
				{
				if(command.length > 1)
					{
					String filename = 	commandinput.substring(10);
					//String filename = command[1];
					System.out.println("Creating from file: "+filename+"...");
					graph.graphFromFile(filename);
					return true;
					}
				}
			}
		catch (FileNotFoundException e)
		{}
		
		try	{
			if (command[0].equals("~fromtextfile"))	//Create graph from file
				{
				if(command.length > 1)
					{
					String str =  command[0]+" ";
					String filename = commandinput.substring(str.length());
					System.out.println("Creating from text file: "+filename+"...");
					graph.graphFromTextFile(filename);
					return true;
					}
				}
			}
		catch (FileNotFoundException e)
		{}
		
		if (command[0].equals("/~addtraffic")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length > 4)
				{
				String Name1 = 	command[1];
				String Name2 = 	command[2];
				int traffic = 			Integer.parseInt(command[3]);		//get traffic value
				String verb = 		command[4];
				System.out.println("Adding traffic: "+Name1+"~> "+Name2+" traffic: "+traffic+" verb: "+verb);
				Node newNode = graph.getNode(Name1);
				if(newNode != null)
					{
					newNode.addTrafficConnection(Name2,traffic,verb);	//add traffic from node 8 to node 2 by 10
					return true;
					}
				}
			}
		
		if (command[0].equals("~adddatagroup")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length == 2)
				{
				String Name1 = 	command[1];
				System.out.println("Adding group data...");
				Node groupnode = graph.getNode(Name1);
				if(groupnode != null)
					{
					graph.addDataGroup(lastqueryresults,groupnode);
					return true;
					}
				}
			}
		if (command[0].equals("~removedatagroup")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length == 2)
				{
				String Name1 = 	command[1];
				System.out.println("Adding group data...");
				Node groupnode = graph.getNode(Name1);
				if(groupnode != null)
					{
					graph.removeDataGroup(lastqueryresults,groupnode);
					return true;
					}
				}
			}
		if (command[0].equals("~inheritdatagroup")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length == 1)
				{
				System.out.println("Inheriting data:...");
				graph.inheritDataGroup(lastqueryresults);
				return true;
				}
			}
		
		if (command[0].equals("~addconnectiongroup")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length == 4)
				{
				String Name1 = 	command[1];
				int traffic = 			Integer.parseInt(command[2]);		//get traffic value
				String verb = 		command[3];
				System.out.println("Adding group traffic...");
				Node newNode = graph.getNode(Name1);
				graph.addConnectionGroup(lastqueryresults,newNode,traffic,verb,true);
				return true;
				}
			}
		if (command[0].equals("~addconnectiongroupreversed")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length == 4)
				{
				String Name1 = 	command[1];
				int traffic = 			Integer.parseInt(command[2]);		//get traffic value
				String verb = 		command[3];
				System.out.println("Adding group traffic reversed...");
				Node newNode = graph.getNode(Name1);
				graph.addConnectionGroup(lastqueryresults,newNode,traffic,verb,false);
				return true;
				}
			}
		
		if (command[0].equals("~getuserinput")) //Get input from user and store it in data set
			{
			if((command.length == 1) || (command.length == 2)) 
				{
				System.out.println("Getting user input: ");
				Scanner sc = new Scanner(System.in);
				String userinput = sc.nextLine();
				ArrayList<String> inputlist = new ArrayList<String>();
				inputlist.add(userinput);
				if(command.length == 1)
					SavedComputationResults.put(lastresult,inputlist);  //if no data set was specified, put it in the last result
				else
					SavedComputationResults.put(command[1],inputlist); // otherwise put it in specified dataset
				return true;
				}
			}	
			
		if (command[0].equals("~addnodedata")) //adds node data to last query nodes. Overrides data unless otherwise specified
			{
			if(command.length >= 3)
				{
				boolean override = true;	//overrides node data by default
				if((command.length % 2) == 0) 
					{
					if (command[command.length-1].equals("false") || command[command.length-1].equals("f")) //check if override to false
						{
						override = false;
						}
					}
				System.out.println("Adding node data:...");
				for(int i = 2; i < command.length; i+=2)
					{
					String variablename 	= command[i-1];
					String nodedata 		= command[i];
					graph.addNodeData(lastqueryresults,variablename,nodedata,override);
					}
				return true;
				}
			}
				
		if (command[0].equals("~removenodedata")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length == 2)
				{
				String variablename 	= command[1];
				System.out.println("Removing node data:...");
				graph.removeNodeData(lastqueryresults,variablename);
				return true;
				}
			}		
		if (command[0].equals("~pathquery")) //addTraffic Name1 Name2 traffic verb
			{
			if(command.length >= 3)
				{
				String Name1 = 	command[1];
				String Name2 = 	command[2];
				boolean weighted = false;
				if(command.length == 4)
					{
					if (command[3].equals("true") || command[3].equals("t")) //check if override to false
						{
						weighted = true;
						}
					}
				System.out.println("Getting path between: "+Name1+" and "+Name2+"...");
				SavedQueryResults.put(lastresult,graph.getNodeNames(graph.runPathQuery(lastqueryresults,Name1,Name2,weighted)));
				return true;
				}
			}		
		if (command[0].equals("~getallnodes")) //runs a nodequery on the initial graph and saves result to memory.
			{
			System.out.println("Getting all nodes:...");
			SavedQueryResults.put(lastresult,graph.getNodeNames(graph.getNodeArrayList()));
			return true;
			}
			
		
			
		if (command[0].equals("~join")) //runs a nodequery on the initial graph and saves result to memory.
			{
			if(command.length == 4)
				{
				ArrayList<String> leftlist = (ArrayList<String>)SavedQueryResults.get(command[2]).clone();
					//System.out.println(leftlist);
				ArrayList<String> rightlist = (ArrayList<String>)SavedQueryResults.get(command[3]).clone();
					//System.out.println(rightlist);
				if((leftlist != null) && (rightlist != null))
					{
					ArrayList<String> R = SavedQueryResults.get(lastresult);
					R.clear();
					switch (command[1]) 		//get the join type
						{
						case "inner":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(lastresult,Joiner.getInnerJoin(leftlist,rightlist));
							break;
						case "full":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(lastresult,Joiner.getFullJoin(leftlist,rightlist));
							break;
						case "left":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(lastresult,Joiner.getLeftOuterJoin(leftlist,rightlist));
							break;
						case "outer":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(lastresult,Joiner.getOuterJoin(leftlist,rightlist));
							break;
						default:
						} 
					return true;
					}
				}
			if(command.length == 5)
				{
				ArrayList<String> leftlist = (ArrayList<String>)SavedQueryResults.get(command[2]).clone();
					//System.out.println(leftlist);
				ArrayList<String> rightlist = (ArrayList<String>)SavedQueryResults.get(command[3]).clone();
					//System.out.println(rightlist);
				if((leftlist != null) && (rightlist != null))
					{
					ArrayList<String> R = SavedQueryResults.get(lastresult);
					R.clear();
					switch (command[1]) 		//get the join type
						{
						case "inner":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(command[4],Joiner.getInnerJoin(leftlist,rightlist));
							break;
						case "full":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(command[4],Joiner.getFullJoin(leftlist,rightlist));
							break;
						case "left":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(command[4],Joiner.getLeftOuterJoin(leftlist,rightlist));
							break;
						case "outer":
							System.out.println("Combining nodes:...");
							SavedQueryResults.put(command[4],Joiner.getOuterJoin(leftlist,rightlist));
							break;
						default:
						} 
					return true;
					}
				}
			}
		if (command[0].equals("~computenodedata")) //Runs through nodes from last query and computes the value of a given expression, putting the result into each node under specified variable
			{
			if(command.length >= 3)
				{
				String commandlength = command[0] + " " + command[1] + " ";
				String expression = 	commandinput.substring(commandlength.length());
				System.out.println("Setting node data to: "+ command[1] + " = " + expression +":...");
				expression = filterQuerySets(expression); //inject sets into query
				graph.runNodeComputation(lastqueryresults,command[1],expression);
				
				return true;
				}
			}		
		if (command[0].equals("~computedataset")) //Runs through nodes from last query and computes the value of a given expression, and returns the results as a dataset
			{
			if(command.length >= 2)
				{
				String commandlength = command[0] + " ";
				String expression = 	commandinput.substring(commandlength.length());
				expression = filterQuerySets(expression); //inject sets into query
				System.out.println("Computing data set" + " = " + expression +":...");
				//System.out.println(graph.computeDataSet(lastqueryresults,query));
				SavedComputationResults.put(lastresult,graph.computeDataSet(lastqueryresults,expression));
				
				return true;
				}
			}
			
		if (command[0].equals("~evaluatedataset")) //Evaluates an expression, possibly using datasets, then places the result as a dataset
			{
			if(command.length >= 2)
				{
				String commandlength = command[0] + " ";
				String query = 	commandinput.substring(commandlength.length());
				query = filterQuerySets(query); //inject sets into query
				System.out.println("Computing data set" + " = " + query +":...");
				//System.out.println(graph.computeDataSet(lastqueryresults,query));
				String evaluatedstring = Query.parseString(query);
				if(!evaluatedstring.equals("NULLRETURN")) //parse the query looking for a successful return
					{
					ArrayList<String> templist = new ArrayList<String>(); //make a new list so it's formatted for SaveComputationResults
					templist.add(evaluatedstring); //add answer to list
					SavedComputationResults.put(lastresult,templist); //put the list in the results
					return true; //successful command run, return true
					}
				return false; //unsuccessful
				}
			}
			
		if (command[0].equals("~gettimestamp")) //Runs through nodes from last query and computes the value of a given expression, and returns the results as a dataset
			{
			if(command.length == 1)
				{
				System.out.println("Getting timestamp:...");
				long timestamp = new Date().getTime();		//get current time in milliseconds
				ArrayList<String> templist = new ArrayList<String>(); //make a list for new data set
				templist.add(String.valueOf(timestamp)); 		//add the timestamp to the list
				SavedComputationResults.put(lastresult,templist);	//put the timestamp in as a dataset under last result
				return true;
				}
			if(command.length == 2)
				{
				System.out.println("Getting timestamp:...");
				long timestamp = new Date().getTime();		//get current time in milliseconds
				ArrayList<String> templist = new ArrayList<String>(); //make a list for new data set
				templist.add(String.valueOf(timestamp)); 		//add the timestamp to the list
				SavedComputationResults.put(command[1],templist);	//put the timestamp in as a dataset under specified name
				return true;
				}
			return false;
			}	
		if (command[0].equals("~nodequery")) //runs a nodequery on the initial graph and saves result to memory.
			{
			System.out.println("Running node query:...");
			String query = 	commandinput.substring(11);
			query = filterQuerySets(query); //inject sets into query
			SavedQueryResults.put(lastresult,graph.getNodeNames(graph.runNodeQuery(lastqueryresults,query)));
			return true;
			}		
		if (command[0].equals("~connectionquery")) //runs a nodequery on the initial graph and saves result to memory.
			{
			if(command.length >= 2)
				{
				System.out.println("Running connection query:...");
				String commandlength = command[0] + " " + command[1] + " ";
				String query = 	commandinput.substring(commandlength.length());
				query = filterQuerySets(query); //inject sets into query
				SavedQueryResults.put(lastresult,graph.getNodeNames(graph.runConnectionQuery(lastqueryresults,query,command[1])));
				//graph.runConnectionQuery(query);
				return true;
				}
			}		
		if (command[0].equals("~writegraph")) //runs a nodequery on the initial graph and saves result to memory.
			{
			if(command.length > 1)
				{
				System.out.println("Writing to file: "+command[1]+"...");
				graph.writeNetwork(graph.getNodeArrayList(),command[1]);
				return true;
				}
			}
		if (command[0].equals("~writeresultsformatted")) //runs a nodequery on the initial graph and saves result to memory.
			{
			if(command.length > 1)
				{
				System.out.println("Writing to file: "+command[1]+"...");
				graph.writeNetwork(lastqueryresults,command[1]);
				return true;
				}
			}
		if (command[0].equals("~help")) //loads specified results list into lastresult
			{
			if(command.length == 1)
				{
				FileReader.printLines(FileReader.getFileLines("Documentation\\General Help.txt"));
				}
			if(command.length == 2) //if a specific topic is desired
				{
					
				}
			}
			
		if (command[0].equals("~printdatasets")) //prints all sets of data and their contained values
			{
			if(command.length == 1)
				{
				System.out.println("Listing data sets:...");
				String[] dataList = this.SavedComputationResults.keySet().toArray(new String[0]); //returns list of variables in the node
				for(int i = 0; i < dataList.length; i++) //iterate through and print out all data sets
					{
					System.out.println(dataList[i] + ": "+SavedComputationResults.get(dataList[i]));
					}
				}
			if(command.length == 2) //if a specific set/sets should be desired.
				{
				ArrayList<String> set = SavedComputationResults.get(command[1]);
					if(set != null)
					{
					System.out.print(set);
					return true;
					}
				}
			}
		if (command[0].equals("~loaddataset")) //loads specified results list into lastresult
			{
			if(command.length == 2)
				{
				ArrayList<String> nameslist = SavedComputationResults.get(command[1]);
				if(nameslist != null)
					{
					System.out.println("Loading set from " + command[1]  +":...");
					
					SavedComputationResults.put(lastresult,nameslist); //moves list of results into last results
					return true;
					}
				}
			}
		try	{
			if (command[0].equals("~loaddatasetfromfile"))	//Create graph from file
				{
				if(command.length > 2)
					{
					String str = command[0]+" " + command[1] + " ";
					String filename = commandinput.substring(str.length());
					System.out.println("Creating from text file: "+filename+"...");
					ArrayList<String> set = FileReader.getFileLines(filename);
					SavedComputationResults.put(command[1],set); //moves list of results into last results
					return true;
					}
				}
			}
		catch (FileNotFoundException e)
		{return false;}
		
		
		if (command[0].equals("~savedataset")) //saves dataset from lastresult into specified dataset
			{
			if(command.length == 2)
				{
				System.out.println("Saving set to " + command[1]  +":...");
				SavedComputationResults.put(command[1],(ArrayList<String>)this.SavedComputationResults.get(lastresult).clone()); //gets results of last query, converts the nodes to a list of names, then saves it to input name
				return true;
				}
			}	
		if (command[0].equals("~printdataset"))	//prints set
			{
			if(command.length > 2)
				{
				ArrayList<String> set = SavedComputationResults.get(command[1]);
					if(set != null)
					{
					System.out.print(set);
					return true;
					}
				}
			}	
		if (command[0].equals("~writedataset"))	//writes specified set to a file
			{
			if(command.length > 2)
				{
				String str = command[0]+" " + command[1] + " ";
				String filename = commandinput.substring(str.length());
				ArrayList<String> set = SavedComputationResults.get(command[1]);
					if(set != null)
					{
					System.out.println("Writing set to: "+filename+"...");
					FileWriter.writeLines(set,filename);
					SavedComputationResults.put(command[1],set); //moves list of results into last results
					return true;
					}
				}
			}
		if (command[0].equals("~writeresults"))	//writes specified set to a file
			{
			if(command.length > 2)
				{
				String str = command[0]+" " + command[1] + " ";
				String filename = commandinput.substring(str.length());
				ArrayList<String> set = SavedQueryResults.get(command[1]);
					if(set != null)
					{
					System.out.println("Writing results to: "+filename+"...");
					FileWriter.writeLines(set,filename);
					SavedQueryResults.put(command[1],set); //moves list of results into last results
					return true;
					}
				}
			}
		if (command[0].equals("~loadresults")) //loads specified results list into lastresult
			{
			if(command.length == 2)
				{
				ArrayList<String> nameslist = SavedQueryResults.get(command[1]);
				if(nameslist != null)
					{
					System.out.println("Loading results from " + command[1]  +":...");
					
					SavedQueryResults.put(lastresult,nameslist); //moves list of results into last results
					return true;
					}
				}
			}
		try	{
			if (command[0].equals("~loadresultsfromfile"))	//Create graph from file
				{
				if(command.length > 2)
					{
					String str = command[0]+" " + command[1] + " ";
					String filename = commandinput.substring(str.length());
					System.out.println("Creating from text file: "+filename+"...");
					ArrayList<String> set = FileReader.getFileLines(filename);
					SavedQueryResults.put(command[1],set); //moves list of results into last results
					return true;
					}
				}
			}
		catch (FileNotFoundException e)
		{return false;}
		if (command[0].equals("~saveresults")) //runs a nodequery on the initial graph and saves result to memory.
			{
			if(command.length == 2)
				{
				System.out.println("Saving results to " + command[1]  +":...");
				SavedQueryResults.put(command[1],(ArrayList<String>)graph.getNodeNames(lastqueryresults).clone()); //gets results of last query, converts the nodes to a list of names, then saves it to input name
				return true;
				}
			}
		if (command[0].equals("~printresults")) //prints the results of a query
			{
			System.out.println("printing results:...");
			graph.printNetwork(lastqueryresults);
			return true;
			}
		if (command[0].equals("~runfromfile")) //runs a nodequery on the initial graph and saves result to memory.
			{
			if(command.length > 1)
				{
				System.out.println("Running commands from: "+command[1]+"...");
				ArrayList<String> filecommands = FileReader.getFileLines(command[1]);
				for(int i = 0; i < filecommands.size(); i++)
					{
					runCommandList(filecommands.get(i),graph);
					}
				return true;
				}
			}
		return false; //no valid command was entered
		}
	}
