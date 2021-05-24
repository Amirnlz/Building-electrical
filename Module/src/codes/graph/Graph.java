package codes.graph;

import codes.picture.Picture;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Graph {

    private final int vertices;
    private final LinkedList<Edge>[] adjacencyList;
    private Picture picture;

    private Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList[source].add(edge);
        edge = new Edge(destination, source, weight);
        adjacencyList[destination].add(edge);
    }

    public int getVertices() {
        return vertices;
    }

    public LinkedList<Edge>[] getAdjacencyList() {
        return adjacencyList;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    private Graph pictureToGraph() {
        return null;
    }

    public boolean isCompleteGraph() {
        int conditionOfComplete = vertices * (vertices - 1) / 2;
        return conditionOfComplete == getNumberOfEdge();
    }

    //    should return a graph complete it later
    private void findOptimizedGraph() {
    }

    private void filterGraph() {

    }

    // should return graph
    private Picture getFinalGraph() {
        return null;
    }

    //show filtered graph for gui
    private File drawGraph() throws IOException {
        File myFile = new File("H:\\myPic.jpg");
        if (!myFile.exists())
            myFile.createNewFile();
        this.picture.save(myFile);
        return myFile;
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            LinkedList<Edge> list = adjacencyList[i];
            for (Edge edge : list)
                System.out.println("vertex-" + i + " is connected to " +
                        edge.destination + " with weight " + edge.weight);
        }
    }

    public int getNumberOfEdge() {
        int sum = 0;
        for (int i = 0; i < vertices; i++) {
            sum += adjacencyList[i].size();
        }
        return sum / 2;
    }
}
