import java.util.Scanner;

public class testUi {

    private boolean spr = true;
    private String fileName;
    private int vertex;
    private int[][] graph;

    Data data = new Data();
    FullSearch fullSearch = new FullSearch();

    public void show() {

        Scanner scanner = new Scanner(System.in);

        while (spr) {

            System.out.println("Wybierz operacje");
            System.out.println("1. Wczytaj dane z pliku");
            System.out.println("2. Wygeneruj losowe dane");
            System.out.println("3. Pokaz aktualne dane");
            System.out.println("4. Algorytm Brute Force");
            System.out.println("0. Wyjdz");

            int nrAlg = scanner.nextInt();

            switch (nrAlg) {

                case 0:

                    spr = false;

                    break;

                case 1:

                    System.out.println("Podaje nazwe pliku do wczytania: ");
                    scanner.nextLine();
                    fileName = scanner.nextLine();
                    data.readData(fileName);
                    vertex = data.getV();
                    graph = data.getGraph();
                    fullSearch.setV(vertex);

                    break;

                case 2:

                    System.out.println("Podaj liczbe wierzcholkow: ");
                    scanner.nextLine();
                    vertex = scanner.nextInt();
                    data.generateRandomData(vertex);
                    graph = data.getGraph();
                    fullSearch.setV(vertex);

                    break;

                case 3:

                    data.printData();

                    break;

                case 4:

                    if(fullSearch.checkGraph(graph)){

                        System.out.println(fullSearch.algorithm(graph));

                    }
                    else{
                        System.out.println("Graf jest niezgodny z zalozeniami");
                    }

                    break;

                default:

                    System.out.println("Wybrano zly numer.");

                    break;

            }

        }

    }

}


