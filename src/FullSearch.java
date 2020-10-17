public class FullSearch {

    private int v;
    private int shortestPath;

    public int algorithm(){



        return shortestPath;

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



}
