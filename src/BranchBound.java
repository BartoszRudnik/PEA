public class BranchBound {

    private int v;
    private int [] finalPath;
    private boolean [] visited;
    private int result = Integer.MAX_VALUE;

    public int getResult(){
        return this.result;
    }

    public int[] getFinalPath(){
        return this.finalPath;
    }

    public boolean [] getVisited(){
        return this.visited;
    }

    public void setV(int v){
        this.v = v;
    }

    public void setFinalPath(int [] finalPath){
        this.finalPath = finalPath;
    }

    public void setVisited(boolean [] visited){
        this.visited = visited;
    }

    public void setResult(int result){
        this.result = result;
    }

    //funkcja tworzaca nowa tablice i kopiujaca do niej zawartosc 'oldPath'
    private int [] copyPath(int [] oldPath) {

        int [] newPath = new int[v + 1];

        if (v >= 0) {

            System.arraycopy(oldPath, 0, newPath, 0, v);

        }

        return newPath;

    }

    //funkcja zwracajaca minimalny koszt drogi z wierzcholka o indeksie 'row'
    private int getMinOfRow(int [][] graph, int row) {

        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < v; i++) {

            if (graph[row][i] != -1 && graph[row][i] < minValue) {

                minValue = graph[row][i];

            }

        }

        return minValue;

    }

    //funkcja zwracajaca drugi najmniejszy koszt drogi z wierzchołka o indeksie 'row'
    public int getSecondMinOfRow(int [][] graph, int row) {

        int count = -1;
        int minValue = Integer.MAX_VALUE;
        int secondMinValue = Integer.MAX_VALUE;

        for (int j = 0; j < v; j++) {

            if(graph[row][j] != -1) {

                if (graph[row][j] < minValue) {

                    secondMinValue = minValue;
                    minValue = graph[row][j];
                    count = -1;

                }else if(graph[row][j] == minValue){

                    count++;

                }
                else if (graph[row][j] <= secondMinValue && graph[row][j] > minValue) {

                    secondMinValue = graph[row][j];

                }
                else if(graph[row][j] <= secondMinValue && graph[row][j] == minValue && count > 0){

                    secondMinValue = graph[row][j];

                }

            }

        }

        return secondMinValue;

    }

    public boolean Algorithm(int [][] graph) {

        //zmienna wyznaczajaca aktualny czas
        long startTime = System.currentTimeMillis();

        //ustalamy maksymalny czas działania algorytmu na 5 minut
        long finishTime = startTime + 5 * 60 * 1000;

        //inicjalizacja zmiennej oznaczajacej poziom, na ktorym aktualnie jestesmy
        int level = 1;

        //tablica przechowujace aktualne rozwiazanie
        int [] currPath = new int[v + 1];

        //zmienna przechowujaca aktualne ograniczenie
        int lowerBound = 0;

        //zmienna przechowujaca koszt aktualnego rozwiazania
        int actualCost = 0;

        visited = new boolean[v];
        finalPath = new int[v + 1];

        for(int i = 0; i < v + 1; i++){

            currPath[i] = -1;

        }

        //obliczamy wstepne ograniczenie korzystajac z formuly 1/2 * suma najmniejszego i drugiego najmniejszego
        //kosztu każdego z wierzchołków
        for (int i = 0; i < v; i++) {

            lowerBound += (getMinOfRow(graph, i) + getSecondMinOfRow(graph, i));

        }
        lowerBound /= 2;

        //jako wierzchołek startowy przyjmujemy wierzcholek o indeksie '0'
        visited[0] = true;
        currPath[0] = 0;

        //wywolywana jest funkcja dokonujaca obliczen na kolejnych poziomach
        levelAlgorithm(graph, lowerBound, actualCost, level, currPath, finishTime);

        //zwracana jest informacje czy algorytm wykonal sie w czasie do 5 minut
        return System.currentTimeMillis() < finishTime;

    }

    private void levelAlgorithm(int [][] graph, int lowerBound, int actualCost, int level, int [] currPath, long finishTime) {

        //sprawdzamy czy funkcja nie przekroczyła ustalonego limitu czasu wykonywania
        if (System.currentTimeMillis() < finishTime) {

            //przypadek, w którym poziom nalezy do przedziału [1,v)
            if (level < v) {

                for (int i = 0; i < v; i++) {

                    //sprawdzamy czy badany wierzcholek istnieje oraz czy nie byl juz wczesniej odwiedzony
                    if (graph[currPath[level - 1]][i] != -1 && !visited[i]) {

                        int tmpLowerBound = lowerBound;
                        actualCost += graph[currPath[level - 1]][i];

                        //aktualizujemy wartosc ograniczenia
                        if (level > 1) {

                            int tmpBound = getMinOfRow(graph,currPath[level - 1]) + getSecondMinOfRow(graph,i);
                            tmpBound /= 2;

                            lowerBound -= tmpBound;

                        }
                        else {

                            int tmpBound = getSecondMinOfRow(graph, currPath[level -1]) + getSecondMinOfRow(graph,i);
                            tmpBound /= 2;

                            lowerBound -= tmpBound;

                        }

                        //jesli aktualne ograniczenie jest mniejsze od aktualnego wyniku to
                        // kontynuujemy przeszukiwanie na nastepnym poziomie
                        if (lowerBound + actualCost < result) {

                            currPath[level] = i;
                            visited[i] = true;

                            levelAlgorithm(graph, lowerBound, actualCost, level + 1, currPath, finishTime);

                        }

                        //jesli aktualne ograniczenie jest wieksze od aktualnego wyniku to anulujemy wszystkie zmiany,
                        //ktore powstaly w tej iteracji
                        for (int j = 1; j < v; j++) {
                            visited[j] = false;
                        }

                        for (int j = 0; j < level; j++) {
                            visited[currPath[j]] = true;
                        }

                        lowerBound = tmpLowerBound;
                        actualCost -= graph[currPath[level - 1]][i];

                    }

                }

            }
            //przypadek, w ktorym osiagnelismy ostatni poziom
            else {

                //dodajemy do aktualnego rozwiazania koszt drogi z ostatniego wierzcholka do wierzcholka startowego
                actualCost += graph[currPath[level - 1]][0];

                //jeśli aktualne rozwiazanie ma mniejszy łaczy kost to aktualizujemy rozwiazanie koncowe
                if (actualCost < result) {

                   finalPath = copyPath(currPath);
                   result = actualCost;

                }

            }

        }
        else{
            return;
        }

    }

    //funkcja wypisujaca wynikowa droge
    public void printPath(){

        for(int i = 0; i < finalPath.length; i++){

            System.out.print(finalPath[i]);

            if(i != finalPath.length - 1) {

                System.out.print(" - ");

            }

        }

        System.out.println();

    }

}