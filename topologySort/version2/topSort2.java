import java.io.*;

public class topSort2{

    public static void main(String[]args) throws FileNotFoundException,IOException{
        System.out.println("Topological Orders:");
        String grpahsPath = args[0];
        int count=1;
        BufferedReader reader = new BufferedReader(new FileReader(grpahsPath));
        int matrixSize=0;
        String line="";
        int[][] adjacencymatrix= new int[0][0];
        while((line = reader.readLine())!=null){
            if(line.contains("V")){
                matrixSize= Integer.parseInt(line.substring(line.lastIndexOf(" "),line.indexOf("}")).trim())+1; 
                adjacencymatrix =new int[matrixSize][matrixSize];
            }
            if(line.contains("(") && !line.contains("E")){
                int row = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")).trim());
                int column=Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")).trim());
                adjacencymatrix[row][column]=1;
            }
            if(line.contains("----------------")){
                calculateTopSort topSort = new calculateTopSort();
                System.out.println(topSort.calculate(adjacencymatrix,count));
        }
    }
    reader.close();
    }

}