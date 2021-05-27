package codes.graph;

import java.util.Random;

public class GraphGenerate {

    private final int junctionBoxNum;
    private final int switchNum;
    private final int vertexNum;

    public GraphGenerate() {
        this.vertexNum = generateVerticesNum();
        this.junctionBoxNum = generateJunctionNum();
        this.switchNum = getSwitchNum();
    }

    public void createGraph() {
        Graph graph = new Graph(vertexNum);
        do {
            linkJunctionToSource(graph);
            linkSwitchesToJunction(graph);
            linkSwitchesToEach(graph);
        } while (graph.isCompleteGraph());
    }

    private void linkJunctionToSource(Graph graph) {
        int source = 0;
        for (int i = 1; i <= junctionBoxNum; i++) {
            int weight = randomWeight();
            graph.addEdge(source, source + 1, weight);
        }
    }

    private void linkSwitchesToJunction(Graph graph) {
        int source = 1;
        int destination = junctionBoxNum + 1;
        for (int i = 0; i < junctionBoxNum; i++) {
            for (int j = 0; j < switchNum; j++) {
                int weight = randomWeight();
                graph.addEdge(source + i, destination + j, weight);
            }
        }
    }
    private void linkSwitchesToEach(Graph graph){
        int keySource = junctionBoxNum+1;
        for (int i = 0; i <switchNum ; i++) {
            int weight = randomWeight();
            graph.addEdge(keySource,keySource+i,weight);
        }
    }

    private int generateVerticesNum() {
        Random random = new Random();
        int number;
        int lowVertexNum = 3;
        int highVertexNum = 20;
        number = random.nextInt(highVertexNum - lowVertexNum) + lowVertexNum;
        return number;
    }

    private int generateJunctionNum() {
        Random random = new Random();
        int number;
        int lowerNum = 1;
        int highNum = 3;
        number = random.nextInt(highNum - lowerNum) + lowerNum;
        return number;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public int getJunctionBoxNum() {
        return junctionBoxNum;
    }

    public int getSwitchNum() {
        return vertexNum - junctionBoxNum - 1;
    }

    private int randomWeight() {
        Random random = new Random();
        return random.nextInt(10 - 1) + 1;
    }

}
