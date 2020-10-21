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

    private int firstMin(int [][] graph, int index) {

        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < v; i++) {

            if (graph[index][i] < minValue && index != i) {

                minValue = graph[index][i];

            }

        }

        return minValue;

    }

    private int secondMin(int [][] graph, int index) {

        int minValue = Integer.MAX_VALUE;
        int secondMinValue = Integer.MAX_VALUE;

        for (int j = 0; j < v; j++) {

            if (index == j) {
                continue;
            }

            if (graph[index][j] <= minValue) {

                secondMinValue = minValue;
                minValue = graph[index][j];

            }
            else if (graph[index][j] <= secondMinValue && graph[index][j] != minValue) {
                secondMinValue = graph[index][j];
            }

        }

        return secondMinValue;

    }

    private void levelAlgorithm(int [][] graph, int curr_bound, int curr_weight, int level, int [] curr_path) {

        if(level >= 1 && level < v) {

            for (int i = 0; i < v; i++) {

                if (graph[curr_path[level - 1]][i] != 0 && !visited[i]) {

                    int temp = curr_bound;
                    curr_weight += graph[curr_path[level - 1]][i];

                    if (level==1)
                        curr_bound -= ((secondMin(graph, curr_path[level-1]) + secondMin(graph, i))/2);
                    else
                        curr_bound -= ((firstMin(graph, curr_path[level-1]) + secondMin(graph, i))/2);

                    if (curr_bound + curr_weight < result) {

                        curr_path[level] = i;
                        visited[i] = true;

                        levelAlgorithm(graph, curr_bound, curr_weight, level + 1, curr_path);

                    }

                    curr_weight -= graph[curr_path[level - 1]][i];
                    curr_bound = temp;

                    for(int j = 0; j< v; j++){
                        visited[j] = false;
                    }

                    for (int j = 0; j <= level - 1; j++)
                        visited[curr_path[j]] = true;

                }

            }

        }
        else{

            if (graph[curr_path[level - 1]][curr_path[0]] != 0) {

                int curr_res = curr_weight + graph[curr_path[level - 1]][curr_path[0]];

                if (curr_res < result) {

                    finalPath = copyPath(curr_path);
                    result = curr_res;

                }

            }

        }

    }

    public void Algorithm(int [][] graph) {

        int level = 1;

        visited = new boolean[v];
        finalPath = new int[v + 1];

        int [] currPath = new int[v + 1];
        int currBound = 0;

        for(int i = 0; i < v + 1; i++){
            currPath[i] = -1;
        }

        for (int i = 0; i < v; i++)
            currBound += (firstMin(graph, i) +
                    secondMin(graph, i));

        if(currBound != 1){
            currBound /= 2;
        }

        visited[0] = true;
        currPath[0] = 0;

        levelAlgorithm(graph, currBound, 0, level, currPath);

    }

}