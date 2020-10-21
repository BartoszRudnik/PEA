import java.util.ArrayList;
import java.util.Arrays;

public class BranchBound {

    private boolean [] visited;
    private StringBuilder finalPath = new StringBuilder();
    private ArrayList<int[][]> graphList = new ArrayList<>();
    private int [][] graph;
    private int v;
    private int level = 0;
    int countGraph;
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

    public StringBuilder getFinalPath(){
        return this.finalPath;
    }

    public int algorithm(int [][] graph){

        finalPath = new StringBuilder();
        visited = new boolean[v];

        resultCost = 0;
        actualCost = 0;

        actualCost += reduceRows(graph);
        actualCost += reduceColumns(graph);

        level++;

        int tmp = actualCost;
        countGraph = 0;

        int[][] levelGraph = copyGraph(graph);
        int[][] resultArray = new int[v][v];

        for(int i = 0; i < v; i++){
            for(int j = 0; j < v; j++){
                resultArray[i][j] = -1;
            }
        }

        startVertex = 0;

        visited[0] = true;

        finalPath.append("0-");
        levelAlgorithm(levelGraph, tmp, level, startVertex, resultArray);
        finalPath.append("0");

        return resultCost;

    }

    private void levelAlgorithm(int[][] levelGraph, int tmp, int level, int startVertex, int [][] rArray){

        if(level >= 1 && level < v){

            int tmpResult = Integer.MAX_VALUE;
            int index = 0;

            for(int i = 1; i < v; i++){

                if(visited[i] == false) {

                    int [][] newGraph;
                    newGraph = copyGraph(levelGraph);
                    actualCost = tmp;

                    for (int j = 0; j < v; j++) {
                        newGraph[startVertex][j] = -1;
                        newGraph[j][i] = -1;
                    }

                    newGraph[i][startVertex] = -1;

                    actualCost += reduceRows(newGraph);
                    actualCost += reduceColumns(newGraph);
                    actualCost += levelGraph[startVertex][i];

                    if(actualCost < tmpResult){

                        tmpResult = actualCost;
                        index = i;

                    }
                    rArray[level - 1][i] = actualCost;
                    int [][] saveGraph = copyGraph(newGraph);
                    graphList.add(saveGraph);
                    countGraph++;

                }

            }

            visited[index] = true;
            tmp = tmpResult;

            for(int i = 0; i < v; i++){
                if(rArray[level - 1][i] == tmp)
                    rArray[level - 1][i] = -1;
            }

            for(int i = 0; i < level - 1; i++){

                for(int j = 0; j < v; j++){

                    if(rArray[i][j] < tmp && rArray[i][j] != -1){
                        level = i;
                        visited = new boolean[v];
                        tmp = rArray[i][j];
                        startVertex = j;
                        finalPath.append(startVertex + "-");
                        rArray[i][j] = -1;
                        levelGraph = graphList.get(countGraph - 1);
                        createPath(levelGraph,visited);
                        levelAlgorithm(levelGraph, tmp, level, startVertex, rArray);
                        return;
                    }

                }

            }

            finalPath.append(index + "-");


            for (int j = 0; j < v; j++) {
                levelGraph[startVertex][j] = -1;
                levelGraph[j][index] = -1;
            }

            levelGraph[index][startVertex] = -1;

            reduceRows(levelGraph);
            reduceColumns(levelGraph);

            level++;
            startVertex = index;
            levelAlgorithm(levelGraph,tmp, level, startVertex, rArray);

        }
        else{
            resultCost = tmp;
            return;
        }


    }

    private int[][] copyGraph(int [][] oldGraph){

        int[][] newGraph = new int[v][v];
        
        for(int i = 0; i < v; i++){

            for(int j = 0; j < v; j++){
                newGraph[i][j] = oldGraph[i][j];
            }

        }

        return newGraph;

    }

    private void createPath(int [][] graph, boolean [] visited){

        visited[0] = true;

        for(int i = 0; i < v; i++){

            boolean test = true;

            for(int j = 0; j < v; j++){

                if(graph[j][i] > -1)
                    test = false;

            }

            if(test)
                visited[i] = true;

        }

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
        int minValue;

        for(int i = 0; i < v; i++){

            minValue = Integer.MAX_VALUE;

            for(int j = 0; j < v; j++){

                if(inputGraph[i][j] < minValue && inputGraph[i][j] > -1)
                    minValue = inputGraph[i][j];

            }

            if(minValue != Integer.MAX_VALUE) {

                for (int k = 0; k < v; k++) {

                    inputGraph[i][k] -= minValue;

                }

                sumRows += minValue;

            }

        }

        return sumRows;

    }

    public int reduceColumns(int [][] inputGraph){

        int sumColumns = 0;
        int minValue;

        for(int i = 0; i < v; i++){

            minValue = Integer.MAX_VALUE;

            for(int j = 0; j < v; j++){

                if(inputGraph[j][i] < minValue && inputGraph[j][i] > -1)
                    minValue = inputGraph[j][i];

            }

            if(minValue != Integer.MAX_VALUE) {

                for (int k = 0; k < v; k++) {

                    inputGraph[k][i] -= minValue;

                }

                sumColumns += minValue;

            }

        }

        return sumColumns;

    }

}
