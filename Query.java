import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;

public class Query
	{
	private ArrayList<Connection> ConnectionList;
	private String query;

public static int Levenshtein(String a, String b) //returns the Levenshtein distance between two strings
		{
		//NOT MY CODE.
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) 
			{
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
		
public static boolean isNumeric(String s) //Checks to see if a string contains all digits
		{
		/*if(!s.matches("[+-]?\\d+"))
			System.out.println(s);*/
		
		return s.matches("-?\\d+(\\.\\d+)?"); //updated to work with negatives and floats
		//return s.matches("[+-]?\\d+");
		//return s.matches("-?\\d+"); //test
		//return s.matches("\\d+"); //only works for ints
		}
public static int countSubstring(String str, String findStr)
	{
    int lastIndex = 0;
    int count = 0;

    while ((lastIndex = str.indexOf(findStr, lastIndex)) != -1) 
		{
        count++;
        lastIndex += findStr.length() - 1;
		}
	return count;
	}	

public static String getSmallestString(ArrayList<String> params) //returns smallest string from list of strings
	{
	String smallest = "NULLRETURN";
	if (params.size() > 0)
		{
		smallest = params.get(0);
		for (int i = 1; i < params.size(); i++)
			{
			if (smallest.length() > params.get(i).length())
				{
				smallest =  params.get(i);
				}
			}
		}
	return smallest;
	}
	
public static String getLargestString(ArrayList<String> params) //returns largest string from list of strings
	{
	String largest = "NULLRETURN";
	if (params.size() > 0)
		{
		largest = params.get(0);
		for (int i = 1; i < params.size(); i++)
			{
			if (largest.length() < params.get(i).length())
				{
				largest =  params.get(i);
				}
			}
		}
	return largest;
	}
	
public static String evaluate(ArrayList<String> params, String operand) //evalaute an expression containing an operator and at most 2 parameters 
	{
	//System.out.println("params: "+params + " "+operand);
	if(operand.equals("COUNT")) //Levenshtein distance of left and right strings
		{
		return String.valueOf(params.size());
		}
	if(operand.equals("MAX")) //Levenshtein distance of left and right strings
		{
		if (params.size() > 0)
			{
			boolean allNumeric = true;
			for (int i = 0; i < params.size(); i++)
				{
				if (!isNumeric(params.get(i)))
					allNumeric = false;
				}
			if(allNumeric)
				{
				int max = Integer.parseInt(params.get(0));
				for (int i = 0; i < params.size(); i++)
					{
					if (max < Integer.parseInt(params.get(i)))
						{
						max = Integer.parseInt(params.get(i));
						}
					}
				return String.valueOf(max);
				}
			}
		}
	if(operand.equals("MIN")) //Levenshtein distance of left and right strings
		{
		if (params.size() > 0)
			{
			boolean allNumeric = true;
			for (int i = 0; i < params.size(); i++)
				{
				if (!isNumeric(params.get(i)))
					allNumeric = false;
				}
			if(allNumeric)
				{
				int min = Integer.parseInt(params.get(0));
				for (int i = 0; i < params.size(); i++)
					{
					if (min > Integer.parseInt(params.get(i)))
						{
						min = Integer.parseInt(params.get(i));
						}
					}
				return String.valueOf(min);
				}
			}
		}
	if(operand.equals("AVG")) //Levenshtein distance of left and right strings
		{
		if (params.size() > 0)
			{
			boolean allNumeric = true;
			for (int i = 0; i < params.size(); i++)
				{
				if (!isNumeric(params.get(i)))
					allNumeric = false;
				}
			if(allNumeric)
				{
				int sum = Integer.parseInt(params.get(0));
				for (int i = 0; i < params.size(); i++)
					{
					sum += Integer.parseInt(params.get(i));
					}
				return String.valueOf((int) (sum / params.size()));
				}
			}
		}
	if(operand.equals("LARGEST")) //Levenshtein distance of left and right strings
		{
		return getLargestString(params);
		}
	if(operand.equals("SMALLEST")) //Levenshtein distance of left and right strings
		{
		return getSmallestString(params);
		}
	if(operand.equals("STRREPLACE")) //Levenshtein distance of left and right strings
		{
		if (params.size() == 3)
			{
			String s1 = params.get(0);
			String s2 = params.get(1);
			String s3 = params.get(2);
			String replacedStr = s1.replace(s2,s3);
			return replacedStr;
			}
		}
	return "NULLRETURN";
	}
		
public static String evaluate(String leftString, String operand, String rightString) //evalaute an expression containing an operator and at most 2 parameters 
		{
			if (leftString.equals("NULLRETURN") || rightString.equals("NULLRETURN")) //invalid params
				{
				return "NULLRETURN";
				}
			
			if(operand.equals("LEVEN")) //Levenshtein distance of left and right strings
			{
				return String.valueOf(Levenshtein(leftString,rightString)); //answer converted to string
			}

			if(operand.equals("&&"))	//returns "true" if both left and right are "true"
			{
				Boolean leftbool = false;
				Boolean rightbool = false;
				if (leftString.equals("true") || leftString.equals("1"))
				{
					leftbool = true;
				}
				if (rightString.equals("true") || rightString.equals("1"))
				{
					rightbool = true;
				}
				if (leftbool && rightbool)
					{
					return "true";
					}
				else
					{
					return "false";
					}
			}
			if(operand.equals("||")) //returns "true" if either left and right are "true"
			{
				Boolean leftbool = false;
				Boolean rightbool = false;
				if (leftString.equals("true") || leftString.equals("1"))
				{
					leftbool = true;
				}
				if (rightString.equals("true") || rightString.equals("1"))
				{
					rightbool= true;
				}
				if (leftbool || rightbool)
					{
					return "true";
					}
				else
					{
					return "false";
					}
			}
			if(operand.equals("NOT"))
			{
				if (leftString.equals("true") || leftString.equals("1"))
				{
					return "false";
				}
				if (leftString.equals("false") || leftString.equals("0"))
				{
					return "true";
				}
			}
			if(operand.equals("ABS")) //returns absolute value of left
				{
				if (isNumeric(leftString)) //both numerics
					{
					//System.out.println("ABS: "+leftString);
					int leftnum = Integer.parseInt(leftString);
					return String.valueOf(Math.abs(leftnum));
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("ISNUMERIC")) //returns absolute value of left
				{
				if (isNumeric(leftString)) //check numerics
					{
					return "true";
					}
					else
					{
					return "false";
					}
				}
			if(operand.equals("SQRT"))	//returns square root of left, as an int
				{
				if (isNumeric(leftString)) //numerics
					{
					int leftnum = Integer.parseInt(leftString);
					return String.valueOf((int) (Math.sqrt(leftnum)));
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("RANDOMVAL"))	//returns square root of left, as an int
				{
				if (isNumeric(leftString)) //numerics
					{
					int leftnum = Integer.parseInt(leftString);
					return String.valueOf((int) (Math.random() * leftnum));
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			
			if(operand.equals("LENGTH")) //Returns length of given string
			{
				return String.valueOf(leftString.length()); //answer converted to string
			}
			if(operand.equals("UPPER")) //Returns length of given string
			{
				return leftString.toUpperCase(); //answer converted to string
			}
			if(operand.equals("LOWER")) //Returns length of given string
			{
				return leftString.toLowerCase(); //answer converted to string
			}
			if(operand.equals("+"))	//returns sum of left and right
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return String.valueOf(leftnum + rightnum);
					}
				else
					{
					return leftString+rightString;		//one of them is not a numeric. Treat it as concatenation
					}
				}
			if(operand.equals("-"))	//returns left minus right
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return String.valueOf(leftnum - rightnum);
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("*")) //returns left * right
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return String.valueOf(leftnum * rightnum);
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("/"))	//returns left / right, as an int
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return String.valueOf((int)(leftnum / rightnum));
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("%")) //returns left modulus right
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return String.valueOf(leftnum % rightnum);
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("LOGBASE")) 	//returns left to the logbase of right
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return  String.valueOf((int) (Math.log(leftnum) / Math.log(rightnum)));
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("POWEROF"))	//returns left ^ right
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					return  String.valueOf((int) Math.pow(leftnum, rightnum));
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("=="))	//checks if two numbers are the same. If one is not a number, compare them as strings
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					if( leftnum == rightnum)
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				else		//one of them is not a numeric, treat them as strings
					{
					if (leftString.equals(rightString))
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				}
			if(operand.equals("!="))
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					if( leftnum != rightnum)
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				else
					{
					if (leftString.equals(rightString))
						{
						return "false";
						}
					else
						{
						return "true";
						}
					}
				}
			if(operand.equals(">"))
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					if( leftnum > rightnum)
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("<"))
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					if( leftnum < rightnum)
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals(">="))
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					if( leftnum >= rightnum)
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("<="))
				{
				if (isNumeric(leftString) && isNumeric(rightString)) //both numerics
					{
					int leftnum = Integer.parseInt(leftString);
					int rightnum = Integer.parseInt(rightString);
					if( leftnum <= rightnum)
						{
						return "true";
						}
					else
						{
						return "false";
						}
					}
				else
					{
					return "NULLRETURN"; //not both numerics
					}
				}
			if(operand.equals("STRCOUNT"))
				{
				return String.valueOf(countSubstring(leftString,rightString));
				}
			
		return "NULLRETURN"; //fail state returns empty string
		}
		
