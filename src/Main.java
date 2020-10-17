import java.util.Arrays;

public class Main {

    public static void main(String[] args){

        FullSearch fs = new FullSearch();

        int graph[][] = { { 0, 10, 15, 20 },
                        { 10, 0, 35, 25 },
                        { 15, 35, 0, 30 },
                        { 20, 25, 30, 0 } };

        fs.setV(4);

        System.out.print(fs.algorithm(graph));


    }

}
