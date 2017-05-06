import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
//import java.util.ArrayList;
//import java.util.List;

public class Graph
{
	private HashMap<String,Node> NametoNode;
	private String Name = "";
	private boolean clearedQuery; //false if the query should continue off of an old query result
	private ArrayList<Node> queryResults;
	
	Graph(String name) //constructor
		{
		//NodeList = new ArrayList<Node>(); //list of neighbors
		NametoNode = new HashMap<String,Node>();
		this.Name = name;
			
		this.clearedQuery = true; //false if the query should continue off of an old query result
		ArrayList<Node> queryResults =  new ArrayList<Node>();
		}
	
		
	public String getName()
		{
		return this.Name;
		}
		
	public void resetQueryState()
		{
		queryResults.clear();
		this.clearedQuery = true;	
		}		
		
	public ArrayList<Node> getLastQueryResults()
		{
		return this.queryResults;
		}		
		
	public Node getNode(String name)
		{
		return NametoNode.get(name);
		}			
	
	public ArrayList<String> getNodeNames(ArrayList<Node> nodeslist) //return all names for a specified list of nodes
		{
		ArrayList<String> nameslist = new ArrayList<String>();
		for(int i=0; i < nodeslist.size(); i++)
			{
			Node output = nodeslist.get(i);
			if(output != null)
				nameslist.add(output.getName());
			}
		return nameslist;
		}
		
	public ArrayList<Node> convertNamesToNodes(ArrayList<String> nameslist) //covnerts a list of names to a list of nodes, pruning the ones that don't exist in the graph
		{
		ArrayList<Node> nodeslist = new ArrayList<Node>(); //return list of nodes
		for(int i=0; i < nameslist.size(); i++)
			{
			Node node = this.NametoNode(nameslist.get(i)); //convert the name to a node for each item in nameslist
			if(node != null)
				nodeslist.add(output); //if the node exists in the graph, add it to the return list
			}
		return nodeslist;
		}
		
	public  void addNodeData(ArrayList<Node> nodeslist, String variablename, String data, boolean override) //return all names for a specified list of nodes
		{
		ArrayList<String> nameslist = new ArrayList<String>();
		for(int i=0; i < nodeslist.size(); i++)
			{
			Node output = nodeslist.get(i);
			if(output != null)
				output.addNodeData(variablename,data,override);
			}
		}
		
	public  void removeNodeData(ArrayList<Node> nodeslist, String variablename) //removes data from selected nodes
		{
		ArrayList<String> nameslist = new ArrayList<String>();
		for(int i=0; i < nodeslist.size(); i++)
			{
			Node output = nodeslist.get(i);
			if(output != null)
				output.removeNodeData(variablename);
			}
		}
	
	public void graphFromFile(String filename) throws FileNotFoundException
		{
		File file1 = new File(filename);
        Scanner inFile1 = new Scanner(file1);
        
        List<String> Inputs1 = new ArrayList<>();
       
	//read from data file
        while(inFile1.hasNextLine())
		{
		String line = inFile1.nextLine();
		Inputs1.add(line);
		}
        
        System.out.println("Data file read successful...");
        inFile1.close();
		int leftreadIndex = 1;
		int rightreadIndex = -1;
		int nodeCount = Integer.parseInt(Inputs1.get(0).replaceAll("\\D+",""));
		rightreadIndex = nodeCount+1;
		//Create nodes and add data
		for (int i = leftreadIndex; i < rightreadIndex; i++)
			{
			String[] currentLine = Inputs1.get(i).split(",");
			String name = currentLine[0];
			Node newNode = new Node(name,this);
			NametoNode.put(name,newNode);
			for (int k = 1; k < currentLine.length; k+=2)
				{
				newNode.addNodeData(currentLine[k],currentLine[k+1],true);
				}
			}
			
		leftreadIndex += nodeCount;
		rightreadIndex += nodeCount;
			
		//Add connections between nodes	
		for (int i = leftreadIndex; i < rightreadIndex; i++)
			{
			
			String[] currentLine = Inputs1.get(i).split(","); //splits input line into connection sections(node-neighbors-children-parents)
			String name = currentLine[0];
				
			if (currentLine.length > 1) //checks if connection information exists
				{
				if(currentLine[1] != "null") //check if connection data exists
					{
					for (int k = 1; k+2 < currentLine.length; k+=3) //iterate through connection data in groups of 3
						{
						String neighborName = currentLine[k];	//get neighbor name,
						Node neighborRef =  NametoNode.get(neighborName); //lookup neighbor reference in hashtable using name
						int connectionValue = Integer.parseInt(currentLine[k+1]); //Amount of traffic between two nodes
						String connectionVerb = currentLine[k+2]; //verb used to specify connection
						NametoNode.get(name).addConnection(neighborRef,connectionValue,connectionVerb); //create the connection between the two nodes
						}
					}
				}	
			}
		}
		
