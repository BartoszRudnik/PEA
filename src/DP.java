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

    public void initializeGraph(){

        setGraphLen();

        resultPath = new StringBuilder();
        resultPath.append("0-");

        tmpGraph = new int[v][graphLen];
        vertexGraph = new int[v][graphLen];

        for(int i = 0; i < v; i++){

            for(int j = 0; j < graphLen; j++){

                if(i == j) {

                    vertexGraph[i][j] = -1;
                    tmpGraph[i][j] = -1;

                }

                else {

                    vertexGraph[i][j] = Short.MAX_VALUE;
                    tmpGraph[i][j] = Short.MAX_VALUE;

                }

            }

        }

        for(int i = 0; i < v; i++){

            tmpGraph[i][0] = graph[i][0];

        }

    }

    public int algorithm(int startVertex, int vertexes){

        int tmpResult = Short.MAX_VALUE;
        int visited;

        if(tmpGraph[startVertex][vertexes] != -1 && tmpGraph[startVertex][vertexes] != Short.MAX_VALUE){
            return tmpGraph[startVertex][vertexes];
        }

        for(int i = 0; i < v; i++){

            int mask = (1 << v) - (1 << i) - 1;
            visited = vertexes & mask;

            if(visited == vertexes)
                continue;
            else{

                int actualCost = graph[startVertex][i] + algorithm(i, visited);

                if(actualCost < tmpResult){

                    tmpResult = actualCost;
                    vertexGraph[startVertex][vertexes] = i;

                }

            }

        }

        tmpGraph[startVertex][vertexes] = tmpResult;

        return tmpResult;

    }

    private void getResultPath(int startVertex, int vertexes){

        if(vertexGraph[startVertex][vertexes] == -1 || vertexGraph[startVertex][vertexes] == Short.MAX_VALUE){
            return;
        }
        else{

            resultPath.append(vertexGraph[startVertex][vertexes]).append("-");

            int tmp = (1 << v) - (1 << vertexGraph[startVertex][vertexes]) - 1;
            int visited = tmp & vertexes;

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

