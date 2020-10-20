public class BranchBound {

    private boolean [] visited;
    private int [][] graph;
    private int v;
    private int level = 0;
    int startVertex = 0;
    int resultCost = 0;
    int actualCost = 0;

    public int [][] getGraph(){
        return this.graph;
    }

    public int getV(){
        return this.v;
    }

    public int getLevel(){
        return this.level;
    }

    public void setGraph(int [][] graph){
        this.graph = graph;
    }

    public void setV(int v){
        this.v = v;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int algorithm(){

        if(level == 0) {

            resultCost = Integer.MAX_VALUE;
            actualCost = 0;

            actualCost += reduceRows(graph);
            actualCost += reduceColumns(graph);

            level++;

        }

        visited = new boolean[v];
        int[][] newGraph = new int[v][v];
        int[][] tmpResult = new int[v][v];

        for(int i = 0; i < v; i++){
            for(int j = 0; j < v; j++)
                tmpResult[i][j] = -1;
        }

        int tmp = actualCost;
        int[][] levelGraph = copyGraph(graph);

        levelAlgorithm(levelGraph,tmp,tmpResult);

        return tmp;

    }

    private void levelAlgorithm(int[][] levelGraph, int tmp, int[][] tmpResult){

        if(level >= 1 && level < v){

            for(int i = 1; i < v; i++){

                if(visited[i] == false) {

                    int [][] newGraph = new int[v][v];
                    newGraph = copyGraph(levelGraph);
                    actualCost = tmp;

                    for (int j = 0; j < v; j++) {
                        newGraph[startVertex][j] = -1;
                        newGraph[j][i] = -1;
                    }

                    for (int k = 0; k < v; k++)
                        newGraph[i][k] = -1;

                    newGraph[i][startVertex] = -1;

                    if (areRowsReduced(newGraph) == false)
                        actualCost += reduceRows(newGraph);
                    if (areColumnsReduced(newGraph) == false)
                        actualCost += reduceColumns(newGraph);

                    actualCost += graph[startVertex][i];

                    tmpResult[level][i] = actualCost;

                }

            }

            int mini = Integer.MAX_VALUE;
            int index = 0;

            for(int i = 0; i < v; i++){
                if(tmpResult[level][i] < mini && tmpResult[level][i] != -1) {
                    mini = tmpResult[level][i];
                    index = i;
                }
            }

            visited[index] = true;
            tmp = mini;

            System.out.println(startVertex + " " + index);

            for (int j = 0; j < v; j++) {
                levelGraph[startVertex][j] = -1;
                levelGraph[j][index] = -1;
            }

            level++;
            startVertex = index;
            levelAlgorithm(levelGraph,tmp,tmpResult);

        }

        for(int i = 1; i < v; i++) {

            if(visited[i] == false) {
                tmp += graph[startVertex][0];
            }
        }

    }

    private int[][] copyGraph(int [][] newGraph){

        newGraph = this.graph;

        return newGraph;

    }

    public boolean areRowsReduced(int [][] inputGraph){

        int count = 0;
        int infinityCount = 0;

        for(int i = 0; i < v; i++){

            count = 0;

            for(int j = 0; j < v; j++){

                if(inputGraph[i][j] == 0)
                    count++;

                if(inputGraph[i][j] <= -1)
                    infinityCount++;

            }

            if(count == 0 && infinityCount != v)
                return false;

        }

        return true;

    }

    public boolean areColumnsReduced(int [][] inputGraph){

        int count = 0;
        int infinityCount = 0;

        for(int i = 0; i < v; i++){

            count = 0;
            infinityCount = 0;

            for(int j = 0; j < v; j++){

                if(inputGraph[j][i] == 0)
                    count++;

                if(inputGraph[j][i] <= -1)
                    infinityCount++;

            }

            if(count == 0 && infinityCount != v)
                return false;

        }

        return true;

    }

    public int reduceRows(int [][] inputGraph){

        int sumRows = 0;
        int minValue = Integer.MAX_VALUE;

        for(int i = 0; i < v; i++){

            minValue = Integer.MAX_VALUE;

            for(int j = 0; j < v; j++){

                if(inputGraph[i][j] < minValue && inputGraph[i][j] > -1)
                    minValue = inputGraph[i][j];

            }

            for(int k = 0; k < v; k++){

                inputGraph[i][k] -= minValue;

            }

            sumRows += minValue;

        }

        return sumRows;

    }

    public int reduceColumns(int [][] inputGraph){

        int sumColumns = 0;
        int minValue = Integer.MAX_VALUE;

        for(int i = 0; i < v; i++){

            minValue = Integer.MAX_VALUE;

            for(int j = 0; j < v; j++){

                if(inputGraph[j][i] < minValue && inputGraph[j][i] > -1)
                    minValue = graph[j][i];

            }

            for(int k = 0; k < v; k++){

                inputGraph[k][i] -= minValue;

            }

            sumColumns += minValue;

        }

        return sumColumns;

    }

}
