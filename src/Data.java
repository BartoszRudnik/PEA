import java.io.*;
import java.util.Random;

public class Data {

    private int [][] graph;
    private int v;

    public int[][] getGraph(){
        return this.graph;
    }

    public int getV(){
        return this.v;
    }

    public void printData(){

        if(graph != null){

            System.out.println(v);

            for(int i = 0; i < v; i++){

                for(int j = 0; j < v; j++){

                    System.out.print(graph[i][j] + " ");

                }

                System.out.println();

            }

        }

    }

    public void generateRandomData(int vertex){

        v = vertex;

        graph = new int[vertex][vertex];

        Random rand = new Random();

        for(int i = 0; i < vertex; i++){

            for(int j = 0; j < vertex; j++){

                if(i == j)
                    graph[i][j] = -1;
                else
                    graph[i][j] = rand.nextInt(100);

            }

        }

    }

    public void readData(String fileName) {

        try {

            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = br.readLine();

            if(line != null)
                v = Integer.parseInt(line);

            graph = new int[v][v];

            for(int i = 0; i < v; i++){

                line = br.readLine();
                String[] str = line.trim().split("\\s+");

                for(int j = 0; j < v; j++)
                {
                    graph[i][j] = Integer.parseInt(str[j]);
                }

            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}
