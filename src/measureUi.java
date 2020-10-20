import java.util.Scanner;

public class measureUi {

    private FullSearch fullSearch = new FullSearch();
    private BranchBound branchBound = new BranchBound();
    private Data data = new Data();

    private boolean spr = true;
    private String fileName;
    private int vertex;
    private int[][] graph;
    private long[] tab;
    private int count;

    public void show(){

        Scanner scanner = new Scanner(System.in);

        while(spr){

            System.out.println("Wybierz operacje:");
            System.out.println("1. Przeprowadz pomiary -> Algorytm BruteForce");
            System.out.println("2. Przeprowadz pomiary -> Algorytm Branch&Bound");
            System.out.println("0. Wyjscie");

            int nr = scanner.nextInt();

            switch(nr){

                case 0:

                    spr = false;
                    break;

                case 1:

                    tab = new long[100];
                    System.out.println("Podaj liczbe wierzcholkow: ");
                    vertex = scanner.nextInt();
                    count = 0;

                    for(int i = 0; i < 200; i++){

                        data.generateRandomData(vertex);
                        graph = data.getGraph();
                        fullSearch.setV(vertex);

                        if(i > 99){

                            long sTime = System.nanoTime();
                            fullSearch.algorithm(graph);
                            long fTime = System.nanoTime();

                            fTime -= sTime;
                            tab[count] = fTime;

                            count++;

                        }

                    }

                    data.saveResult(Integer.toString(vertex) + ".txt", tab);

                    break;

                case 2:
                    tab = new long[100];
                    System.out.println("Podaj liczbe wierzcholkow: ");
                    vertex = scanner.nextInt();
                    count = 0;

                    for(int i = 0; i < 200; i++){

                        data.generateRandomData(vertex);
                        graph = data.getGraph();
                        branchBound.setV(vertex);

                        if(i > 99){

                            long sTime = System.nanoTime();
                            branchBound.algorithm(graph);
                            long fTime = System.nanoTime();

                            fTime -= sTime;
                            tab[count] = fTime;

                            count++;

                        }

                    }

                    data.saveResult(Integer.toString(vertex) + ".txt", tab);

                    break;

            }

        }

    }

}
