import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Joiner
	{

	public static ArrayList<Node> getFullJoin(ArrayList<Node> leftnodeslist, ArrayList<Node> rightnodeslist) //return all nodes between the two lists
		{
		ArrayList<Node> outputlist = leftnodeslist;
		
		for(int i=0; i < rightnodeslist.size(); i++) //get all nodes from right list that aren't in left list
			{
			Node node = rightnodeslist.get(i);
			if (!leftnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		return outputlist;
		}	
		
	public static ArrayList<Node> getOuterJoin(ArrayList<Node> leftnodeslist, ArrayList<Node> rightnodeslist) //return all nodes not shared between the two lists
		{
		ArrayList<Node> outputlist = new ArrayList<Node>();
		for(int i=0; i < leftnodeslist.size(); i++)	//get all nodes from left list that aren't in right list
			{
			Node node = leftnodeslist.get(i);
			if (!rightnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		
		for(int i=0; i < rightnodeslist.size(); i++) //get all nodes from right list that aren't in left list
			{
			Node node = rightnodeslist.get(i);
			if (!leftnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		return outputlist;
		}
	
	public static ArrayList<Node> getLeftOuterJoin(ArrayList<Node> leftnodeslist, ArrayList<Node> rightnodeslist) //return all nodes in left list that are not shared between the two lists
		{
		ArrayList<Node> outputlist = new ArrayList<Node>();
		for(int i=0; i < leftnodeslist.size(); i++)	//get all nodes from left list that aren't in right list
			{
			Node node = leftnodeslist.get(i);
			if (!rightnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		return outputlist;
		}
		
	public static ArrayList<Node> getInnerJoin(ArrayList<Node> leftnodeslist, ArrayList<Node> rightnodeslist) //returns all nodes shared between the two lists
		{
		ArrayList<Node> outputlist = new ArrayList<Node>();
		for(int i=0; i < leftnodeslist.size(); i++)	//get all nodes from left list that are in right list
			{
			Node node = leftnodeslist.get(i);
			if (rightnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		return outputlist;
		}
	}