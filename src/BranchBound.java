public class BranchBound {

    private int [][] graph;
    private int v;

    public int [][] getGraph(){
        return this.graph;
    }

    public int getV(){
        return this.v;
    }

    public void setGraph(int [][] graph){
        this.graph = graph;
    }

    public void setV(int v){
        this.v = v;
    }

    public boolean areRowsReduced(){

        int count = 0;
        int infinityCount = 0;

        for(int i = 0; i < v; i++){

            count = 0;

            for(int j = 0; j < v; j++){

                if(graph[i][j] == 0)
                    count++;

                if(graph[i][j] <= -1)
                    infinityCount++;

            }

            if(count == 0 && infinityCount != v)
                return false;

        }

        return true;

    }

    public boolean areColumnsReduced(){

        int count = 0;
        int infinityCount = 0;

        for(int i = 0; i < v; i++){

            count = 0;
            infinityCount = 0;

            for(int j = 0; j < v; j++){

                if(graph[j][i] == 0)
                    count++;

                if(graph[j][i] <= -1)
                    infinityCount++;

            }

            if(count == 0 && infinityCount != v)
                return false;

        }

        return true;

    }

    public int reduceRows(){

        int sumRows = 0;
        int minValue = Integer.MAX_VALUE;

        for(int i = 0; i < v; i++){

            minValue = Integer.MAX_VALUE;

            for(int j = 0; j < v; j++){

                if(graph[i][j] < minValue && graph[i][j] > -1)
                    minValue = graph[i][j];

            }

            for(int k = 0; k < v; k++){

                graph[i][k] -= minValue;

            }

            sumRows += minValue;

        }

        return sumRows;

    }

    public int reduceColumns(){

        int sumColumns = 0;
        int minValue = Integer.MAX_VALUE;

        for(int i = 0; i < v; i++){

            minValue = Integer.MAX_VALUE;

            for(int j = 0; j < v; j++){

                if(graph[j][i] < minValue && graph[j][i] > -1)
                    minValue = graph[j][i];

            }

            for(int k = 0; k < v; k++){

                graph[k][i] -= minValue;

            }

            sumColumns += minValue;

        }

        return sumColumns;

    }

}
