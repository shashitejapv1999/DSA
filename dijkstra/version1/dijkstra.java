/*
 * name - P V Shashi Teja
 * I pledge that all the code is written by me and i did not copy from anyone
 * i just followed the dijkstra algorithm which was taught in class
 * and as a copyright,. no one is allowed to copy my code without my permission. 
*/

import java.io.*;
import java.util.*;

//this is the main class named dijkstra, which contains multiple methods
public class dijkstra{

    //main driver code (which takes input file from the commamndline arguments)
    public static void main(String args[]) throws FileNotFoundException,IOException{
        String path = args[0];
        //path= "wdGraphs.txt";
        dijkstra dj = new dijkstra();
             dj.parseFile(path);
    }

    /*this is the parse method which has void return type it just parses the given
    input file and creates the graph data and calls the calculateDijkstra method  
    */
    public void parseFile(String inputPath) throws FileNotFoundException,IOException{

        FileReader fr = new FileReader(inputPath);
        BufferedReader br = new BufferedReader(fr);
    
        String st ="";
        int graphSize=0,count=1;
        
        List<Integer> validNodes= new ArrayList<>();
        double[] distanceArray = new double[graphSize];
        Map<Integer,ArrayList<Node>> graph = new HashMap<>();
        System.out.println("Shortest Paths from vertex 0 to vertex n-1 in "+inputPath+", |V|=n");
        System.out.println("");
        while((st =br.readLine()) != null){

            if(st.contains("**")){
                graphSize= Integer.parseInt(st.substring(st.indexOf("=")+1,st.indexOf(",")));
                distanceArray = new double[graphSize];
                Arrays.fill(distanceArray, Double.MAX_VALUE);

            }
            if(st.contains("(") && !st.contains("u")){
                int startVertex= Integer.parseInt(st.substring(st.indexOf("(")+1,st.indexOf(",")).trim());
                int endVertex= Integer.parseInt(st.substring(st.indexOf(",")+1,st.lastIndexOf(",")).trim());
                double distance =  Double.parseDouble(st.substring(st.lastIndexOf(",")+1,st.indexOf(")")).trim());
                if(!graph.containsKey(startVertex)){
                    graph.put(startVertex,new ArrayList<Node>());
                    validNodes.add(startVertex);
                }
                graph.get(startVertex).add(new Node(endVertex, distance));
            }
            if(st.contains("---")){  
                    System.out.println("G"+count++ +"'s shortest path from 0 to "+(graphSize-1)+":");
                    calculateDijkstra(graph,graphSize,distanceArray,graphSize-1,validNodes);       
                    System.out.println("");   
                    graph.clear();
                    validNodes.clear();
                }
        }
        br.close();
    }

    // this is the calculateDijkstra method which caluclates the shortest path
    // from source to end vertex using dijktsra algorithm
    public void calculateDijkstra(Map<Integer,ArrayList<Node>> graph, int graphSize,double [] distanceArray,int end,List<Integer> validNodes){
            
        PriorityQueue<Integer> pq = new PriorityQueue<>((node1, node2) -> distanceArray[node1] < distanceArray[node2] ? -1 : +1);
        
        int bestPath[] = new int [graphSize];
        boolean [] markedNode= new boolean[graphSize];
        Arrays.fill(markedNode,false);
        Arrays.fill(bestPath,Integer.MAX_VALUE);
    
            pq.add(0);
            distanceArray[0]=0; 
            while(!pq.isEmpty()){
                int check = pq.poll();
                if(markedNode[check] || !validNodes.contains(check))
                    continue;

                markedNode[check] = true;
                for( Node i : graph.get(check)){
                    Double currentDistance = distanceArray[i.endVertex];
                    Double newDistance = distanceArray[check] + i.distance ;
                    if(currentDistance > newDistance){
                        distanceArray[i.endVertex]= newDistance;
                        bestPath[i.endVertex]= check;
                    }
                    pq.add(i.endVertex);
                }
            }
            if(distanceArray[end] == Double.MAX_VALUE){
                System.out.println("      *** There is no path.");
                return;
            }
            
            int toStart=graphSize-1;
            ArrayList<Integer> ar = new ArrayList<>();
            ar.add(graphSize-1);
            while(toStart != 0){
                ar.add(bestPath[toStart]);
                toStart= bestPath[toStart];   
            }
                 for(int i= ar.size()-1;i>0;i--){
                    double dist=0;
                    for(Node j: graph.get(ar.get(i))){
                        if(j.endVertex== ar.get(i-1)){
                            dist= j.distance;
                        }
                    }
            System.out.printf("      (%3d, %3d, %6.3f) --> %7.3f%n",ar.get(i),ar.get(i-1),dist,distanceArray[ar.get(i-1)]);
                }
    }

    //this class has been created to store the end vertex and distance from the given vertex
    // I am using a hashmap and NODE as value to store the data
class Node{
    int endVertex;
    double distance;
    public Node(int endVertex,double distance){
        this.endVertex = endVertex;
        this.distance = distance;
        }
    }
}