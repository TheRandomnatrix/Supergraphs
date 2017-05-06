import java.util.*;

public class SetExample {

    public static void main(String args[]) {

        // We create a new, empty set
        Set mySet1 = new HashSet();
        // We add a few elements
        mySet1.add("A");
        mySet1.add("C");
        mySet1.add("A");
        mySet1.add("B");
        // Print the elements of the Set
        System.out.println("mySet1: " + mySet1);

        // Create a list and add some elements
        List list = new ArrayList();
        list.add("A");
        list.add("C");
        list.add("A");
        list.add("A");
        list.add("B");
        list.add("C");
        // Now create the set using the appropriate constructor
        Set mySet2 = new HashSet(list);
        // Print the elements of the list an the the set
        System.out.println("list: " + list);
        System.out.println("mySet2: " + mySet2);

        // Compare the two sets
        System.out.println("MySet1 matches mySet2: " + mySet1.equals(mySet2));

        // Now we will remove one element from mySet2 and compare again
        mySet2.remove("A");
        System.out.println("mySet2: " + mySet2);
        System.out.println("MySet1 matches mySet2: " + mySet1.equals(mySet2));

        // Lets check if our sets contain all the elements of the list
        System.out.println("MySet1 contains all the elements: " + mySet1.containsAll(list));
        System.out.println("MySet2 contains all the elements: " + mySet2.containsAll(list));

    }
}