	public void graphFromTextFile(String filename) throws FileNotFoundException //reads a text file and creates a graph from that
		{
		File file1 = new File(filename);
        Scanner inFile1 = new Scanner(file1);

		//strings containing the current and last read words
		String currentword = "";
		String lastword = "";
		//read from data file
        while(inFile1.hasNextLine()) //iterate through file line by line
		{
		String line = inFile1.nextLine(); //read line
		//Fix any spacing issues with periods so a period is its own "word"	
		line = line.replace(" .",".");
		line = line.replace(". ",".");	
		line = line.replace("."," . ");
			
		String[] words = line.split(" "); //split into words
		for (int i = 0; i < words.length; i++) //iterate word by word
			{
			currentword = words[i];
			if(getNode(currentword) == null) //check if word does not exist in dictionary
				{
				Node newNode = new Node(currentword,this); //create the word
				NametoNode.put(currentword,newNode); //add the word to this graphs map for reference
				}
			if(!lastword.equals("")) //check if second or more word
				{
				Node currentwordnode = NametoNode.get(currentword);
				Node lastwordnode = NametoNode.get(lastword);
				//DO NOT FORGET TO UNCOMMENT THIS CODE OUT WHEN DONE. WITHOUT THIS IT DOESN'T STORE BACKWARDS CONTEXT
				//currentwordnode.addTrafficConnection(lastword,1,"before");
				lastwordnode.addTrafficConnection(currentword,1,"after");
				}
			lastword = currentword;
			}
		}
        
        System.out.println("Data file read successful...");
        inFile1.close();
		}
		
		
	public void addNode(String name,Node node) //adds a node to this graph
		{
		if (!NametoNode.containsKey(name) && !NametoNode.containsValue(node)) //check if node isn't already added to list of nodes
			{
			NametoNode.put(name,node); //adds the node to list
			//node.addConnection(this, traffic, verb);  //adds the same connection to the neighbor node. Remove if you want a directed graph
			}
		}
		
	public void removeNode(Node node)
		{
		String name =  node.getName();
		removeNode(name);
		}
		
	public void removeNode(String name)
		{
		if(NametoNode.containsKey(name))
			{
			Node node = NametoNode.get(name);
			String[] namesList = NametoNode.keySet().toArray(new String[0]); //returns an array of all names in nametonode
			for(int i=0; i < namesList.length; i++)
				{
				Node neighbor = NametoNode.get(namesList[i]);
				neighbor.removeConnection(node);
				}
			NametoNode.remove(name);	
			}
		}
		
	public void removeNodes(ArrayList<Node> nodeslist) //remove all specified nodes from this graph
		{
		for(int i=0; i < nodeslist.size(); i++)
			{
			Node node = nodeslist.get(i);
			removeNode(node);
			}
		resetQueryState();
		}
	
	public void addConnection(Node left,Node right,int traffic, String verb) //prints the network for all nodes in a specified list
		{
		if ((left != null) && (right != null))//makes sure node isn't null
			{
			left.addConnection(right,traffic,verb);
			}
		}
		
	public void addConnectionGroup(ArrayList<Node> nodeslist,Node node,int traffic, String verb,boolean reverse) //prints the network for all nodes in a specified list
		{
		if (node != null) //makes sure node isn't null
			{
			for(int i=0; i < nodeslist.size(); i++)
				{
				Node output = nodeslist.get(i);
				if (reverse)	//determines direction of connections
					{
					output.addConnection(node, traffic, verb); //adds connections from all nodes in list to node
					}
				else
					{
					node.addConnection(output, traffic, verb); //adds connections from node to all nodes in list
					}
				}
			}
		}
		
	public void clearGraph() //clears all nodes and connections from graph
		{
		clearGraphConnections(); //clear all connections in graph
		NametoNode.clear(); //clear all nodes. Must happen after clearing connections
		}
		
	public void clearGraphConnections() //clears all connections from graph
		{
		String[] namesList = NametoNode.keySet().toArray(new String[0]); //returns an array of all names in nametonode
		for(int i=0; i < namesList.length; i++)	//go through every node in this graph and tell it to remove all its connections
			{
			NametoNode.get(namesList[i]).clearConnections();	
			}
		}
		
	public void printNetwork()	//prints the network for all nodes in this graph
	{
	String[] namesList = NametoNode.keySet().toArray(new String[0]); //returns an array of all names in nametonode
		//String[] y = x.toArray(new String[0]);
	for(int i=0; i < namesList.length; i++)
		{
		Node output = NametoNode.get(namesList[i]);
		System.out.println("	"+output.getDataFormatted());
		}
	for(int i=0; i < namesList.length; i++)
		{
		Node output = NametoNode.get(namesList[i]);
		System.out.println("	"+output.getConnectionsFormatted());
		}
	}
	
	public void printNetwork(ArrayList<Node> nodeslist) //prints the network for all nodes in a specified list
	{
	for(int i=0; i < nodeslist.size(); i++)
		{
		Node output = nodeslist.get(i);
		System.out.println("	"+output.getDataFormatted());
		}
	for(int i=0; i < nodeslist.size(); i++)
		{
		Node output = nodeslist.get(i);
		System.out.println("	"+output.getConnectionsFormatted());
		}
	}
	
