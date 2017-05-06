/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import java.io.PrintWriter;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.*;
/*import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Object;
import java.util.Random;
*/
public class Driver
{
 
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
	{ 
	
	//Data file variables
	
	/*parseit("( 1 && ( 1 && 0 ) )");
	parseit("( 12 * ( 5 - ( 1 + 2 ) ) )");
	parseit("( 12 * ( 5 - ( 1 + 2 ) ) ) > 15");
	parseit("( test == test )");
	parseit("( test == test1 )");
	parseit("( test1 && ( test2 && test3 ) )");*/
	
	/*String teststring = "word1.word2";	
	System.out.println*/
		
	String datafilename = "results.txt";
	String textfilename = "CiaphasCaneEdited.txt";
	String outputfilename = "NodeDataOutput.txt";
	Supergraph supergraph = new Supergraph();
	Graph graph = new Graph("graphythegraph");
	supergraph.addGraph(graph);
	
		
		/*(try
	{
	graph.graphFromFile(filename);
	}
	catch (FileNotFoundException e)
	{}*/
	
	//graph.printNetwork();
	//runCommand("/printnetwork",graph);
	//runCommand("/fromfile "+datafilename,graph);
	//runCommand("/fromtextfile "+textfilename,graph);
	//runCommand("/printnetwork",graph);
	//runCommand("/printallconnections",graph);
	
	//runCommand("/nodequery "+"( ( name == Name1 ) || ( name == Name2 ) )",graph);
	//runCommand("/printresults",graph);
	//runCommand("/clearquery",graph);
	//runCommand("/nodequery "+"( ( name == the ) )",graph);
	//runCommand("/nodequery "+"( LENGTH ( name ) <= 4 )",graph);
	//runCommand("/connectionquery "+"( true )",graph);
	//runCommand("/connectionquery "+"( LENGTH ( rightname ) == 3 )",graph);
	//runCommand("/nodequery "+"( ( Data1 < 30 ) )",graph);
	//runCommand("/printresults",graph);
	//runCommand("/writegraph "+outputfilename,graph);
	//runCommand("/clearquery",graph);
	//runCommand("/nodequery "+"( ( name == Name5 ) || ( name == Name6 ) )",graph);
	//runCommand("/addnodedata "+"isNoun true true",graph);
	//runCommand("/removenodedata "+"Data2",graph);
	//runCommand("/printresults",graph);
	//runCommand("/printnetwork",graph);

	
	//runCommand("/addnode Name9",graph);
	//runCommand("/printnetwork",graph);
	//runCommand("/printnetwork",graph);
	//runCommand("/clearconnections",graph);
	//runCommand("/printnetwork",graph);
	/*runCommand("/addtraffic Name1 Name2 37 verb1",graph);
	runCommand("/addtraffic Name1 Name3 23 verb1",graph);
	runCommand("/addtraffic Name2 Name2 23 verb2",graph);
	runCommand("/addtraffic Name2 Name3 37 verb1",graph);
	runCommand("/addtraffic Name3 Name1 23 verb1",graph);
	runCommand("/addtraffic Name3 Name4 23 verb2",graph);
	runCommand("/addtraffic Name4 Name7 37 verb1",graph);
	runCommand("/addtraffic Name7 Name9 23 verb1",graph);
	runCommand("/addtraffic Name9 Name1 23 verb2",graph);*/
	//graph.printNetwork();
	//System.out.println(graph.getNodeArrayList());
	//System.out.println(graph.getAllConnections());
	
	
	/*Scanner sc = new Scanner(System.in);
	String input = sc.nextLine();
	
	while(!input.equals("/exit"))
	{
	supergraph.runCommand(input,graph);
	//System.out.println(input);
	input = sc.nextLine();
	}*/
	
	supergraph.runCommand("~run "+"Scripts\\DatabaseCommands.txt",graph,false); //
	
	/*supergraph.runCommand("/fromfile "+datafilename,graph);
	//supergraph.runCommand("/pr-n",graph);
	Node start = graph.getNode("luggage");
	Node end = graph.getNode("emperors");
	System.out.println("Start: "+start.getName() + " End: " + end.getName());
	System.out.println(PathFinder.getPath(start,end,graph.getNodeArrayList()));  */
	
	/*System.out.println("running query whatever:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( Data1 > 30 )"));	//get all nodes with data1 greater than 30 
	System.out.println("running query whatever2:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( ( Data1 LEVEN 30 ) <= 1 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running modulus query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( ( Data1 % 10 ) == 3 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running logbase 2 query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( ( Data1 LOGBASE 2 ) < 4 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running exponential query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( ( Data1 POWEROF 2 ) == 169 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running unary query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( ABS ( Data1 * -1 ) == 30 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running string query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( LENGTH ( Data1 ) == 2 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running nary query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( MAX ( Data3 Data2 Data1 ) + 45 == 3045 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running nary query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( MIN ( MIN ( Data1 Data2 ) MIN ( Data3 Data4 ) ) == 4 )"));	//get all nodes with data1 greater than 30  */
	
	/*System.out.println("running string replace query:...");
	graph.printNetwork(graph.runNodeQuery(graph.getNodeArrayList(),"( STRREPLACE ( Data1 0 1 ) == 3111 )"));	//get all nodes with data1 greater than 30  */

	
	//System.out.print(33 % 10);
	
	/*System.out.println("Initializing chain query:...");
	//runCommand("/printresults",graph);
	runCommand("/clearquery",graph);
	runCommand("/nodequery "+"( ( Data1 LEVEN 30 ) <= 1 )",graph);
	runCommand("/nodequery "+"( ( Data1 < 30 ) )",graph);
	runCommand("/printresults",graph); */
	
	/*System.out.println("Initializing chain query:...");
	graph.printNetwork(graph.runNodeQuery("( ( Data1 Leven 30 ) <= 1 )"));	//get all nodes with data1 greater than 30  */
	/*System.out.println("Running followup query:...");
	graph.printNetwork(graph.runNodeQuery("( ( Data1 < 30 ) )"));	//get all nodes with data1 less than 30  */
	
	//System.out.println("running query 1:...");
	//graph.printNetwork(graph.runConnectionQuery(graph.getNodeArrayList(),"( leftname == Name1 )"));	//get all nodes outbound from name1  */
	//System.out.println("running query 2:...");
	//graph.printNetwork(graph.runConnectionQuery(graph.getNodeArrayList(),"( rightname == Name1 )")); //get all nodes named Name1 that have a connection to them(will essentially just return Name1)
	//System.out.println("running query 3:...");
	//graph.printNetwork(graph.runConnectionQuery(graph.getNodeArrayList(),"( rightname == leftname )")); //get all nodes with a recursive relationship
	/*System.out.println("running query 4:...");
	graph.printNetwork(graph.runConnectionQuery(graph.getNodeArrayList(),"( rightname == leftname ) || ( ( leftname == Name6 ) && ( traffic < 12 ) )")); //get all nodes with a recursive relationship, and all nodes outbound from 6
	System.out.println("running query 7:...");
	graph.printNetwork(graph.runConnectionQuery(graph.getNodeArrayList(),"( traffic > 12 )")); //get all nodes with a recursive relationship, and all nodes outbound from 6 */
	
	
	/*System.out.println("running query 6:...");
	graph.printNetwork(graph.runConnectionQuery(graph.runConnectionQuery(graph.getNodeArrayList(),"verb == d"),"traffic == 2"));
	
	System.out.println("running query 1:...");
	//graph.printNetwork(graph.runConnectionQuery(graph.getNodeArrayList(),"( leftname == Name1 )"));	//get all nodes outbound from name1  */
	
	
	/*
	if(1 == 1)
	{
		ArrayList<Node> test = graph.runConnectionQuery(graph.getNodeArrayList(),"verb == d");
		test = graph.runConnectionQuery(test,"traffic == 2");
		graph.printNetwork(test);
	}
	//runCommand("/printnetwork",graph);
	//runCommand("/removenode Name8",graph);
	//runCommand("/printnetwork",graph); */
	
	//addTraffic Name1 Name2 traffic verb
	
	
	
	/*Node output = graph.getNode("Name1");
	System.out.println(output.getNodeDataValue("Data1"));
	System.out.println(output.getNodeDataValue("Data45"));*/
	
	/*System.out.println("Performing a random walk....");
	Node output = graph.getNode("the");
	for (int i = 0; i < 100; i++)
		{
		if(output != null)
			{
			System.out.print(output.getName()+" ");
			output = output.getWeightedNeighbor();
			}
		}*/
		
	/*graph.removeNode("Name3");
	graph.getNode("Name4").addTraffic("Name6",10,"d");
	graph.printNetwork();
	graph.getNode("Name4").addTrafficConnection("Name5",53,"test");
	graph.getNode("Name4").addTrafficConnection("Name6",-15,"othertest");
	Node newnode = new Node("Name3",graph);
	graph.addNode("Name3",newnode);
	graph.getNode("Name4").addTrafficConnection("Name3",-15,"othertest");
	graph.getNode("Name4").addTrafficConnection("Name6",-15,"othertest");*/
	//System.out.println(graph.getNode("Name4").nodeverbSearch(graph.getNode("Name6"), "othertest").getVerb());
	//System.out.println(graph.getNode("Name4").nodeverbSearch(graph.getNode("Name6"), "d").getVerb());
	//System.out.println(graph.getNode("Name4").nodeverbSearch(graph.getNode("Name5"), "test").getVerb());
	//graph.printNetwork();
	/*Node output = graph.getNode("Name1");
	output.addTraffic(graph.getNode("Name2"),10);	//add traffic from node 8 to node 2 by 10
	//print network data
	graph.printNetwork(); */
		
	//remove first node from network
	//output = graph.getNode("Name1");
	//graph.removeNode(output);
	//graph.printNetwork();
	

		
	}


	

	
}
