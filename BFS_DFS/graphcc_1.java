import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class graphcc{

	public static void bfsAlgorithm(ArrayList<ArrayList<Integer>> adjList, Boolean[] discovered, int source, ArrayList<Integer> result) {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(source);
    discovered[source] = true;

    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        for (int neighbor : adjList.get(node)) {
            if (!discovered[neighbor]) {
                discovered[neighbor] = true;
                queue.add(neighbor);
            }
        }
    }
}



    private static void dfsAlgorithm(ArrayList<ArrayList<Integer>> adjList, Boolean[] explored, int source,
            int totalVertices, ArrayList<Integer> result) {
                explored[source]= true;
                result.add(source);
                for(int i=0;i<adjList.get(source).size();i++){
                    if(!explored[adjList.get(source).get(i)]){
                        dfsAlgorithm(adjList,explored,adjList.get(source).get(i),totalVertices,result);
                    }
                }
    }
	
	public static void dfsAlgorithm(ArrayList<ArrayList<Integer>> adjList, Boolean[] explored, int source, ArrayList<Integer> result) {
    Stack<Integer> stack = new Stack<>();
    stack.push(source);

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (!explored[node]) {
            explored[node] = true;
            result.add(node);
            for (int neighbor : adjList.get(node)) {
                if (!explored[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }
    }
}


    public static void main(String[]args) throws FileNotFoundException{
        String fileName = args[0];
        File file = new File(args[0]);
        Scanner sc = new Scanner(file);
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        int totalVertices = 0,tracker=1;
        while(sc.hasNextLine()){
            String reader = sc.nextLine();
            if(reader.contains("|V|")){
                totalVertices = Integer.valueOf(reader.substring(reader.indexOf("=")+1, reader.lastIndexOf(" ")).trim());
                adjList = new ArrayList<>(totalVertices);
            for (int i = 0; i < totalVertices; i++)
                adjList.add(new ArrayList<Integer>());
            }
            if(reader.contains("(") && !reader.contains("E")){
                reader = reader.replace("(", "").replace(")", "").replace("}", "");
                String[] readValues = reader.split(",");
                adjList.get(Integer.valueOf(readValues[0].trim())).add(Integer.valueOf(readValues[1].trim()));
                adjList.get(Integer.valueOf(readValues[1].trim())).add(Integer.valueOf(readValues[0].trim()));
            }
            if(reader.contains("-------------")){
                //the abelow is for BFS
                Boolean[] discovered = new Boolean[totalVertices];
                ArrayList<Integer> result = new ArrayList<>();
                System.out.println("** G"+ tracker +"'s connected components:");
                for(int i=0;i<totalVertices;i++)
                    discovered[i]= false;
                checkDiscoveryBFS(adjList,discovered,totalVertices,result);
                tracker++;

                //the below snippet is for DFS 
                Boolean[] explored = new Boolean[totalVertices];
                for(int i=0;i<totalVertices;i++)
                explored[i]= false;
                result.clear();
                checkDiscoveryDFS(adjList,explored,totalVertices,result);


            }

        }
        sc.close();
    }
  
    public static void checkDiscoveryBFS(ArrayList<ArrayList<Integer>> adjList,Boolean[] discovered,int totalVertices,ArrayList<Integer> result){
            System.out.println("    Breadth First Search:");
        for(int i=0;i<discovered.length;i++){
            if(!discovered[i]){
                result.clear();
                bfsAlgorithm(adjList,discovered,i,result);
                String c = result.stream().map(Object::toString).collect(Collectors.joining(" "));
                System.out.println("        "+c);
            }
        }
    }

    public static void checkDiscoveryDFS(ArrayList<ArrayList<Integer>> adjList,Boolean[] explored,int totalVertices,ArrayList<Integer> result){
        System.out.println("    Depth First Search:");
    for(int i=0;i<explored.length;i++){
        if(!explored[i]){
            result.clear();
            dfsAlgorithm(adjList,explored,i,totalVertices,result);
            String c = result.stream().map(Object::toString).collect(Collectors.joining(" "));
            System.out.println("        "+c);
        }
    }
}
}

