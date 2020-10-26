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

    private int [] copyPath(int [] oldPath) {

        int [] newPath = new int[v + 1];

        if (v >= 0) {

            System.arraycopy(oldPath, 0, newPath, 0, v);

        }

        return newPath;

    }

    public boolean checkGraph(int [][] graph){

        if(v <= 1)
            return false;

        for(int i = 0; i < v; i++){

            if(graph[i][i] != -1)
                return false;

        }

        return true;

    }

    private int getMinOfRow(int [][] graph, int row) {

        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < v; i++) {

            if (graph[row][i] != -1 && graph[row][i] < minValue) {

                minValue = graph[row][i];

            }

        }

        return minValue;

    }

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

        long startTime = System.currentTimeMillis();
        long finishTime = startTime + 5 * 60 * 1000;

        int level = 1;
        int [] currPath = new int[v + 1];
        int lowerBound = 0;
        int actualCost = 0;

        visited = new boolean[v];
        finalPath = new int[v + 1];

        for(int i = 0; i < v + 1; i++){

            currPath[i] = -1;

        }

        for (int i = 0; i < v; i++) {

            lowerBound += (getMinOfRow(graph, i) + getSecondMinOfRow(graph, i));

        }

        if(lowerBound > 1) {

            lowerBound /= 2;

        }

        visited[0] = true;
        currPath[0] = 0;

        levelAlgorithm(graph, lowerBound, actualCost, level, currPath, finishTime);

        return System.currentTimeMillis() < finishTime;

    }

    private void levelAlgorithm(int [][] graph, int lowerBound, int actualCost, int level, int [] currPath, long finishTime) {

        if (System.currentTimeMillis() < finishTime) {

            if (level < v) {

                for (int i = 0; i < v; i++) {

                    if (graph[currPath[level - 1]][i] != -1 && !visited[i]) {

                        int tmpCost = lowerBound;
                        actualCost += graph[currPath[level - 1]][i];

                        if (level > 1)
                            lowerBound -= ((getMinOfRow(graph, currPath[level - 1]) + getSecondMinOfRow(graph, i)) / 2);
                        else
                            lowerBound -= ((getSecondMinOfRow(graph, currPath[level - 1]) + getSecondMinOfRow(graph, i)) / 2);

                        if (lowerBound + actualCost < result) {

                            currPath[level] = i;
                            visited[i] = true;

                            levelAlgorithm(graph, lowerBound, actualCost, level + 1, currPath, finishTime);

                        }

                        actualCost -= graph[currPath[level - 1]][i];
                        lowerBound = tmpCost;

                        for (int j = 1; j < v; j++) {
                            visited[j] = false;
                        }

                        for (int j = 0; j < level; j++)
                            visited[currPath[j]] = true;

                    }

                }

            } else {

                if (graph[currPath[level - 1]][0] != -1) {

                    int currRes = actualCost + graph[currPath[level - 1]][0];

                    if (currRes < result) {

                        finalPath = copyPath(currPath);
                        result = currRes;

                    }

                }

            }

        }
        else{
            return;
        }

    }

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