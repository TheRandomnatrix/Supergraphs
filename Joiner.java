import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Joiner
	{

	
		
	public static ArrayList<String> getFullJoin(ArrayList<String> leftnodeslist, ArrayList<String> rightnodeslist) //return all nodes between the two lists
		{
		//System.out.println("FULL:");
		//System.out.println(leftnodeslist);
		//System.out.println(rightnodeslist);
		ArrayList<String> outputlist = leftnodeslist;
		for(int i=0; i < rightnodeslist.size(); i++) //get all nodes from right list that aren't in left list
			{
			String node = rightnodeslist.get(i);
			if (!leftnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		//System.out.println(outputlist);
		return outputlist;
		}	
	
		
	public static ArrayList<String> getOuterJoin(ArrayList<String> leftnodeslist, ArrayList<String> rightnodeslist) //return all nodes not shared between the two lists
		{
		//System.out.println("OUTER:");
		//System.out.println(leftnodeslist);
		//System.out.println(rightnodeslist);
		ArrayList<String> outputlist = new ArrayList<String>();
		for(int i=0; i < leftnodeslist.size(); i++)	//get all nodes from left list that aren't in right list
			{
			String node = leftnodeslist.get(i);
			if (!rightnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		
		for(int i=0; i < rightnodeslist.size(); i++) //get all nodes from right list that aren't in left list
			{
			String node = rightnodeslist.get(i);
			if (!leftnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		//System.out.println(outputlist);
		return outputlist;
		}
	
	public static ArrayList<String> getLeftOuterJoin(ArrayList<String> leftnodeslist, ArrayList<String> rightnodeslist) //return all nodes in left list that are not shared between the two lists
		{
		//System.out.println("LEFT:");
		//System.out.println(leftnodeslist);
		//System.out.println(rightnodeslist);
		ArrayList<String> outputlist = new ArrayList<String>();
		for(int i=0; i < leftnodeslist.size(); i++)	//get all nodes from left list that aren't in right list
			{
			String node = leftnodeslist.get(i);
			if (!rightnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		//System.out.println(outputlist);
		return outputlist;
		}

	
	public static ArrayList<String> getInnerJoin(ArrayList<String> leftnodeslist, ArrayList<String> rightnodeslist) //returns all nodes shared between the two lists
		{
		//System.out.println("INNER:");
		//System.out.println(leftnodeslist);
		//System.out.println(rightnodeslist);
		ArrayList<String> outputlist = new ArrayList<String>();
		for(int i=0; i < leftnodeslist.size(); i++)	//get all nodes from left list that are in right list
			{
			String node = leftnodeslist.get(i);
			if (rightnodeslist.contains(node))
				{
				outputlist.add(node);
				}
			}
		//System.out.println(outputlist);
		return outputlist;
		}
	}