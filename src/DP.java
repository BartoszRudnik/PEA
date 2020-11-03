public class DP {

    private int [][] graph;
    private int [][] tmpGraph;
    private int v;
    private int result;
    private int maxCount;
    private StringBuilder [] resultPaths;

    public int[][] getGraph(){
        return this.graph;
    }

    public void setGraph(int [][] graph){
        this.graph = graph;
    }

    public int getV(){
        return this.v;
    }

    public void setV(int v){
        this.v = v;
    }

    public void setResult(int result){
        this.result = result;
    }

    public void setMaxCount(int maxCount){
        this.maxCount = maxCount;
    }

    private void initializeGraph(){

        tmpGraph = new int[v][maxCount];

        for (int i = 0; i < v; i++){

            for(int j = 0; j < maxCount; j++){

                tmpGraph[i][j] = Short.MAX_VALUE;

            }

        }

    }

    public void algorithm(){

        setMaxCount((int) Math.pow(2, v));
        initializeGraph();
        tmpGraph[0][1] = 0;

        resultPaths = new StringBuilder[v];

        for(int i = 0; i < v; i++){
            resultPaths[i] = new StringBuilder();
        }

        for (int i = 1; i < maxCount; i++) {

            for (int j = 0; j < v; j++) {

                int tmp1 = (int) Math.pow(2, j);

                if ((i & tmp1) != 0) {

                    for (int k = 0; k < v; k++) {

                        int tmp2 = (int) Math.pow(2, k);

                        if ((i & tmp2) == 0) {

                            int logicalSum = i | tmp2;
                            int min1 = tmpGraph[j][i] + graph[j][k];

                            if(min1 < tmpGraph[k][logicalSum]){

                                resultPaths[j].append(min1 + "*" + i + "* ");
                                tmpGraph[k][logicalSum] = min1;

                            }

                        }

                    }

                }

            }

        }

    }

    public int getResult(){

        setResult(Short.MAX_VALUE);

        for (int i = 0; i < v; i++) {            
            
            int min1 = graph[i][0] + tmpGraph[i][maxCount - 1];

            if(min1 < result){

                result = min1;

            }

        }

        return result;

    }

    public void getResultPath(){

        for(int i = 0; i < v; i++){

            System.out.println(resultPaths[i]);

        }

    }

}
