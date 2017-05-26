import java.util.*;
//import java.util.ArrayList;
//import java.util.List;

public class Node
{
	//Maintains lists of connections. Maps a destination node from this node to a list of connections to that destination node
	private HashMap<Node,ArrayList<Connection>> ConnectionList;
	//name of the node. Should be unique
	private String Name;
	//graph this node exists in
	private Graph parentGraph;
	//Maintains a variablename:variablevalue data mapping
	private HashMap<String,String> NodeData;
	
	private HashSet<String> DataGroups;	//list of names of nodes that this node belongs to
	
	
	Node(String name,Graph parentGraph) //constructor
		{
		//Maps connections. 
		ConnectionList = new HashMap<Node,ArrayList<Connection>>(); 
		this.Name = name; 
		this.parentGraph = parentGraph;
		NodeData = new HashMap<String,String>();
		this.DataGroups = new HashSet<String>();
		}

	public String getName()
		{
			return this.Name;
		}
		
	public HashMap<String,String> getNodeData()  //returns node data
		{
		return this.NodeData;
		}
	

	public void inheritData()  //returns node data from inherited nodes. Will recursively run through things
		{
		this.NodeData = (HashMap<String,String>)getNodeDataInherited(0).clone();
		}
		
	public HashMap<String,String> getNodeDataInherited(int depth)  //returns node data from inherited nodes. Will recursively run through things
		{
		//print the depth and current node
		for (int b = 0; b < depth; b++)
			System.out.print("	");
		System.out.println("current node: "+this.getName());	//print the name of the node
			

		HashMap<String,String> ReturnData = new HashMap<String,String>();	// this.NodeData.clone();
		if(!DataGroups.isEmpty())
			{
			String[] namesList = DataGroups.toArray(new String[0]);		//get the list of groups
			//System.out.println("	parents: "+namesList);
				
			for(int i = 0; i <namesList.length; i++) 	//iterate through all groups
				{
				Node node = parentGraph.getNode(namesList[i]);
				if(node != null)
					{
					HashMap<String,String> InheritedData = node.getNodeDataInherited(depth+1);	//get 
					String[] dataList = InheritedData.keySet().toArray(new String[0]); //returns list of variables in the node
						
					for(int k = 0; k < dataList.length; k++) //iterate through inherited data
						{
						ReturnData.put(dataList[k],node.getNodeDataValue(dataList[k]));	//put the inherited data in
						}
					}
				}
				
			String[] dataList = this.getNodeData().keySet().toArray(new String[0]); //returns list of variables in the node
			for(int i = 0; i < dataList.length; i++) //iterate through current data
				{
				ReturnData.put(dataList[i],this.getNodeDataValue(dataList[i]));
				}
			}
		else
			{
			return getNodeData(); //return ones own data
			}
		return ReturnData;
		}
	
	public Boolean containsNodeData(String variablename) //checks to see if the node contains a specified variable
		{
			return NodeData.containsKey(variablename);
		}
	
	public void addDataGroup(String name) //adds a data group to the node
		{
		if(!this.Name.equals(name)) //make sure the node is not directly a child of itself
			{
			this.DataGroups.add(name);
			}
		else
			{
			System.out.println("ERROR: CANNOT ADD CHILD TO SELF");
			}
		}
	
	public HashSet<String> getDataGroups() //adds a data group
		{
		return this.DataGroups;
		}
		
	public void removeDataGroup(String name) //removes a data group
		{
		DataGroups.remove(name);
		}
		
	public String getNodeDataValue(String variablename)  //returns node data. null if it doesn't exist
		{
		return NodeData.get(variablename);
		}
		
	public void addNodeData(String variablename, String data, boolean override) //adds a datum to the node, will only overwrite if specified. Uses hashmap
		{
		if(override) //whether to overwrite existing data
			NodeData.put(variablename,data); //adds the variable,value to list, overwrites value if already exists
		else
			{
			if(!NodeData.containsKey(variablename))
				{
				NodeData.put(variablename,data);
				}
			}
		}
		
	public void removeNodeData(String variablename) //removes a datum from the node. Uses hashmap
		{
		NodeData.remove(variablename); //removes a variable from the node, if it exists
		}
		
	public void removeConnection(Node node)
		{
		ConnectionList.remove(node);
		}

	public void addConnection(Node node,int traffic,String verb) //adds a neighbor node
		{
		if (!ConnectionList.containsKey(node)) //check if there are not any connections to the node
			{
			ArrayList<Connection> newConnectionList = new ArrayList<Connection>(); //create new list of connections
			ConnectionList.put(node,newConnectionList);	//map the new empty list to the node
			newConnectionList.add(new Connection(this,node,traffic,verb)); //add a new connection to the list
			}
		else
			{
			if(nodeverbSearch(node, verb) == null)	//check if a duplicate connection to the node with the same verb doesn't exist
				{
				ConnectionList.get(node).add(new Connection(this,node,traffic,verb)); //create a new connection and add it to existing connection list for specified node
				}
			}
		}
		
