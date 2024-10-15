import java.util.*;

public class calculateTopSort{

    public String calculate(int[][] adjacencymatrix,int count){
        List<Integer> vertices= new ArrayList<>();
        String result="";
        for(int j=0;j<adjacencymatrix.length;j++){
            vertices.add(j);
        }
    while(calculateTotalInDegree(adjacencymatrix,vertices)){
    for(int i=0;i<adjacencymatrix.length;i++){
        if(findInDegree(adjacencymatrix, i) && vertices.contains(Integer.valueOf(i))){
            vertices.remove(Integer.valueOf(i));
            updateAdjacencyMatrix(adjacencymatrix,i);
            result+=" "+i;
        }
    }
    if(vertices.isEmpty())
        break;
}
    if(!vertices.isEmpty())
        if(vertices.size()==adjacencymatrix.length)
            return "G"+count+":  No in-degree 0 vertex; not an acyclic graph." ;
        else
            return "G"+count+": "+result+" -> no more in-degree 0 vertex; not an acyclic graph." ;    
    else
        return "G"+count+": "+result ;

}

public boolean calculateTotalInDegree(int[][] adjacencymatrix,List<Integer> vertices){
    for(int i=0;i<adjacencymatrix.length;i++){
        if(findInDegree(adjacencymatrix, i) && vertices.contains(Integer.valueOf(i)))
            return true;
        
    }
    return false;
}
public void updateAdjacencyMatrix(int[][] adjacencymatrix,int vertex){
    for(int k=0;k<adjacencymatrix.length;k++){
        if(adjacencymatrix[vertex][k]==1)
            adjacencymatrix[vertex][k]=0;
    }
}

public boolean findInDegree(int[][] adjacencymatrix,int vertex){
    for(int i=0;i<adjacencymatrix.length;i++){
        if(adjacencymatrix[i][vertex]==1)
            return false;
    }
    return true;
}
}