package codes.graph;

import java.io.File;
import java.util.LinkedList;


public class Graph {

    private File file;
    private final int vertices;
    private final LinkedList<Edge>[] adjacencyList;

    Graph (int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        //initialize adjacency lists for all the vertices
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList[source].addFirst(edge); //for directed graph
    }

    private File getFile() {
        return file;
    }

    private void setFile(File file) {
        this.file = file;
    }

    private File pictureToGraph() {
        return null;
    }


    private boolean isCompleteGraph() {
        return false;
    }

    private void setWeight() {

    }

    //    should return a graph complete it later
    private void findOptimizedGraph() {
//        PrimeAlgorithm getPrime=new PrimeAlgorithm();
//        return getPrime.Prime(this.file);
    }

    private void filterGraph() {

    }

    // should return graph
    private File getFinalGraph() {
        return null;
    }

    private File drawGraph() {
        return null;
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            LinkedList<Edge> list = adjacencyList[i];
            for (Edge edge : list) {
                System.out.println("vertex-" + i + " is connected to " +
                        edge.destination + " with weight " + edge.weight);
            }
        }
    }
}
