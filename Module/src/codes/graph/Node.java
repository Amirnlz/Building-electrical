package codes.graph;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Node {
    private Point coord = new Point();
    private int id;
    int vertex, weight;
    private java.util.List<Node> path;

    public Node() {
    }

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public Node(int id) {
        this.id = id;
    }

    public Node(Point p) {
        this.coord = p;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoord(int x, int y) {
        coord.setLocation(x, y);
    }

    public Point getCoord() {
        return coord;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public List<Node> getPath() {
        return path;
    }

    public int getX() {
        return (int) coord.getX();
    }

    public int getY() {
        return (int) coord.getY();
    }

    public int getId() {
        return id;
    }
    public int getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }


    @Override
    public String toString() {
        return "Node " + id;
    }
}