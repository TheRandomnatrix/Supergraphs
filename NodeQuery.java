import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;

public class NodeQuery extends Query
	{
	private static final String variabletag = "::";

private static String filterQueryParams(Node node, String rawquery) //returns string replacing keywords with their actual values from a connection
	{
		String q = rawquery;
		
		q = q.replaceAll(variabletag+"name",node.getName()); //replaces "name" with node's name
		q = q.replaceAll(variabletag+"RANDOMNUM",String.valueOf((int) (Math.random() * 100000))); //replaces randomnum with random number
		
		String[] dataList = node.getNodeData().keySet().toArray(new String[0]); //returns list of variables in the node
		for(int i = 0; i < dataList.length; i++) //iterate through query, replacing all variables with their actual value if it exists
		{
		q = q.replaceAll(variabletag+dataList[i],node.getNodeDataValue(dataList[i]));
		}
		//q = q.replaceAll("traffic",String.valueOf(connect.getTraffic()));
		return q;
	}
	
public static ArrayList<Node> evaluateNodes(ArrayList<Node> NodeList,String query)
	{
	ArrayList<Node> returnList = new ArrayList<Node>();
	for (int i = 0; i < NodeList.size(); i++)
		{
		Node node = NodeList.get(i);
		String q = filterQueryParams(node,query);
		if(q.indexOf(variabletag)==-1)	//checks to see if any variables were not properly replaced with their true value. Will be an invalid expression
			{
			if(parseString(q).equals("true")) //parse the query looking for a successful return
				{
				returnList.add(node);
				}
			}
		}
	return returnList;
	}
	
	public static void computeNodeData(ArrayList<Node> nodeslist, String varname,String query) //computes and sets variables
		{
		for (int i = 0; i < nodeslist.size(); i++)
			{
			Node node = nodeslist.get(i);
			String q = filterQueryParams(node,query);
			if(q.indexOf(variabletag)==-1)	//checks to see if any variables were not properly replaced with their true value. Will be an invalid expression
				{
				String evaluatedstring = parseString(q);
				if(!evaluatedstring.equals("NULLRETURN")) //parse the query looking for a successful return
					{
					node.addNodeData(varname, evaluatedstring,true);
					}
				}
			}
		}
		
	public static ArrayList<String> computeDataSet(ArrayList<Node> nodeslist, String query) //computes a set of 
		{
		
		for (int i = 0; i < nodeslist.size(); i++)
			{
			Node node = nodeslist.get(i);
			String q = filterQueryParams(node,query);
			if(q.indexOf(variabletag)==-1)	//checks to see if any variables were not properly replaced with their true value. Will be an invalid expression
				{
				String evaluatedstring = parseString(q);
				if(!evaluatedstring.equals("NULLRETURN")) //parse the query looking for a successful return
					{
					node.addNodeData(varname, evaluatedstring,true);
					}
				}
			}
		}
		
	
	}