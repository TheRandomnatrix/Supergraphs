import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;

public class Connection
{
	private Node leftNode;
	private Node rightNode;
	private String leftName;
	private String rightName;
	private Graph parentGraph;
	private int traffic;
	private String verb;
		
	Connection(Graph graph, String leftnode,String rightnode,int traffic,String verb) //constructor
		{
		this.parentGraph = graph;
		this.leftName = leftnode;
		this.rightName = rightnode;
		this.traffic = traffic;
		this.verb = verb;
		}
	
	public Node getleftNode()
		{
		return parentGraph.getNode(this.leftName);
		}
	public Node getrightNode()
		{
		return parentGraph.getNode(this.rightName);
		}	
	public int getTraffic()
		{
			return this.traffic;
		}
	public void addTraffic(int value)
		{
			this.traffic += value;
		}
	public String getVerb()
		{
		return this.verb;	
		}

}