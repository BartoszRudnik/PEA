public class DP {

    private int [][] graph;
    private int [][] tmpGraph;
    private int [][] vertexGraph;
    private int v;
    private int graphLen;
    private StringBuilder resultPath = new StringBuilder();

    public void setGraph(int [][] graph){
        this.graph = graph;
    }

    public int [][] getGraph(){
        return this.graph;
    }

    public void setV(int v){
        this.v = v;
    }

    public int getV(){
        return this.v;
    }

    public void setGraphLen(){
        graphLen = (1 << v);
    }

    public int getGraphLen(){
        return this.graphLen;
    }

    // funkcja inicjalizująca tablice do przechowywania wierzcholkow
    // i tablice przechowująca wyniki tymczasowe
    public void initializeGraph(){

        setGraphLen();

        resultPath = new StringBuilder();
        resultPath.append("0-");

        tmpGraph = new int[v][graphLen];
        vertexGraph = new int[v][graphLen];

        for(int i = 0; i < v; i++){

            for(int j = 0; j < graphLen; j++){

                    vertexGraph[i][j] = Short.MAX_VALUE;
                    tmpGraph[i][j] = Short.MAX_VALUE;

            }

        }

        for(int i = 0; i < v; i++){

            tmpGraph[i][0] = graph[i][0];

        }

    }

    //funkcja obliczająca najtansze przejscie cyklu w grafie Hamiltona
    public int algorithm(int startVertex, int vertexes){

        int tmpResult = Short.MAX_VALUE;
        int visited;

        //jeśli badany podproblem został juz obliczony, zwracamy uzyskany wczesniej rezultat
        if(tmpGraph[startVertex][vertexes] != Short.MAX_VALUE){
            return tmpGraph[startVertex][vertexes];
        }

        for(int i = 0; i < v; i++){

            //wyznaczamy nowa maske, odejmujemy '1' aby wyrzucic z maski wierzcholek startowy
            int mask = (1 << v) - (1 << i) - 1;
            //przy pomocy operacji iloczynu bitowego wyrzucamy z maski aktualnie badany wierzcholek
            visited = vertexes & mask;

            //jesli wytworzony zostal taki sam podzbior jak przy poprzednim wywolaniu
            // to nie kontynuujemy obliczen dla tego wierzcholka
            if(visited == vertexes)
                continue;
            else{

                //dotychczasowy koszt jest rowny sumie kosztu przejscia z poprzedniego wierzcholka do aktualnie
                //badanego wierzcholka i kosztowi przejscia przez pozostale nieodwiedzone jeszcze wierzcholki
                int actualCost = graph[startVertex][i] + algorithm(i, visited);

                //jesli otrzymany koszt jest mniejszy od dotychczas najmniejszego kosztu
                //to aktualizujemy dotychczas najmniejszy koszt i dodajemy indeks aktualnie badanego wierzcholka
                //do tablicy wierzcholkow
                if(actualCost < tmpResult){

                    tmpResult = actualCost;
                    vertexGraph[startVertex][vertexes] = i;

                }

            }

        }

        tmpGraph[startVertex][vertexes] = tmpResult;

        return tmpResult;

    }

    //funkcja odtwarzajace kolejne odwiedzone wierzcholki dla najlepszego przejscia
    private void getResultPath(int startVertex, int vertexes){

        //osiagniecie wartosci 'Short.MAX_VALUE' oznacza odtworzenie calej sciezki
        if(vertexGraph[startVertex][vertexes] != Short.MAX_VALUE){

            //do wynikowej sciezki dodajemy indeks wierzcholka znajdujacego sie
            //w tablicy wierzcholkow pod indeksem wyznaczonym przez poprzedni wierzcholek
            //i maske odwiedzonych wierzcholkow
            resultPath.append(vertexGraph[startVertex][vertexes]).append("-");

            //wyznaczamy nowa maske bitowa
            int tmp = (1 << v) - (1 << vertexGraph[startVertex][vertexes]) - 1;

            //z maski wyrzucamy indeks wierzcholka, ktory otrzymalismy w tym wywolaniu
            int visited = tmp & vertexes;

            //rekurencyjnie wyszukujemy kolejne elementy sciezki
            getResultPath(vertexGraph[startVertex][vertexes], visited);

        }

    }

    public void computeAlgorithm(){

        initializeGraph();

        int result = algorithm(0, (1 << v) - 2);

        getResultPath(0, (1 << v) - 2);
        resultPath.append("0");

        System.out.println(result);
        System.out.println(resultPath);

    }

}

