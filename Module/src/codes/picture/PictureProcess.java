package codes.picture;

import codes.graph.Edge;
import codes.graph.Graph;
import codes.graph.Node;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PictureProcess {

    File file;
    private final int BLACK_CODE = -16777216;
    private final int WHITE_CODE = -1;
    private final int RED_CODE = 16711680;
    private final int GREEN_CODE = 65280;
    private final int YELLOW_CODE = 16776960;
    private final static List<int[]> objectsLocation = new ArrayList<>();
    private final static List<int[]> keysLocation = new ArrayList<>();
    private final static List<int[]> sourceLocation = new ArrayList<>();
    private final static List<int[]> objectsDistance = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private JLabel label = new JLabel();

    //this method just for test
    public void startProcess() {
        Picture picture = removeNoises();
        picture = extendPicture(100, picture);
        findObjects(picture);
        label = picture.getJLabel();
        sourceIndexChanger();
        printobjectsLocation();
        printkeysLocation();
        printSourceLocation();
        findDistance(picture);
        printobjectsDistance();
        final int N = objectsLocation.size();
        Graph graph = new Graph(edges, N);
        int source = 0;
        findShortestPaths(graph, source, N, picture);
        minimize(picture).show();
    }

    public void findDistance(Picture picture) {
        for (int i = 0; i < objectsLocation.size(); i++) {
            for (int j = i; j < objectsLocation.size(); j++) {
                if (i != j) {
                    //same x
                    if ((objectsLocation.get(i)[0] == objectsLocation.get(j)[0] || Math.abs(objectsLocation.get(i)[0] - objectsLocation.get(j)[0]) < 5)) {
                        if (checkDoorX(objectsLocation.get(i)[0], objectsLocation.get(i)[1], objectsLocation.get(j)[1], picture)) {
                            insertToObjectsDistanceList(
                                    i,
                                    objectsLocation.get(i)[0],
                                    objectsLocation.get(i)[1],
                                    j,
                                    objectsLocation.get(j)[0],
                                    objectsLocation.get(j)[1],
                                    Math.abs(objectsLocation.get(i)[1] - objectsLocation.get(j)[1])
                            );
                        }
                    }
                    //same y
                    if ((objectsLocation.get(i)[1] == objectsLocation.get(j)[1] || Math.abs(objectsLocation.get(i)[1] - objectsLocation.get(j)[1]) < 5)) {
                        //System.out.println("eror!"+objectsLocation.get(i)[1]+","+objectsLocation.get(i)[0]+","+objectsLocation.get(j)[0]);
                        if (checkDoorY(objectsLocation.get(i)[1], objectsLocation.get(i)[0], objectsLocation.get(j)[0], picture)) {
                            insertToObjectsDistanceList(
                                    i,
                                    objectsLocation.get(i)[0],
                                    objectsLocation.get(i)[1],
                                    j,
                                    objectsLocation.get(j)[0],
                                    objectsLocation.get(j)[1],
                                    Math.abs(objectsLocation.get(i)[0] - objectsLocation.get(j)[0])
                            );
                        }
                    }

                }
            }
        }
    }

    public boolean checkDoorX(int X, int fromY, int toY, Picture pic) {
        if (fromY < toY) {
            for (int i = fromY; i < toY; i++) {
                if (pic.getRGB(X + 4, i) == WHITE_CODE) {
                    return false;
                }
            }
        } else {
            for (int i = toY; i < fromY; i++) {
                if (pic.getRGB(X + 4, i) == WHITE_CODE) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkDoorY(int Y, int fromX, int toX, Picture pic) {
        if (fromX < toX) {
            for (int i = fromX; i < toX; i++) {
                if (pic.getRGB(i, Y + 4) == WHITE_CODE) {
                    return false;
                }
            }

        } else {
            for (int i = toX; i < fromX; i++) {
                if (pic.getRGB(i, Y + 4) == WHITE_CODE) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printobjectsLocation() {
        System.out.println("All Nodes (x,y)");
        int i = 0;
        for (int[] point : objectsLocation) {
            System.out.println(i + ":(" + point[0] + "," + point[1] + ")");
            i++;
        }
    }

    public void printkeysLocation() {
        System.out.println("--------------------------------");
        System.out.println("Key Nodes (x,y)");
        for (int[] point : keysLocation) {
            System.out.println("(" + point[0] + "," + point[1] + ")");
        }
    }

    public void printSourceLocation() {
        System.out.println("--------------------------------");
        System.out.println("Source Node (x,y)");
        for (int[] point : sourceLocation) {
            System.out.println("(" + point[0] + "," + point[1] + ")");
        }
    }

    public void printobjectsDistance() {
        System.out.println("--------------------------------");
        System.out.println("from (x,y) - to (x,y) - distance");
        for (int[] point : objectsDistance) {
            System.out.println("index " + point[0] + ":(" + point[1] + "," + point[2] + ")->index " + point[3] + ":(" + point[4] + "," + point[5] + "):" + point[6]);
            edges.add(new Edge(point[0], point[3], point[6]));
            edges.add(new Edge(point[3], point[0], point[6]));
        }
        System.out.println("--------------------------------");
    }

    public static Picture printShortestPathX(int X, int fromY, int toY, Picture pic) {
        if (fromY < toY) {
            for (int i = fromY; i < toY; i++) {
                for (int j = X; j < X + 14; j++) {
                    pic.setRGB(j, i, -115342);
                }
            }
        } else {
            for (int i = toY; i < fromY; i++) {
                for (int j = X; j < X + 14; j++) {
                    pic.setRGB(j, i, -115342);
                }
            }
        }

        return pic;
    }

    public static Picture printShortestPathY(int Y, int fromX, int toX, Picture pic) {
        if (fromX < toX) {
            for (int i = fromX; i < toX; i++) {
                for (int j = Y; j < Y + 14; j++) {
                    pic.setRGB(i, j, -115342);
                }
            }
        } else {
            for (int i = toX; i < fromX; i++) {
                for (int j = Y; j < Y + 14; j++) {
                    pic.setRGB(i, j, -115342);
                }
            }
        }

        return pic;
    }

    public void sourceIndexChanger() {
        for (int i = 0; i < objectsLocation.size(); i++) {
            if (objectsLocation.get(i)[0] == sourceLocation.get(0)[0] && objectsLocation.get(i)[1] == sourceLocation.get(0)[1]) {
                int tempX = objectsLocation.get(0)[0];
                int tempY = objectsLocation.get(0)[1];
                int[] array = {tempX, tempY};
                objectsLocation.set(i, array);
                int[] array2 = {sourceLocation.get(0)[0], sourceLocation.get(0)[1]};
                objectsLocation.set(0, array2);
            }

        }
    }

    public Picture removeNoises() {
        Picture picture = new Picture(this.file);

        int width = picture.width();
        int height = picture.height();
        int divide = 3;
        int divided = divide * divide;

        Picture minimized = new Picture(width / divide, height / divide);//picture for minimize origin picture and get average
        Picture filtered = new Picture(width / divide, height / divide);//picture for copying minimized pic

        for (int i = 0; i < width / divide; i++) {
            for (int j = 0; j < height / divide; j++) {
                filtered.setRGB(i, j, -1);
                //minimizing origin picture and get average
                int rgb = average(i, j, divide, picture);
                minimized.setRGB(i, j, rgb / divided);
                //check if pixels are black then copy to filtered graph
                if (minimized.getRGB(i, j) >= -16777216 && minimized.getRGB(i, j) <= -16178526)
                    filtered.setRGB(i, j, -16777216);
                //copy filtered picture into origin picture and return to primary size
                backToOriginSize(i, j, divide, picture, filtered);
            }
        }
        return picture;
    }

    private int average(int i, int j, int divide, Picture picture) {
        int temp = 0;
        for (int k = i * divide; k < (i + 1) * divide; k++) {
            for (int h = j * divide; h < (j + 1) * divide; h++) {
                temp += picture.getRGB(k, h);
            }
        }
        return temp;
    }

    private void backToOriginSize(int i, int j, int divide, Picture picture, Picture filtered) {
        for (int k = i * divide; k < (i + 1) * divide; k++) {
            for (int h = j * divide; h < (j + 1) * divide; h++) {
                picture.setRGB(k, h, filtered.getRGB(i, j));
            }
        }
    }

    private Picture extendPicture(int extendNumber, Picture picture) {
        Picture extendedPicture = new Picture(picture.width() + extendNumber, picture.height() + extendNumber);
        fillWhite(extendedPicture);
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                extendedPicture.setRGB(j + (extendNumber / 2), i + (extendNumber / 2), picture.getRGB(j, i));
            }
        }
        return extendedPicture;
    }

    private void fillWhite(Picture picture) {
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                picture.setRGB(j, i, -1);
            }
        }
    }

    public void findObjects(Picture pic) {
        for (int i = 0; i < pic.height(); i++)
            for (int j = 0; j < pic.width(); j++) {
                findSource(i, j, pic);
                findKeys(i, j, pic);
            }

        for (int i = 0; i < pic.height(); i++)
            for (int j = 0; j < pic.width(); j++)
                findBoxes(i, j, pic);

    }

    private void findSource(int i, int j, Picture pic) {
        if (isSource(i, j, pic)) {
            if (i < pic.height() / 2 && j < pic.width() / 2) {
                showNode(i + 12, j + 12, pic, "source");
            }
            if (i < pic.height() / 2 && j > pic.width() / 2) {
                showNode(i + 12, j, pic, "source");
            }
            if (i > pic.height() / 2 && j < pic.width() / 2) {
                showNode(i, j + 14, pic, "source");
            }
            if (i > pic.height() / 2 && j > pic.width() / 2) {
                showNode(i, j, pic, "source");
            }
        }
    }

    private boolean isSource(int y, int x, Picture picture) {
        if (y + 43 <= picture.height() && x + 63 <= picture.width()) {
            for (int i = y; i < y + 43; i++) {
                for (int j = x; j < x + 63; j++) {
                    if (picture.getRGB(j, i) != BLACK_CODE) {
                        return false;
                    }
                }
            }
        } else
            return y + 43 <= picture.height() && x + 63 <= picture.width();
        return true;
    }

    private void findKeys(int i, int j, Picture pic) {
        if (isKey(i, j, pic)) {
            if (i - 10 >= 0 && j - 10 >= 0) {
                if (pic.getRGB(j, i - 10) == BLACK_CODE && pic.getRGB(j - 10, i) == WHITE_CODE) {
                    showNode(i, j + 12, pic, "key");
                }
                if (pic.getRGB(j, i - 10) == WHITE_CODE && pic.getRGB(j - 10, i) == BLACK_CODE) {
                    showNode(i + 12, j, pic, "key");
                }
                if (pic.getRGB(j, i - 10) == WHITE_CODE && pic.getRGB(j - 10, i) == WHITE_CODE) {
                    showNode(i, j, pic, "key");
                }
            } else {
                if (j < 5) {
                    showNode(i, j + 12, pic, "key");
                } else {
                    showNode(i, j, pic, "key");
                }
            }
        }
    }

    private boolean isKey(int y, int x, Picture picture) {
        int size = 42;
        if (y + size <= picture.height() && x + size <= picture.width()) {
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    if (picture.getRGB(j, i) != BLACK_CODE) {
                        return false;
                    }
                }
            }
        } else
            return y + size <= picture.height() && x + size <= picture.width();
        return true;
    }

    private void findBoxes(int i, int j, Picture pic) {
        if (isBox(i, j, pic))
            showNode(i, j, pic, "box");
    }

    private boolean isBox(int y, int x, Picture picture) {
        int size = 8;
        if (y + size <= picture.height() && x + size <= picture.width()) {
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    if (i - size >= 0 && i + size < picture.height() && j - size >= 0 && j + size < picture.width()) {
                        if (picture.getRGB(j, i) != BLACK_CODE) {
                            return false;
                        } else {
                            if (picture.getRGB(j, i + (size * 2)) == -256 || picture.getRGB(j, i - (size * 2)) == -256 || picture.getRGB(j + (size * 2), i) == -256 || picture.getRGB(j - (size * 2), i) == -256) {
                                return false;
                            }
                            if (picture.getRGB(j, i + (size * 3)) == -256 || picture.getRGB(j, i - (size * 3)) == -256 || picture.getRGB(j + (size * 3), i) == -256 || picture.getRGB(j - (size * 3), i) == -256) {
                                return false;
                            }
                            if ((picture.getRGB(j, i + size) != WHITE_CODE && picture.getRGB(j, i - size) != WHITE_CODE) &&
                                    ((picture.getRGB(j + size, i) == WHITE_CODE && picture.getRGB(j - size, i) == WHITE_CODE) || (picture.getRGB(j + size, i) == -65536 || picture.getRGB(j - size, i) == -65536))) {
                                return false;
                            }

                            if (((picture.getRGB(j, i + size) == WHITE_CODE && picture.getRGB(j, i - size) == WHITE_CODE) || (picture.getRGB(j, i + size) == -65536 || picture.getRGB(j, i - size) == -65536)) &&
                                    (picture.getRGB(j + size, i) != WHITE_CODE && picture.getRGB(j - size, i) != WHITE_CODE)) {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return y + size <= picture.height() && x + size <= picture.width();
        }
        return true;
    }

    private void showNode(int y, int x, Picture picture, String flag) {
        switch (flag) {
            case "source":
                showSource(x, y, picture);
                break;
            case "key":
                showKey(x, y, picture);
                break;
            case "box":
                showBox(x, y, picture);
                break;
        }
    }

    private void showSource(int x, int y, Picture picture) {
        for (int i = y; i < y + 43; i++) {
            for (int j = x; j < x + 60; j++) {
                picture.setRGB(j, i, RED_CODE);
            }
        }
    }

    private void showKey(int x, int y, Picture picture) {
        for (int i = y; i < y + 42; i++) {
            for (int j = x; j < x + 42; j++) {
                picture.setRGB(j, i, GREEN_CODE);
            }
        }
    }

    private void showBox(int x, int y, Picture picture) {
        int size = 4;
        for (int i = y; i < y + 14; i++) {
            for (int j = x; j < x + 14; j++) {
                if (picture.getRGB(j + size, i + size) == -65536 || picture.getRGB(j - size, i + size) == -65536 || picture.getRGB(j + size, i - size) == -65536 || picture.getRGB(j - size, i - size) == -65536) {
                    sourceLocation.clear();
                    insertToSourceLocationList(x, y);
                }
            }
        }

        if (picture.getRGB(x + size * 3, y) == -16711936 || picture.getRGB(x - size * 3, y) == -16711936 || picture.getRGB(x, y + size * 3) == -16711936 || picture.getRGB(x, y - size * 3) == -16711936) {
            insertToKeysLocationList(x, y);
        }

        insertToObjectsLocationList(x, y);
        for (int i = y; i < y + 14; i++) {
            for (int j = x; j < x + 14; j++) {
                picture.setRGB(j, i, YELLOW_CODE);
            }
        }
    }

    private void insertToObjectsLocationList(int i, int j) {
        objectsLocation.add(new int[]{i, j});
    }

    private void insertToKeysLocationList(int i, int j) {
        keysLocation.add(new int[]{i, j});
    }

    private void insertToSourceLocationList(int i, int j) {
        sourceLocation.add(new int[]{i, j});
    }

    private void insertToObjectsDistanceList(int index1, int x1, int y1, int index2, int x2, int y2, int distance) {
        objectsDistance.add(new int[]{index1, x1, y1, index2, x2, y2, distance});
    }

    public void setFile(File file) {
        this.file = file;
    }

    private Picture minimize(Picture picture) {

        int div = 2;
        if (picture.width() > 2400 || picture.height() > 2000)
            div = 3;

        Picture pic = new Picture(picture.width() / div, picture.height() / div);

        for (int i = 0; i < pic.height(); i++) {
            for (int j = 0; j < pic.width(); j++) {
                int average = 0;
                for (int k = i * div; k < (i + 1) * div; k++) {
                    for (int h = j * div; h < (j + 1) * div; h++) {
                        average += picture.getRGB(h, k);
                    }
                }

                pic.setRGB(j, i, average / (div * div));
            }
        }

        return pic;
    }

    private static void getRoute(int[] prev, int i, List<Integer> route) {
        if (i >= 0) {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }

    // Run Dijkstra\92s algorithm on a given graph
    public static void findShortestPaths(Graph graph, int source, int N, Picture picture) {
        // create a min-heap and push source node having distance 0
        PriorityQueue<Node> minHeap;
        minHeap = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
        minHeap.add(new Node(source, 0));

        // set initial distance from the source to `v` as INFINITY
        List<Integer> dist;
        dist = new ArrayList<>(Collections.nCopies(N, Integer.MAX_VALUE));

        // distance from the source to itself is zero
        dist.set(source, 0);

        // boolean array to track vertices for which minimum
        // cost is already found
        boolean[] done = new boolean[N];
        done[source] = true;

        // stores predecessor of a vertex (to a print path)
        int[] prev = new int[N];
        prev[source] = -1;

        List<Integer> route = new ArrayList<>();

        // run till min-heap is empty
        while (!minHeap.isEmpty()) {
            // Remove and return the best vertex
            Node node = minHeap.poll();

            // get the vertex number
            int u = node.getVertex();

            // do for each neighbor `v` of `u`
            for (Edge edge : graph.getAdjList().get(u)) {
                int v = edge.getDest();
                int weight = edge.getWeight();

                // Relaxation step
                if (!done[v] && (dist.get(u) + weight) < dist.get(v)) {
                    dist.set(v, dist.get(u) + weight);
                    prev[v] = u;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }

            // mark vertex `u` as done so it will not get picked up again
            done[u] = true;
        }

        for (int i = 1; i < N; i++) {
            if (i != source && dist.get(i) != Integer.MAX_VALUE) {
                getRoute(prev, i, route);
                if (checkKeyFound(i)) {
                    for (int k = 0; k < route.size() - 1; k++) {
                        int index1 = route.get(k);
                        int index2 = route.get(k + 1);
                        if (objectsLocation.get(index1)[0] == objectsLocation.get(index2)[0] ||
                                Math.abs(objectsLocation.get(index1)[0] - objectsLocation.get(index2)[0]) < 5) {
                            printShortestPathX(objectsLocation.get(index1)[0], objectsLocation.get(index1)[1], objectsLocation.get(index2)[1], picture);
                        }
                        if (objectsLocation.get(index1)[1] == objectsLocation.get(index2)[1] ||
                                Math.abs(objectsLocation.get(index1)[1] - objectsLocation.get(index2)[1]) < 5) {
                            printShortestPathY(objectsLocation.get(index1)[1], objectsLocation.get(index1)[0], objectsLocation.get(index2)[0], picture);
                        }
                    }
                    System.out.println("go from source with coordinates:(" + objectsLocation.get(source)[0] +
                            "," + objectsLocation.get(source)[1] + ") to key with coordinates:(" + objectsLocation.get(i)[0] + "," +
                            objectsLocation.get(i)[1] + ") with minimum cost " + dist.get(i) + " by route:" + route);
                }
                route.clear();
            }
        }
    }

    public static boolean checkKeyFound(int index) {
        for (int i = 0; i < objectsLocation.size(); i++) {
            for (int[] ints : keysLocation) {
                if (objectsLocation.get(index)[0] == ints[0] && objectsLocation.get(index)[1] == ints[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public JLabel getLabel() {
        return this.label;
    }
}