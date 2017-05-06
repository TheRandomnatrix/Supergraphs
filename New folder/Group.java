import java.util.ArrayList;
import java.util.List;

public class Group extends Node
{
	private ArrayList<Node> NodeList; //list of neighboring nodes
	private ArrayList<Node> ChildList; //list of child nodes
	private ArrayList<Node> ParentList;
	private ArrayList<String> NodeData;
	String Name = "";
	
	Group(String name) //constructor
		{
		NodeList = new ArrayList();
		ChildList = new ArrayList();
		NodeData = new ArrayList();
		this.Name = name;
		}
	
	public ArrayList<Node> getChildList() //returns list of neighbors
		{
		return this.ChildList;
		}
		
	public setTop(int top)
		{
			this.isTop = top;
		}
	
	public void addChild(Node node) //adds a neighbor node
		{
		for (int i = 0; i < ChildList.size(); i++) //loop through and check if duplicate
			{
				if (node == ChildList.get(i))
					return;
			}
		ChildList.add(node); //adds the node to list
		node.addParent(this);
		}
		

}