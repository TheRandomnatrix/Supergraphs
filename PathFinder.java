import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class PathFinder
	{

	public static ArrayList<Node> getPath(Node startnode, Node endnode, ArrayList<Node> nodelist, boolean weighted) 
		{
		//Given a start and end node, compute a path between the two over the given subgraph using a breadth first search

		ArrayList<Node> outputlist = new ArrayList<Node>(); //the path to return
		HashMap<Node,Integer> distances = new HashMap<Node,Integer>(); //maps the distance of a node from the start point
		HashMap<Node,Node> parent = new HashMap<Node,Node>(); //maps a child to a parent. Necessary for traversing backwards once end node is found
		ArrayList<Node> nodeset = new ArrayList<Node>(); //the set of nodes that are to be visited
		ArrayList<Connection> connectlist = new ArrayList<Connection>();
		boolean found = false;	
			
		//test if the start and end nodes are even in the given subgraph. If they aren't, return empty list
		if((!nodelist.contains(startnode)) || (!nodelist.contains(endnode))) 
			return outputlist;
		
		for(int i=0; i < nodelist.size(); i++) //create the hash table for the distances
			{
			distances.put(nodelist.get(i),1000000); //set it to some arbitrarily high number
			}
			
		distances.put(startnode,0); //set the starting point distance to 0
		nodeset.add(startnode); //add the start node to the list of nodes to visit
		
		while(nodeset.size() > 0) //iterate through the list of to be visited nodes, which will expand/contract with each iteration
			{
			//System.out.println("	"+nodeset.get(0).getName());
			connectlist.clear();		//reset the list of connections
			connectlist.addAll(nodeset.get(0).getAllConnections());	//get all the connections for current node
			for(int i=0; i < connectlist.size(); i++) //run through those connections
				{
				//System.out.println("	test");
				Node rightnode =  connectlist.get(i).getrightNode(); //get the end node from each connection
				if(distances.containsKey(rightnode)) 	//check if that node lies within the subgraph, otherwise ignore it
					{
					
					int TentativeDist = 1;	//set tentative distance to 
					if(weighted)
						{
						TentativeDist = Math.abs(connectlist.get(i).getTraffic());
						if (TentativeDist == 0)
							{
							TentativeDist = 1;
							}
						}
					//System.out.println(nodeset.get(0).getName() + " -> "+rightnode.getName());
					//System.out.println("	"+(distances.get(rightnode)) + " : " + (distances.get(nodeset.get(0))+TentativeDist));
					if(distances.get(rightnode) > (distances.get(nodeset.get(0))+TentativeDist)) //check if tentative distance is less than current distance
						{
						//System.out.println("	less than");
						distances.put(rightnode,distances.get(nodeset.get(0))+TentativeDist); 	//set distance to tentative distance
						nodeset.add(rightnode); 																		//add the node to the list to traverse
						parent.put(rightnode,nodeset.get(0)); 													//make the parent of that node the current node
						if(rightnode == endnode) //found the end goal
							{
							found = true;
							}
						}
					}
				}
			nodeset.remove(0);
			}
			
		if(found == true) //path was found. Backtrace from end and return list of nodes
			{
			//System.out.println("found it");
			Node p = endnode; 	//get end node
			outputlist.add(0,p);	//add to output list
			//System.out.println("		"+p.getName());
			p = parent.get(p);	
			while(p != null)	//backtrace to start
				{
				//System.out.println("		"+p.getName());
				outputlist.add(0,p);
				p = parent.get(p);
				}
			}
			else
			{
			System.out.println("didn't find it");
			}
		return outputlist;
		}

	}