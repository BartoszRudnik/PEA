import java.util.Scanner;

public class measureUi {

    private final FullSearch fullSearch = new FullSearch();
    private final BranchBound branchBound = new BranchBound();
    private final DP dynamic = new DP();
    private final Data data = new Data();

    private boolean spr = true;

    public void show(){

        Scanner scanner = new Scanner(System.in);

        while(spr){

            System.out.println("Wybierz operacje:");
            System.out.println("1. Przeprowadz pomiary -> Algorytm BruteForce");
            System.out.println("2. Przeprowadz pomiary -> Algorytm Branch&Bound");
            System.out.println("3. Przprowadz pomiary -> DP");
            System.out.println("0. Wyjscie");

            int nr = scanner.nextInt();

            int[][] graph;
            switch(nr){

                case 0:

                    spr = false;
                    break;

                case 1:

                    long[] tab = new long[100];
                    System.out.println("Podaj liczbe wierzcholkow: ");
                    int vertex = scanner.nextInt();
                    int count = 0;

                    for(int i = 0; i < 200; i++){

                        data.generateRandomDataAsymetric(vertex);
                        graph = data.getGraph();
                        fullSearch.setV(vertex);

                        if(i <= 99)
                            fullSearch.algorithm(graph);
                        else{

                            long sTime = System.nanoTime();
                            fullSearch.algorithm(graph);
                            long fTime = System.nanoTime();

                            fTime -= sTime;
                            tab[count] = fTime;

                            count++;

                        }

                        System.out.println(i);

                    }

                    data.saveResult(vertex + "_FS.txt", tab);

                    break;

                case 2:
                    int timeExceeded = 0;
                    tab = new long[100];
                    System.out.println("Podaj liczbe wierzcholkow: ");
                    vertex = scanner.nextInt();
                    count = 0;

                    for(int i = 0; i < 200; i++){

                        data.generateRandomDataAsymetric(vertex);
                        graph = data.getGraph();
                        branchBound.setV(vertex);

                        if(i <= 99)
                            branchBound.Algorithm(graph);
                        else{

                            long sTime = System.nanoTime();
                            boolean test = branchBound.Algorithm(graph);
                            long fTime = System.nanoTime();

                            fTime -= sTime;

                            if(test) {

                                tab[count] = fTime;

                            }
                            else {

                                tab[count] = - 1;
                                timeExceeded++;

                            }

                            count++;

                        }

                        System.out.println(i);

                    }

                    if(timeExceeded > 0){
                        System.out.println("Czas 5 minut przekroczony dla: " + timeExceeded + "% operacji");
                    }

                    data.saveResult(vertex + "_BB.txt", tab);

                    break;

                case 3:

                    tab = new long[100];
                    System.out.println("Podaj liczbe wierzcholkow: ");
                    vertex = scanner.nextInt();
                    count = 0;

                    for(int i = 0; i < 200; i++){

                        data.generateRandomDataAsymetric(vertex);
                        graph = data.getGraph();

                        dynamic.setV(vertex);
                        dynamic.setGraph(graph);
                        dynamic.initializeGraph();

                        if(i <= 99)
                            dynamic.algorithm(0, (1 << vertex) - 2);
                        else{

                            long sTime = System.nanoTime();
                            dynamic.algorithm(0 , (1 << vertex) - 2);
                            long fTime = System.nanoTime();

                            fTime -= sTime;
                            tab[count] = fTime;

                            count++;

                        }

                        System.out.println(i);

                    }

                    data.saveResult(vertex + "_DP.txt", tab);

                    break;

            }

        }

    }

}
