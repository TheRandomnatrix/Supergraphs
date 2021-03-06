import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;

public class ConnectionQuery extends Query
	{
	private static final String variabletag = "::";
	
	public static ArrayList<Node> getNodeQuery(ArrayList<Connection> ConnectionList, String query, String returnside) 
		{
		/*//This method evaluates every connection in the constructor with the constructor query
			and returns the list of all destination nodes from all valid connections
			
			*/
		//System.out.println(ConnectionList);
		ArrayList<Connection> connectList = evaluateConnections(ConnectionList,query); //narrow list of connections down
		//System.out.println(connectList);
		Set<Node> nodeList = new HashSet<Node>();
		for (int i = 0; i < connectList.size(); i++)
			{
			if(returnside.equals("left") || returnside.equals("both"))
				{
				nodeList.add(connectList.get(i).getleftNode()); //get the right nodes of all connections
				}
			if(returnside.equals("right") || returnside.equals("both"))
				{
				nodeList.add(connectList.get(i).getrightNode()); //get the right nodes of all connections
				}
			}
		ArrayList<Node> setList = new ArrayList<Node>(nodeList);
		return setList;
		}

	public static String filterQueryParams(Connection connect, String rawquery) //returns string replacing keywords with their actual values from a connection
		{
			String q = rawquery;
			q = q.replaceAll(variabletag+"RANDOMNUM",String.valueOf((int) (Math.random() * 100000))); //replaces randomnum with random number
			
			q = q.replaceAll(variabletag+"traffic",String.valueOf(connect.getTraffic()));
			q = q.replaceAll(variabletag+"verb",connect.getVerb());
			q = q.replaceAll(variabletag+"leftname",connect.getleftNode().getName());
			q = q.replaceAll(variabletag+"rightname",connect.getrightNode().getName());
			
			Node node = connect.getleftNode();
			String[] leftdataList = node.getNodeData().keySet().toArray(new String[0]); //returns list of variables in the node
			for(int i = 0; i < leftdataList.length; i++) //iterate through query, replacing all variables with their actual value if it exists
				{
				q = q.replaceAll(variabletag+"LEFT"+leftdataList[i],node.getNodeDataValue(leftdataList[i]));
				}
			if(q.indexOf((variabletag+"LEFT"+"ConnectionCount"))!=-1)	//checks to see if connection count is desired. Check is done to avoid calling it uneccessarily
				{
				q = q.replaceAll((variabletag+"LEFT"+"ConnectionCount"),String.valueOf(node.getAllConnections().size())); //gets count of outbound connections
				}
			node = connect.getrightNode();
			String[] rightdataList = node.getNodeData().keySet().toArray(new String[0]); //returns list of variables in the node
			for(int i = 0; i < rightdataList.length; i++) //iterate through query, replacing all variables with their actual value if it exists
				{
				q = q.replaceAll(variabletag+"RIGHT"+rightdataList[i],node.getNodeDataValue(rightdataList[i]));
				}
			if(q.indexOf((variabletag+"RIGHT"+"ConnectionCount"))!=-1)	//checks to see if connection count is desired. Check is done to avoid calling it uneccessarily
				{
				q = q.replaceAll((variabletag+"RIGHT"+"ConnectionCount"),String.valueOf(node.getAllConnections().size())); //gets count of outbound connections
				}
			return q;
		}
	
	public static ArrayList<Connection> evaluateConnections(ArrayList<Connection> ConnectionList, String query)
		{
		ArrayList<Connection> returnList = new ArrayList<Connection>();
		for (int i = 0; i < ConnectionList.size(); i++)
			{
			Connection connect = ConnectionList.get(i);
			String q = filterQueryParams(connect,query);
			if(q.indexOf(variabletag)==-1)	//checks to see if any variables were not properly replaced with their true value. Will be an invalid expression
				{
				if(parseString(q).equals("true")) //parse the query looking for a successful return
					{
					returnList.add(connect); //add it to the return list
					}
				}
			}
		return returnList;
		}
	}