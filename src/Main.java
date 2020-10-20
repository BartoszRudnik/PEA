import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //Ui ui = new Ui();
        //ui.showMenu();

        BranchBound bb = new BranchBound();

        int adj[][] = {{-1, 10, 15, 20},
                       {10, -1, 35, 25},
                       {15, 35, -1, 30},
                       {20, 25, 30, -1}};

        bb.setGraph(adj);
        bb.setV(4);

        System.out.println(bb.algorithm());

    }

}