	public void addConnection(String name,int traffic,String verb) //adds a neighbor node
		{
		 Node node = this.parentGraph.getNode(name);
		if(node != null)
			{
			 addConnection(node,traffic,verb);
			}
		}
	
	public Connection nodeverbSearch(Node node, String verb) //returns connection if it exists with specified verb, connecting from this node to specified node
			{
			if (ConnectionList.containsKey(node))
				{
				ArrayList<Connection> newConnectionList = new ArrayList<>(ConnectionList.get(node));
				for(int i = 0; i < newConnectionList.size(); i++)
					{
					if(newConnectionList.get(i).getVerb().equals(verb))
						{
						return newConnectionList.get(i);
						}
					}
				}
			return null;
			}
		
	public void addTraffic(Node node, int trafficIncrease, String verb) //adds traffic at the following node.
		{
		Connection tempConnection = nodeverbSearch(node,verb);
		if(tempConnection != null)
			{
			tempConnection.addTraffic(trafficIncrease);
			}
		}
		
	public void addTraffic(String name, int trafficIncrease, String verb) //adds traffic at the following node.
		{
		Node node = this.parentGraph.getNode(name);
		if(node != null)
			{
			Connection tempConnection = nodeverbSearch(node,verb);
			if(tempConnection != null)
				{
				tempConnection.addTraffic(trafficIncrease);
				}
			}
		}
	
	public void clearConnections() //removes all connections for this node
		{
		Node[] nodeList = ConnectionList.keySet().toArray(new Node[0]);
		for (int i = 0; i < nodeList.length; i++) //loop through and concatenate node data
			{
			removeConnection(nodeList[i]);
			}
		}
		
	public void addTrafficConnection(String name, int trafficIncrease, String verb) //adds traffic at the following node, will additionally create a connection if it doesn't exist
		{
		Node node = this.parentGraph.getNode(name);
		if(node != null) //checks if connects for that node exist
			{
			Connection tempConnection = nodeverbSearch(node,verb);
			if(tempConnection != null)	//checks if connects for that node exist for that verb
				{
				tempConnection.addTraffic(trafficIncrease); //add to traffic at existing connection
				}
			else
				{
				addConnection(name,trafficIncrease,verb);	 //create a new connection if it doesnt exist
				}
			}
			else
			{
			addConnection(name,trafficIncrease,verb); //create a new node if it doesnt exist
			}
		}
		
		public String getDataFormatted() //Returns a string containing formatted data about node
		{
		String[] dataList = NodeData.keySet().toArray(new String[0]);		//list of nodes connected to this node
		String output = getName();
		for (int i = 0; i < dataList.length; i++) //loop through and concatenate node data
			{
			output+= ","+dataList[i];							//get the variable name
			output+= ","+NodeData.get(dataList[i]);	//get the value ascociated with the variable
			}
		return output;
		}
		
		public String getConnectionsFormatted() //Returns a string containing CSV formatted data about node connections	
		{
		String output = getName();	//output variable
		ArrayList<Connection> allConnectionList = getAllConnections(); //list of all connections from this node
		if(allConnectionList.size() == 0) //no connections for this node
			{
			output += ",null";
			}
		
		for (int k = 0; k < allConnectionList.size(); k++) //loop through and concatenate connection data
			{
			Connection connection =  allConnectionList.get(k);
			output += "," + connection.getrightNode().getName();
			output += "," + connection.getTraffic();
			output += "," + connection.getVerb();
			}

		return output;
		}
		
	public ArrayList<Connection> getAllConnections() //returns list of all connections from this node to other nodes.
		{
		Node[] nodeList = ConnectionList.keySet().toArray(new Node[0]);		//list of nodes connected to this node
		ArrayList<Connection> connectList = new ArrayList<Connection>();	//list of connections. Will be populated with all connections from this node	
		for(int i = 0; i <nodeList.length; i++) //iterate through all connections
				{
				connectList.addAll(ConnectionList.get(nodeList[i]));
				}
		return connectList;
		}

	public Node getWeightedNeighbor() //returns a neighbor, with a weighted distribution using traffic data
		{
		ArrayList<Connection> connectList = getAllConnections();	//list of all connections from this node to other nodes.
		String output = getName();
		int TrafficSum = 0; //sum of all traffic data
			
		if(connectList.size() > 0) //check if there are connections
			{
			//iterate through first time to get sum of weights
			for(int i = 0; i < connectList.size(); i++) //iterate through all connections
				{
				TrafficSum += connectList.get(i).getTraffic();
				}
				
			//get random number between sum	
			int randomWeight = (int) (Math.random() * TrafficSum);
			//reset counter and iterate through to find weights
			int leftSum = 0;
			int rightSum = 0;
				
			for(int i = 0; i < connectList.size(); i++) //iterate through all connections
				{
				leftSum = rightSum;
				rightSum += connectList.get(i).getTraffic();
				if((randomWeight >= leftSum) &&  (randomWeight <= rightSum))
					{
					return connectList.get(i).getrightNode();
					}
				}	
			}
		return null; //no neighbors or weighted distribution is messed up
		}
}