import java.util.Scanner;

public class testUi {

    private boolean spr = true;
    private int[][] graph;
    private int vertex = 0;
    private final Data data = new Data();
    private FullSearch fullSearch = new FullSearch();
    private BranchBound branchBound = new BranchBound();
    private DP dP = new DP();

    public void show() {

        Scanner scanner = new Scanner(System.in);

        while (spr) {

            System.out.println("Wybierz operacje");
            System.out.println("1. Wczytaj dane z pliku");
            System.out.println("2. Wygeneruj losowe dane asymetryczne");
            System.out.println("3. Wygeneruj losowe dane symetryczne");
            System.out.println("4. Pokaz aktualne dane");
            System.out.println("5. Algorytm Brute Force");
            System.out.println("6. Algorytm Branch&Bound");
            System.out.println("7. Algorytm programowania dynamicznego");
            System.out.println("0. Wyjdz");

            int nrAlg = scanner.nextInt();

            switch (nrAlg) {

                case 0:

                    spr = false;

                    break;

                case 1:

                    System.out.println("Podaje nazwe pliku do wczytania: ");

                    scanner.nextLine();
                    String fileName = scanner.nextLine();

                    data.readData(fileName);

                    break;

                case 2:

                    System.out.println("Podaj liczbe wierzcholkow: ");

                    scanner.nextLine();
                    vertex = scanner.nextInt();

                    data.generateRandomDataAsymetric(vertex);

                    break;

                case 3:

                    System.out.println("Podaj liczbe wierzcholkow: ");

                    scanner.nextLine();
                    vertex = scanner.nextInt();

                    data.generateRandomDataSymetric(vertex);

                    break;

                case 4:

                    data.printData();

                    break;

                case 5:

                    fullSearch = new FullSearch();

                    vertex = data.getV();
                    graph = data.getGraph();
                    fullSearch.setV(vertex);

                    if(data.checkGraph(graph)){

                        System.out.println(fullSearch.algorithm(graph));
                        fullSearch.getResultPath();

                    }
                    else{
                        System.out.println("Graf jest niezgodny z zalozeniami");
                    }

                    break;

                case 6:

                    branchBound = new BranchBound();

                    vertex = data.getV();
                    graph = data.getGraph();
                    branchBound.setV(vertex);

                    if(data.checkGraph(graph)) {

                        if (branchBound.Algorithm(graph)) {

                            System.out.println(branchBound.getResult());
                            branchBound.printPath();

                        } else {

                            System.out.println("Przekroczono czas 5 minut");

                        }

                    }
                    else{
                        System.out.println("Graf jest niezgodny z zalozeniami");
                    }


                    break;

                case 7:

                    dP = new DP();

                    vertex = data.getV();
                    graph = data.getGraph();
                    dP.setV(vertex);
                    dP.setGraph(graph);

                    if(data.checkGraph(graph)) {

                        dP.algorithm();
                        System.out.println(dP.getResult());
                        //dP.getResultPath();

                    }

                    break;

                default:

                    System.out.println("Wybrano zly numer.");

                    break;

            }

        }

    }

}


