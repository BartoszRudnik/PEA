public class FullSearch {

    private int v;
    private StringBuilder path = null;

    public void setV(int v){
        this.v = v;
    }

    public int getV(){
        return this.v;
    }

    public void setPath(StringBuilder path){
        this.path = path;
    }

    public StringBuilder getPath(){
        return this.path;
    }

    public FullSearch(){
        this.v = 0;
    }

    public int algorithm(int [][] graph){

        int shortestPath = Integer.MAX_VALUE;

        //wyznaczenie liczby permutacji dla zadanej liczby wierzcholkow
        long numberOfPermutations = numberOfPermutations(v);

        int [] data = new int[v - 1];
        StringBuilder tmp;

        //Wstawianie do tablicy wszystkich wierzcholkow z wyjatkiem wierzcholka startowego
        for(int i = 1; i < v; i++){

            data[i - 1] = i;

        }

        for(long i = 0; i < numberOfPermutations; i++){

            // przechowuje koszt aktualnej permutacji
            int actualPath = 0;
            int startPos = 0;

            tmp = new StringBuilder();

            tmp.append(startPos);
            tmp.append('-');

            //obliczanie kosztu aktualnej permutacji
            for (int j : data) {

                tmp.append(j);
                tmp.append('-');

                actualPath += graph[startPos][j];
                startPos = j;

            }
            actualPath += graph[startPos][0];

            tmp.append(0);

            //wyznaczenie kolejnej permutacji
            if(i < numberOfPermutations - 1) {

                data = permutation(data);

            }

            // aktualizacji kosztu minimalnego przejscia
            if(actualPath < shortestPath) {

                shortestPath = actualPath;
                path = tmp;

            }

        }

        return shortestPath;

    }

    //funkcja sluzaca do wypisania sciezki o najmniejszym koszcie
    public void getResultPath(){

        if(path != null)
            System.out.println(path);
        else
            System.out.println("Nie wyznaczono jeszcze sciezki");

    }

    //funkcja sprawdzajaca czy zadany graf ma na przekatnej '-1'
    public boolean checkGraph(int [][] graph){

        if(v <= 1)
            return false;

        for(int i = 0; i < v; i++){

            if(graph[i][i] != -1)
                return false;

        }

        return true;

    }

    //funkcja zamieniajaca miejscami element o indeksie 'firstIndex' z elementem o indeksie 'secondIndex' w tablicy 'data'
    public int [] swap(int [] data, int firstIndex, int secondIndex){

        int tmp = data[firstIndex];

        data[firstIndex] = data[secondIndex];
        data[secondIndex] = tmp;

        return data;

    }

    //funkcja odwracajaca kolejnosc wystepowania elementów w tablicy od elementu o indeksie 'start' do elementu o indeksie 'finish'
    public int [] reverse(int [] data, int start, int finish){

        while(start < finish){

            data = swap(data, start, finish);

            start++;
            finish--;

        }

        return data;

    }

    //funkcja wyznaczajaca nastepna permutacje dla danych w danej tablicy
    public int[] permutation(int [] data){

        if(data.length > 1){

            int lastIndex = data.length - 2;
            int next = data.length - 1;

            //wyznaczamy indeks elementu wzgledem, ktorego stworzona zostanie nastepna permutacja, przyjmijmy nazwe 'pivot'
            while (lastIndex >= 0) {

                if (data[lastIndex] < data[lastIndex + 1]) {
                    break;
                }

                lastIndex--;

            }

            //jesli 'lastIndex' ma wartosc -1 to w tablicy nie wystepuje para rosnacych elementów i nie mozna wyznaczyc nastepnej permutacji
            if(lastIndex >= 0) {

                //wyznaczamy indeks ostatniego rosnacego elementu
                for (int i = data.length - 1; i > lastIndex; i--) {

                    if (data[i] > data[lastIndex]) {

                        next = i;
                        break;

                    }

                }

                //zamiana miejsc pivota i ostatniego rosnacego elementu
                data = swap(data, next, lastIndex);

                //odwrócenie kolejnosci elementów znajdujących się w tablicy pomiędzy pivotem a ostatnim rosnacym elementem
                data = reverse(data, lastIndex + 1, data.length - 1);

            }

        }

        return data;

    }

    //funkcja wyznaczajaca liczbe mozliwych permutacji dla grafu o 'n' wierzcholkach
    public long numberOfPermutations(int n){

        if(n < 1)
            return 0;

        long result = 1;

        for(int i = 2; i <= n - 1; i++) {

            result *= i;

        }

        return result;

    }

}
