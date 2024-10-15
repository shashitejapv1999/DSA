/*
 * full name - P V SHASHI TEJA
 * Student id- 807540094
 * A pledge of honesty that I did not copy/modify from other’s codes.
 * I declaration that no one has any copyrights to modify or use my code.
 * 
*/
import java.util.*;
import java.io.*;

public class topSort{

    
    //the main method which reads the input file and stores the data accordingly
    // In my main method I am calling the findTopSort() function to perform the calculation
    public static void main(String[]args) throws FileNotFoundException,IOException{
        String path = args[0];
        int graphSize=0;
        int globalCounter=1;
        int[][] edges=new int[0][0];
        FileReader fr= new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String st;
        System.out.println("Topological Orders:");
        while((st = br.readLine())!=null){
            if(st.contains("}") && !st.contains(" }")){
                graphSize= Integer.parseInt(st.substring(st.lastIndexOf(" "),st.indexOf("}")).trim())+1; 
                edges=new int[graphSize][graphSize];
                        }
            if(st.contains("(") && !st.contains("u")){
                edges[Integer.parseInt(st.substring(st.indexOf(",")-2, st.indexOf(",")).trim())][Integer.parseInt(st.substring(st.indexOf(",")+1,st.indexOf(")")).trim())]=1;
            }
            if(st.contains("---")){
            findTopSort(edges,graphSize,globalCounter++);
        }
    }
        br.close();
    }

    // this function performs the logic to find the topological order of a given graph data
    public static void findTopSort(int[][] edges,int graphSize,int globalCounter){
            List<Integer> vertices= new ArrayList<>();
            String answer="";
            for(int j=0;j<graphSize;j++){
                vertices.add(j);
            }
        while(completeSearch(edges,vertices)){

        for(int i=0;i<graphSize;i++){
            if(inDegree(edges, i) && vertices.contains(Integer.valueOf(i))){
                vertices.remove(Integer.valueOf(i));
                for(int k=0;k<graphSize;k++){
                    if(edges[i][k]==1)
                        edges[i][k]=0;
                }
                answer+=" "+i;
            }
        }
        if(vertices.isEmpty()){
            break;
        }
    }
    
    if(!vertices.isEmpty()){
        if(vertices.size()==graphSize){
            System.out.println("G"+globalCounter+":  No in-degree 0 vertex; not an acyclic graph.");
        }else{
        System.out.println("G"+globalCounter+": "+answer+" -> no more in-degree 0 vertex; not an acyclic graph.");    
    }
}else{
        System.out.println("G"+globalCounter+": "+answer);
    }
}
    // this is a helper method which is used to check the in-degree of a given vertex 
    public static boolean inDegree(int[][] edges,int vertex){
        for(int i=0;i<edges.length;i++){
            if(edges[i][vertex]==1)
                return false;
        }
        return true;
    }

    // this is a helper method which is used to check the in-degree of all the vertices   
    public static boolean completeSearch(int[][] edges,List<Integer> vertices){
        for(int i=0;i<edges.length;i++){
            if(inDegree(edges, i) && vertices.contains(Integer.valueOf(i))){
                return true;
            }
        }
        return false;
    }

}