	public void printNetworkConnections()	//prints the connections for all nodes in this graph
	{
	String[] namesList = NametoNode.keySet().toArray(new String[0]); //returns an array of all names in nametonode
	for(int i=0; i < namesList.length; i++)
		{
		Node output = NametoNode.get(namesList[i]);
		System.out.println("	"+output.getConnectionsFormatted());
		}
	}
	
	public void writeNetwork(ArrayList<Node> nodeslist, String filename) //prints the network for all nodes in a specified list to a formatted file
	{
		
	try
	{
    PrintWriter writer = new PrintWriter(filename, "UTF-8");
	writer.println(String.valueOf(nodeslist.size())); //write the size of the 
	for(int i=0; i < nodeslist.size(); i++)
		{
		Node output = nodeslist.get(i);
		writer.println(output.getDataFormatted());
		}
    for(int i=0; i < nodeslist.size(); i++)
		{
		Node output = nodeslist.get(i);
		writer.println(output.getConnectionsFormatted());
		}
    writer.close();
	} catch (IOException e) 
		{}
	}
	
	public void printLastQueryResult()
	{
	if(this.queryResults.size() > 0)
		{	
		printNetwork(queryResults);
		}
	}
	
	
	public ArrayList<Node> runConnectionQuery(String query) //Used for chaining queries. Runs a fresh query if meant to
	{
		if(clearedQuery)
		{
		return runConnectionQuery(getNodeArrayList(),query); //starts a fresh query from all nodes in the graph
		}
		else
		{
		return runConnectionQuery(this.queryResults,query); //resuses the results of an old query for chaining queries
		}
	}
	
	public ArrayList<Node> runConnectionQuery(ArrayList<Node> nodeList, String query) //runs a specified query on a group of nodes and returns the outbound nodes satisfying it
		{
		ArrayList<Connection> connectlist = getAllConnections(nodeList);
		ConnectionQuery newquery = new ConnectionQuery(connectlist,query);
			
		this.clearedQuery = false;		//sets query state to old
		this.queryResults = newquery.getNodeQuery();
			
		return queryResults;	
		//return newquery.getNodeQuery();	
		}
	
	public ArrayList<Node> runNodeQuery(String query) //Used for chaining queries. Runs a fresh query if meant to
	{
		if(clearedQuery)
		{
		return runNodeQuery(getNodeArrayList(),query); //starts a fresh query from all nodes in the graph
		}
		else
		{
		return runNodeQuery(this.queryResults,query); //resuses the results of an old query for chaining queries
		}
	}
		
	public ArrayList<Node> runNodeQuery(ArrayList<Node> nodeList, String query) //runs a specified query on a group of nodes and returns the nodes satisfying it
		{
		NodeQuery newquery = new NodeQuery(nodeList,query);
			
		this.clearedQuery = false;	//sets query state to old
		this.queryResults = newquery.getNodeQuery();
		
		return queryResults;	
		//return newquery.getNodeQuery();
		}
	
	public ArrayList<Node> runPathQuery(ArrayList<Node> nodeList, Node startnode, Node endnode) //runs a query getting the shortest path between two nodes
		{	
		ArrayList<Node> outputlist = new ArrayList<Node>();
		if ((startnode != null)&&(endnode != null)&&(nodeList.size() > 0))
			{
			this.clearedQuery = false;	//sets query state to old
			outputlist = PathFinder.getPath(startnode,endnode,nodeList);
			this.queryResults = outputlist;
			}
		return outputlist;	
		}
		
	public ArrayList<Node> runPathQuery(ArrayList<Node> nodeList, String start, String end) //calls runPathQuery with nodes, using names
		{
		Node startnode = getNode(start);
		Node endnode = getNode(end);
		return runPathQuery(nodeList, startnode,endnode);
		}
	
	public ArrayList<Connection> getAllConnections(ArrayList<Node> nodeslist) //return all connections for a specified list of nodes
		{
		ArrayList<Connection> connectlist = new ArrayList<Connection>();
		for(int i=0; i < nodeslist.size(); i++)
			{
			Node output = nodeslist.get(i);
			connectlist.addAll(output.getAllConnections());
			}
		return connectlist;
		}
		
		
	public ArrayList<Connection> getAllConnections() //return all connections for  all nodes in this graph
		{
		ArrayList<Node> nodeslist = getNodeArrayList();
		ArrayList<Connection> connectlist = new ArrayList<Connection>();
		for(int i=0; i < nodeslist.size(); i++)
			{
			Node output = nodeslist.get(i);
			connectlist.addAll(output.getAllConnections());
			}
		return connectlist;
		}
		
	public ArrayList<Node> getNodeArrayList()	//converts keys in nodelist hashmap to an arraylist
		{
		ArrayList<Node> whatever = new ArrayList<Node>();
		String[] namesList = NametoNode.keySet().toArray(new String[0]); //returns an array of all names in nametonode
		
		for(int i=0; i < namesList.length; i++)
			{
			Node output = NametoNode.get(namesList[i]);
			whatever.add(output);
			}
		return whatever;
		}
	
		
	
}