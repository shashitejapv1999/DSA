import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class graphcc{
    public static void main(String[]args) throws Exception{
        String path = args[0];
		System.out.println("Connected components of graphs in "+path);	
		System.out.println("");
        FileReader fr = new FileReader(path);
        int[][] adjmatrix = new int[0][0];
        BufferedReader br = new BufferedReader(fr);
        String st= "";
        int count=1;
        while((st = br.readLine()) != null){
            if(st.contains("** G")){
                int graphSize= Integer.parseInt(st.substring(st.lastIndexOf(",")+1, st.indexOf("}")));
                adjmatrix= new int[graphSize+1][graphSize+1];
                br.readLine();
            }
            if(st.contains("(") && !st.contains("in")){
                int start = Integer.parseInt(st.substring(st.indexOf("(")+1, st.indexOf(",")).trim());
                int end = Integer.parseInt(st.substring(st.indexOf(",")+1, st.indexOf(")")).trim());
                //System.out.println(start+" "+end);
                adjmatrix[start][end] = 1;
                adjmatrix[end][start] = 1;

            }
            if(st.contains("----")){
                System.out.println("** G"+ count++ +"'s connected components:");
                graphcc gcc = new graphcc();
                ArrayList<Integer> checker = new ArrayList<>();
                ArrayList<Integer> checker1 = new ArrayList<>();

                for(int i=0;i<adjmatrix.length;i++){
                    checker.add(i);
                    checker1.add(i);
                }
                
                System.out.println("     Breadth First Search:");
                gcc.solveBFS(adjmatrix,0,checker);

                System.out.println("     Depth First Search:");
                ArrayList<Integer> visited = new ArrayList<>();
                String res="";
                LinkedHashSet<Integer> hs = new LinkedHashSet<>();
                while(!checker1.isEmpty()){
                    res ="";
					System.out.print("	");
                    gcc.solveDFS(adjmatrix,checker1.get(0),checker1,visited,res,hs);
                    System.out.println("  "+hs.toString().replaceAll(",", "").replace("[", "").replace("]", ""));
                    hs.clear();

                }
                //gcc.solveDFS(adjmatrix, 0, checker1,visited,res);
            }

        }

    }

    public void solveBFS(int[][] adjmatrix,int check,ArrayList<Integer> checker){
        Queue<Integer> pq = new LinkedList<>();
        pq.add(check);
        LinkedHashSet<Integer> hs = new LinkedHashSet<>();
        ArrayList<Integer> visited = new ArrayList<>();
        while(!pq.isEmpty()){
            check = pq.poll();
            visited.add(check);
            checker.remove((Integer)check);
            hs.add(check); //System.out.print(check+" ");
            for(int i=0;i<adjmatrix.length;i++){
                if(adjmatrix[check][i] == 1 && !visited.contains(i)){
                    pq.add(i);
                }
            }
        }
		System.out.print("	");
        System.out.println("  "+hs.toString().replaceAll(",", "").replace("[", "").replace("]", ""));
        if(!checker.isEmpty()){
            //System.out.println(" checker is "+ checker);
            solveBFS(adjmatrix,checker.get(0),checker);
            
        }
    }

    public void solveDFS(int[][] adjmatrix,int check,ArrayList<Integer> checker,ArrayList<Integer> visited,String res,HashSet<Integer> hs){
        Queue<Integer> pq = new LinkedList<>();
        pq.add(check);
        while(!pq.isEmpty()){
            check = pq.poll();
            visited.add(check);
            hs.add(check);
            if(checker.isEmpty()){
                break;
            }
            checker.remove((Integer)check);
            res += check+" "; //System.out.print(check+" ");
            for(int i=0;i<adjmatrix.length;i++){
                if(adjmatrix[check][i] == 1 && !visited.contains(i)){
                    pq.add(i);
                    hs.add(i);
                    solveDFS(adjmatrix, i, checker,visited,res,hs);
                }
            }
        }

    }
}