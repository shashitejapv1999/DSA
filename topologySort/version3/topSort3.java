import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class topSort3 {

    public static void main(String args[]) throws FileNotFoundException {
 
        String inputPath= args[0];
        File file = new File(inputPath);
        Scanner sc = new Scanner(file);
        int[][] adjacencyMatrix= new int[0][0];
        int count=1;
        System.out.println("Topological Orders:");
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contains("** G")){
                int beginIndex = line.lastIndexOf(" ");
                int endIndex = line.indexOf("}");
                int graphSize= Integer.valueOf(line.substring(beginIndex, endIndex).trim())+1;
                adjacencyMatrix = new int[graphSize][graphSize];
            }
            if(line.contains("(") && !line.contains("E") ){
                int beginIndex= line.indexOf("(");
                int middleIndex= line.indexOf(",");
                int endIndex= line.indexOf(")");
                int startVertex= Integer.valueOf(line.substring(beginIndex+1, middleIndex).trim());
                int endVertex= Integer.valueOf(line.substring(middleIndex+1, endIndex).trim());
                adjacencyMatrix[startVertex][endVertex]=1;
            }
            if(line.contains("---")){
                Queue<Integer> result= new LinkedList<>();
                calculateTopologyOrder cto = new calculateTopologyOrder();
                List<Integer> vertices = new ArrayList<>();
                for(int i=0;i<adjacencyMatrix.length;i++)
                    vertices.add(i);
                cto.topologyAlgorithm(adjacencyMatrix,result,vertices);
                System.out.print("G"+ count++ +": ");
                if(result.isEmpty())
                    System.out.print(" No in-degree 0 vertex; not an acyclic graph.");
                result.stream().forEach(s -> System.out.print(" "+s));
                if(result.size()<adjacencyMatrix.length && result.size()!=0)
                    System.out.print(" -> no more in-degree 0 vertex; not an acyclic graph.");
                System.out.println("");
            }
        }      
    sc.close();
    }

}