public static String parseString(String inputString) //Evaluates an infix expression and returns a string containing the answer
		{
		/* 
		Note that all parameters and operations are space seperated
		This algorithm reads left to right evaluating an infix expression. It maintains the height(parenthesis depth) of each parameter.
		"(" increases height and ")" decreases height.
		Parameters and operations are maintained as a list behaving like a stack(mainly for easier random access)
		Binary operations are evaluated when a ) or a parameter is encountered, and the height of the left and right parameters are the same
		Unary functions(ABS()/SQRT()) are evaluated when a ")"  is encountered
		N-ary functions(things like max()/min()) are evaluated when a ")"  is encountered
			
		If something is not an operation then it is a parameter
		This method will return true if the expression evaluates to true with only 1 remaining value in the "stack", otherwise returns false
		Can be modified to return the result as a string or arraylist<string>
		*/
			
		//BEWARE OF DUPLICATE BINARY OPERATION EVALUATION CODE AT TOP AND BOTTOM OF METHOD
		String output = ""; //output string
		String[] inputs = inputString.split(" "); //break expression into a list of parameters and operands
		ArrayList<String> 	params = new ArrayList<String>(); //list of parameters. Behaves like a stack
		ArrayList<Integer> paramHeights = new ArrayList<Integer>(); //list of the heights of parameters. This is to ensure parenthesis work as intended. behaves like stack
		ArrayList<String> operation = new ArrayList<String>(); //list of operations. behaves like a stack
		ArrayList<Integer> operationtype = new ArrayList<Integer>(); //list of type of operation(unary,binary, n-ary). behaves like a stack.
		//ArrayList<Integer> operationHeights = new ArrayList<Integer>(); //list of number of parameters per operation. behaves like a stack
		int currentheight = 0;
			
		for (int i = 0; i < inputs.length; i++) //loop through the list of parameters and operations
			{
			int wasspecial = 0; //value for determing if read in input was an operation. Defaults to parameter otherwise.
			
			if (inputs[i].equals(")")) //closing parenthesis means the possibility of two "parameters" sharing the same height, and thus being open for evaluation
				{
				wasspecial = 1;		// ) means it isn't a parameter
					
				//handle the N-ary functions
				if((paramHeights.size() > 1) && (operation.size() > 0)) //whenever there are two parameters and an operation
				{
				if(operationtype.get(operationtype.size() - 1) == 3)
					{
						
					ArrayList<String> tempparams = new ArrayList<String>();
					tempparams.add(params.get(params.size() - 1));
					int tempheight = paramHeights.get(paramHeights.size() - 1);
					for (int k = (params.size() - 2); k >= 0; k--)
						{
						if (paramHeights.get(k) == tempheight)
							{
							tempparams.add(params.get(k));
							}
							else
								break;
						}
					String operand = operation.get(operation.size() - 1); //get topmost operand
					Collections.reverse(tempparams); //reverse the list
					String answer = evaluate(tempparams,operand); //evaulate the sub expression
					//System.out.println("	Answer: "+answer);
					operation.remove(operation.size() - 1); //remove the topmost operand
					operationtype.remove(operationtype.size() - 1);
						
					//System.out.println("	Answer1: "+params);
					//System.out.println("	Answer2: "+tempparams);
					for (int k = 0; k < tempparams.size(); k++)
						{
						paramHeights.remove(paramHeights.size() - 1); //remove top most parameter height
						params.remove(params.size() - 1); //remove top most parameter
						}
						
					params.add(answer); //set the second highest paramter to the evaluation of the expression
					paramHeights.add(currentheight); //height of parameter is current height
					}
				}		
					
				currentheight -=1;		//decrement the height
				
				//keep the topmost parameter updated with the current height
				if(paramHeights.size() > 0) 
					{
					int index = paramHeights.size()-1;
					paramHeights.set(index,currentheight);
					}
				//unary operations	
				if((paramHeights.size() >= 1) && (operation.size() > 0)) //check for unary operation requirements. Must have at least one param
					{	
					if(operationtype.get(operationtype.size() - 1) == 1) //check for unary operator at top of stack
						{
							String rightparam = params.get(params.size() - 1); //get topmost parameter on stack
							String operand = operation.get(operation.size() - 1); //get topmost operand
							String answer = evaluate(rightparam,operand,""); //evaulate the sub expression
							
							operation.remove(operation.size() - 1); //remove the topmost operand
							operationtype.remove(operationtype.size() - 1); //remove its type
							//paramHeights.remove(paramHeights.size() - 1); //remove top most parameter height
							params.set(params.size() - 1,answer); //set the highest paramter to the evaluation of the expression
						}
					}
				//binary operations	
				if((paramHeights.size() > 1) && (operation.size() > 0)) //Check for binary operation requirements. Must have at least two params
					{	
					if(operationtype.get(operationtype.size() - 1) == 2) //check for binary operator at top of stack
						{
						int paramheight1 = paramHeights.get(paramHeights.size() - 2); //get height of left parameter
						int paramheight2 = paramHeights.get(paramHeights.size() - 1);	//get height of right parameter
						if (paramheight1 == paramheight2) //if last two params have same heights, this means they can be evaulated
							{
							String leftparam = params.get(params.size() - 2);  //get left parameter
							String rightparam = params.get(params.size() - 1); //get right parameter
							String operand = operation.get(operation.size() - 1); //get operand
							String answer = evaluate(leftparam,operand,rightparam); //evaulate the sub expression
							
							operation.remove(operation.size() - 1); //remove the topmost operand
							operationtype.remove(operationtype.size() - 1);
							paramHeights.remove(paramHeights.size() - 1); //remove top most parameter height
							params.remove(params.size() - 1); //remove top most parameter
							params.set(params.size() - 1,answer); //set the second highest paramter to the evaluation of the expression
							}
						}
					}
				}
			//increase height for (	
			if (inputs[i].equals("("))
				{
				currentheight +=1;
				wasspecial = 1;
				}	
			
			//check for operations and functions
			String tester = inputs[i];
			switch (tester) 		//Unary operators
				{
				case "ABS":
				case "SQRT":
				case "NOT":
				case "ISNUMERIC":
				case "LENGTH":
				case "UPPER":
				case "LOWER":
					{	
					//System.out.print(inputs[i]);
					operation.add(inputs[i]);
					operationtype.add(1);
					//operationHeights.add(currentheight);
					wasspecial = 1;
					break;
					}
				default:
				} 	//*/
			switch (tester) //Binary operators
				{
				case "&&":
				case "||":
				case "+":
				case "-":
				case "*":
				case "/":
				case "%":
				case "LOGBASE":
				case "POWEROF":
				case "==":
				case "!=":
				case ">":
				case "<":
				case "<=":
				case ">=":
				case "LEVEN":
				case "STRCOUNT":
					{	
					operation.add(inputs[i]);
					operationtype.add(2);
					//operationHeights.add(currentheight);
					wasspecial = 1;
					break;
					}
				default:
				} 
			switch (tester) 	//N-ary operators
				{
				case "MIN":
				case "MAX":
				case "SMALLEST":
				case "LARGEST":
				case "CHOOSE":
				case "COUNT":
				case "AVG":
				case "STRREPLACE":
				case "CONCAT":
					{	
					//System.out.print(inputs[i]);
					operation.add(inputs[i]);
					operationtype.add(3);
					//operationHeights.add(currentheight);
					wasspecial = 1;
					break;
					}
				default:
				} 
			
			//add parameters to list and evaluate them as necessary
			if (wasspecial == 0)		//was not an operation or parenthesis. Must be a parameter then
				{
				params.add(inputs[i]);	//add the parameter to top of stack
				paramHeights.add(currentheight); //height of parameter is current height
				
				//binary operator
				if((paramHeights.size() > 1) && (operation.size() > 0)) //whenever there are two parameters and an operation
					{
					if(operationtype.get(operationtype.size() - 1) == 2)
						{
						int paramheight1 = paramHeights.get(paramHeights.size() - 2); //get height of left parameter
						int paramheight2 = paramHeights.get(paramHeights.size() - 1);	//get height of right parameter
						if (paramheight1 == paramheight2) //if last two params have same heights, this means they can be evaulated
							{
							String leftparam = params.get(params.size() - 2);  //get left parameter
							String rightparam = params.get(params.size() - 1); //get right parameter
							String operand = operation.get(operation.size() - 1); //get operand
							String answer = evaluate(leftparam,operand,rightparam); //evaulate the sub expression
							
							operation.remove(operation.size() - 1); //remove the topmost operand
							operationtype.remove(operationtype.size() - 1);
							paramHeights.remove(paramHeights.size() - 1); //remove top most parameter height
							params.remove(params.size() - 1); //remove top most parameter
							params.set(params.size() - 1,answer); //set the second highest paramter to the evaluation of the expression
							}
						}
					}					
				}
			}
		
			
		if(params.size() == 1)
		{
		return params.get(0); //return answer
		}			
		return "NULLRETURN"; //could not parse it completely. Return as failed
		
		/*if(params.size() == 1)
		{
			if(params.get(0).equals("true"))
			{
			return true;
			}
		}
		return false;		//returns false if the final result was not true, or there was more than 1 parameter*/
		}
		
	
	}