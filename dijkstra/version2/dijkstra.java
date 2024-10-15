
import java.util.*;
import java.io.*;
import java.text.*;

class Vertex{
    int destination;
    double weight;
    public Vertex(int destination,double weight){
        this.destination=destination;
        this.weight=weight;
    } 
}

public class dijkstra{
    public static void main(String[]args) throws Exception{
        
    File file = new File(args[0]);
    Scanner sc = new Scanner(file);
    int totalVertices=0;
    double infinity=9999999.0;
    double[] weightNode = new double[totalVertices];
    List<List<Vertex>> adjacencyList = new ArrayList<>();
        int graphCounter=1;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contains("V=")){
                adjacencyList = new ArrayList<>();
                totalVertices= Integer.parseInt(line.substring(line.lastIndexOf(".")+1, line.indexOf("}")))+1;
                weightNode = new double[totalVertices];
                for(int i=0;i<weightNode.length;i++){
                    adjacencyList.add(new ArrayList<>());
                    weightNode[i]=infinity;
                }
                sc.nextLine();
            }
            if(line.contains("(")){
                String[] edges= line.split(",");
                int u= Integer.parseInt(edges[0].replace("(", "").trim());
                int v= Integer.parseInt(edges[1].trim());
                double weight= Double.parseDouble(edges[2].replace(")", "").replace("}", "").trim());
                adjacencyList.get(u).add(new Vertex(v, weight));
                }
            if(line.contains("---")){  
                    ArrayList<Integer> nodesWithNoConnection = new ArrayList<>();
                    for(int i=0;i<adjacencyList.size();i++){
                        if(adjacencyList.get(i).size()==0)
                            nodesWithNoConnection.add(i);
                    }
                    System.out.println("G"+graphCounter++ +":'s shortest path from 0 to "+(totalVertices-1)+":");
                    double shortestPath= findShortestPath(adjacencyList,totalVertices,weightNode,nodesWithNoConnection);
                    if(shortestPath == infinity){
                        System.out.println("    *** There is no path.");
                        continue;
                    }
            }
        }
        sc.close();
    }

    public static double findShortestPath(List<List<Vertex>> adjacaencyList, int totalVertices, double[] weightNode,ArrayList<Integer> nodesWithNoConnection){
        Queue<Integer> pQueue = new PriorityQueue<>((vertex1, vertex2) -> weightNode[vertex1] < weightNode[vertex2] ? -1 : +1);
        pQueue.offer(0); // 0 is considered as source
        boolean[] visitedNodes = new boolean[totalVertices];
        weightNode[0]= 0; // distance from source to source node is 0
        int[] storePathData = new int[totalVertices];
        for(int i=0;i<storePathData.length;i++){
            storePathData[i]=-1;
        }
        while(!pQueue.isEmpty()){
            int elementToConsider = pQueue.poll();
            if(visitedNodes[elementToConsider] || nodesWithNoConnection.contains(elementToConsider)){
                continue; // this condition is to skip the vertices which are already visited
            }
            for(Vertex vertex: adjacaencyList.get(elementToConsider)){
                double distanceToSource = weightNode[vertex.destination];
                double distanceFromElement = weightNode[elementToConsider] + vertex.weight;
                if(distanceToSource > distanceFromElement){
                    weightNode[vertex.destination]= distanceFromElement;
                    storePathData[vertex.destination]= elementToConsider;
                }
                    pQueue.offer(vertex.destination);
            }
            visitedNodes[elementToConsider]= true;

        }
        printPath(adjacaencyList,totalVertices,storePathData);
        return weightNode[totalVertices-1];
        
    }

    public static void printPath(List<List<Vertex>> adjacaencyList,int totalVertices, int [] storePathData){
        if(storePathData[totalVertices-1] != -1){
            int traverseNode= totalVertices-1;
            ArrayList<Integer> pathReverse = new ArrayList<>();
            pathReverse.add(traverseNode);
            while(traverseNode != 0){   
                pathReverse.add(storePathData[traverseNode]);
                traverseNode = storePathData[traverseNode];
            }
            double weight=0;
            NumberFormat df = new DecimalFormat("#0.000"); 
            for( int i=pathReverse.size()-1;i>0;i--){
                for(Vertex k: adjacaencyList.get(pathReverse.get(i))){
                    if(k.destination == pathReverse.get(i-1)){
                        weight+= k.weight;
                        System.out.println("    ("+String.format("%3d",pathReverse.get(i))+","+String.format("%3d",pathReverse.get(i-1))+","+df.format(k.weight)+") -->   "+df.format(weight));
                    }
                }
            }
        }
    }
}