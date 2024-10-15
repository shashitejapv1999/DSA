import java.util.*;

public class calculateTopologyOrder{

public Queue<Integer> topologyAlgorithm(int[][] adjacencyMatrix,Queue<Integer>result,List<Integer> vertices){
    for(int i=0;i<adjacencyMatrix.length;i++){
        if(!result.contains(i) && !findInDegree(adjacencyMatrix,i)){
            result.add(i);
            updateAdjacencyMatrix(adjacencyMatrix,i); }
    for(int j=0;j<adjacencyMatrix.length;j++){
        if(!result.contains(j) && !findInDegree(adjacencyMatrix, j)){
            result.add(j);
            updateAdjacencyMatrix(adjacencyMatrix, j);
        }    }
    }
    return result;
}

public boolean findInDegree(int[][] adjacencyMatrix,int vertex){
    for(int i=0;i<adjacencyMatrix.length;i++)
        if(adjacencyMatrix[i][vertex]==1)
            return true;
    return false;
}

public void updateAdjacencyMatrix(int[][] adjacencyMatrix,int vertex){
    for(int i=0;i<adjacencyMatrix.length;i++)
        if(adjacencyMatrix[vertex][i]==1)
            adjacencyMatrix[vertex][i]=0;
}

}