import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FullSearch {

    private int v;

    public void setV(int v){
        this.v = v;
    }

    public int getV(){
        return this.v;
    }

    public FullSearch(){
        this.v = 0;
    }

    public int algorithm(int graph[][]){

        int s = 0;
        int count = 0;
        int shortestPath = Integer.MAX_VALUE;
        int numberOfPermutations = numberOfPermutations(v);
        int data[] = new int[v - 1];

        for(int i = 0; i < v; i++){

            if(i != s) {
                data[count] = i;
                count++;
            }

        }

        for(int i = 0; i < numberOfPermutations; i++){

            int actualPath = 0;
            int k = s;

            for(int j = 0; j < data.length; j++){
                actualPath += graph[k][data[j]];
                k = data[j];
            }
            actualPath += graph[k][s];

            if(i < numberOfPermutations - 1)
                data = permutation(data);

            if(actualPath < shortestPath)
                shortestPath = actualPath;

        }

        return shortestPath;

    }

    public boolean checkGraph(int graph[][]){

        if(v <= 1)
            return false;

        for(int i = 0; i < v; i++){

            if(graph[i][i] != -1)
                return false;

        }

        return true;

    }

    public int [] swap(int data[],int firstIndex, int secondIndex){

        int tmp = data[firstIndex];

        data[firstIndex] = data[secondIndex];
        data[secondIndex] = tmp;

        return data;

    }

    public int [] reverse(int data[], int first, int second){

        while(first < second){

            int tmp = data[second];

            data = swap(data, first, second);

            first++;
            second--;

        }

        return data;

    }

    public int[] permutation(int data[]){

        if(data.length > 1){

            int lastIndex = data.length - 2;
            int next = data.length - 1;

            while (lastIndex >= 0) {
                if (data[lastIndex] < data[lastIndex + 1]) {
                    break;
                }
                lastIndex--;
            }

            for(int i = data.length - 1; i > lastIndex; i--){
                if(data[i] > data[lastIndex]){
                    next = i;
                    break;
                }
            }

            data = swap(data, next, lastIndex);
            data = reverse(data, lastIndex  + 1, data.length - 1);

        }

        return data;

    }

    public int numberOfPermutations(int n){

        if(n < 1)
            return 0;

        int result = 1;

        for(int i = 2; i <= n - 1; i++)
            result *= i;

        return result;

    }

}
