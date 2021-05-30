package codes.graph;

import java.util.Random;

public class GraphGenerate {

    private int junctionBoxNum;
    private int switchNum;
    private int vertexNum;
    private Graph graph;

    public void createGraph() {
        Graph generatedGraph;
        do {
            generateNumbers();
            generatedGraph = new Graph(vertexNum);
            linkVertices(generatedGraph);
            setGraph(generatedGraph);
        } while (generatedGraph.isCompleteGraph());
    }

    private void generateNumbers() {
        this.vertexNum = generateVerticesNum();
        this.junctionBoxNum = generateJunctionNum();
        this.switchNum = getSwitchNum();
    }

    private void linkVertices(Graph graph) {
        linkJunctionToSource(graph);
        linkSwitchesToJunction(graph);
        linkSwitchesToEach(graph);
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

    private void linkSwitchesToEach(Graph graph) {
        int keySource = junctionBoxNum + 1;
        for (int i = 0; i < switchNum; i++) {
            int weight = randomWeight();
            graph.addEdge(keySource, keySource + i, weight);
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

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    private int randomWeight() {
        Random random = new Random();
        return random.nextInt(10 - 1) + 1;
    }

}
