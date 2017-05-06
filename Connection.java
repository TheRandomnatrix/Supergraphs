import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;

public class Connection
{
	private Node leftNode;
	private Node rightNode;
	private int traffic;
	private String verb;
	
	
	Connection(Node leftnode,Node rightnode,int traffic,String verb) //constructor
		{
		this.leftNode = leftnode;
		this.rightNode = rightnode;
		this.traffic = traffic;
		this.verb = verb;
		}
	
	public Node getleftNode()
		{
		return this.leftNode;
		}
	public Node getrightNode()
		{
		return this.rightNode;
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