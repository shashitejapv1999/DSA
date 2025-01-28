/*
 * full name - P V SHASHI TEJA
 * A pledge of honesty that I did not copy/modify from other’s codes.
 * I declaration that no one has any copyrights to modify or use my code.
 *
 */
import java.util.*;

/*
 * This is the class which has all the required logic
 * I have created a constructor which has all the logic in it
 * There are some more methods which are implemented as asked
 */
public class HuffmanCode {
    public static Node rootFinal = null;
    static HashMap<Character,String> hm = new HashMap<>();
    static HashMap<String,Character> hm1 = new HashMap<>();

    public HuffmanCode(char[] a, int[] f){
        // priority queue which sorts the elements based on the frequencies
        PriorityQueue<Node> pq = new PriorityQueue<>((b,c) -> b.frequency < c.frequency ? -1 : 1);
        for(int i=0;i<a.length;i++){
            Node n = new Node(a[i],f[i]);
            pq.add(n);
        }
        //logic to create a right-heavy tree
    while(!pq.isEmpty() && pq.size()>1){
        Node root = new Node('*', 0);
        Node one = pq.poll();        
        Node two = pq.poll();
        //checking the condition between two nodes to decide the right and left node
        if(one.height != two.height){
            if(one.height > two.height){
                root.right = one;
                root.left = two;
            }
            else{
                root.right = two;
                root.left = one;
            }
        }else 
        if(one.numberOfNodes != two.numberOfNodes){
            if(one.numberOfNodes > two.numberOfNodes){
                root.right = one;
                root.left = two;
            }else{
                root.right = two;
                root.left = one;
            }
        }else {
            if (one.asciiValues > two.asciiValues) {
                root.right = one;
                root.left = two;
            } else {
                root.right = two;
                root.left = one;
            }
        }
//updating the root node by calculating the two nodes and adding this new node to queue.
    root.frequency = one.frequency + two.frequency;
    if(one.height < two.height){
        root.height= two.height + 1;
    }else{
        root.height= one.height + 1;
    }
    root.asciiValues = one.asciiValues + two.asciiValues;
    root.numberOfNodes += one.numberOfNodes + two.numberOfNodes + 1 ;
    rootFinal = root;
    pq.add(root);
    }
}
//The below method is used to print the code words and its frequencies and ascii values
public void printCodeWords(){
    String s="";
    System.out.println("Huffman Codes:");
    //I am calling another method where I am passing the root node, which is used
    //for traversing and empty string to print the details
        printCodeAll(rootFinal, s);
    }

    //the below method actually prints the codes in required format
    //It uses recursion to traverse both left and right node
    public static void printCodeAll(Node root,String s) {
            if (root.left == null && root.right == null ) {
              System.out.println(root.value +"["+(int)root.value+"]:" + s+" ("+ root.frequency+")");
              hm.put(root.value,s);
              hm1.put(s,root.value);
              return;
            }
            printCodeAll(root.left, s + "0");
            printCodeAll(root.right, s + "1");
          }

    //This method simply reads each character from the string and
    //in return it appends the code to the result string and prints it.
    public String encode(String text){
        StringBuilder result= new StringBuilder();
        for(int i=0;i<text.length();i++){
            result.append(hm.get(text.charAt(i)));
        }
        return  result.toString();
    }

    //This reads the data charcter wise and store it in a temporary string,
    //once when a match is found it appends the decoded letter and then temporary string starts again
    public String decode(String text){
        StringBuilder result = new StringBuilder();
        String finlaAnswer= "";
        for(int i=0;i<text.length();i++){
            result.append(text.charAt(i));
            if(hm1.containsKey(result.toString())){
                finlaAnswer += hm1.get(result.toString());
                result = new StringBuilder();
            }
        }
        return finlaAnswer;
    }
}

