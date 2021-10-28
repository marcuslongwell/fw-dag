package dag.fw;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GraphFactory {
    public static Graph createRandom(int minLevel, int maxLevel, int minPerLevel, int maxPerLevel) throws Exception {
        Random random = new Random();
        Graph graph = new Graph();

        if (minLevel < 2) throw new Exception("Minimum level must be at least 2");
        if (minLevel > maxLevel) throw new Exception("Minimum level must be less than or equal to max level");
        if (minPerLevel < 1) throw new Exception("Minimum vertices per level must be at least 1");
        if (minPerLevel > maxPerLevel) throw new Exception("Maximum vertices must be greater than or equal to minimum vertices");

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<Vertex> prevVerticies = new ArrayList<>();
        long vertId = 0;
        int levels = random.nextInt(minLevel, maxLevel + 1);
//        System.out.println("Generating " + String.valueOf(levels) + " levels");
        for (int i = 0; i < levels; i++) {
            int verticesOnLevel = random.nextInt(minPerLevel, maxPerLevel + 1);
//            System.out.println("Vertices on level " + String.valueOf(i) + ": " + String.valueOf(verticesOnLevel));

            ArrayList<Vertex> levelVertices = new ArrayList<>();
            ArrayList<Edge> levelEdges = new ArrayList<>();
            for (int j = 0; j < verticesOnLevel; j++) {
                Vertex vertex = new Vertex(vertId);
                levelVertices.add(vertex);
//                System.out.println("Added vertex: " + vertex);
                vertId++;

                // pick a random vertex on previous level to edge to
                if (!prevVerticies.isEmpty()) {
                    // for the vertices in first row, make sure every vertex has an edge coming from it
                    // if we're not in the first row, we can allow for the graph to terminate at the previous
                    // node, so we don't need to do that
                    int prevIndex = j;
                    if (i == 0) {
                        prevIndex = random.nextInt(0, prevVerticies.size());
                    }
                    Vertex prevVertex = prevVerticies.get(prevIndex);
                    Edge edge = new Edge(prevVertex, vertex);
                    levelEdges.add(edge);
//                    System.out.println("Created edge: " + edge);
                }
            }

            prevVerticies = levelVertices;
            vertices.addAll(levelVertices);
            edges.addAll(levelEdges);
        }

        graph.setVertices(vertices);
        graph.setEdges(edges);

        System.out.println("Created " + String.valueOf(vertices.size()) + " vertices with " + String.valueOf(edges.size()) + " edges");

        return graph;
    }

    public static Graph createRandom(int levels, int perLevel) throws Exception {
        return createRandom(levels, levels, perLevel, perLevel);
    }

    public static Graph createRandom() throws Exception {
        return createRandom(2, 3, 1, 2);
    }

    // creates a tree of nodes so we can test consistently-generated graph of any size with lots of edges
    // i.e. levels of 4 would create
    //    *
    //   ***
    //  *****
    // *******
    // where all nodes have edges to all of the nodes below them
    public static Graph createTree(int levels) throws Exception {
        Graph graph = new Graph();

        if (levels < 2) throw new Exception("Levels must be 2 or greater");

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        long vertId = 0;

        ArrayList<Vertex> prevVertices = new ArrayList<>();
        for (int i = 0; i < levels; i++) {
            ArrayList<Vertex> levelVertices = new ArrayList<>();

            for (int j = 0; j < i * 2 + 1; j++) {
                Vertex vertex = new Vertex(vertId);
                levelVertices.add(vertex);
                vertId++;

                if (!prevVertices.isEmpty()) {
                    for (int k = 0; k < prevVertices.size(); k++) {
                        Edge edge = new Edge(prevVertices.get(k), vertex);
                        edges.add(edge);
                    }
                }
            }

            prevVertices = levelVertices;
            vertices.addAll(levelVertices);
        }

        graph.setVertices(vertices);
        graph.setEdges(edges);

        System.out.println("Created " + String.valueOf(vertices.size()) + " vertices with " + String.valueOf(edges.size()) + " edges");

        return graph;
    }

    // creates a waterfall of nodes so we can test a graph of any size with lots of nodes and little edges
    // i.e. levels of 4 and width of 6 would create
    //       *
    // *  *  *  *  *  *
    // *  *  *  *  *  *
    // *  *  *  *  *  *
    // where all nodes connect only to the node directly above them (except row 1, which connects to single starting node)
    public static Graph createWaterfall(int levels, int width) throws Exception {
        Graph graph = new Graph();

        if (levels < 2) throw new Exception("Levels must be 2 or greater");
        if (width < 1) throw new Exception("Width must be 1 or greater");

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        Vertex firstVirtex = new Vertex(0);
        vertices.add(firstVirtex);
        long vertId = 1;

        ArrayList<Vertex> prevVertices = new ArrayList<>();
        for (int i = 0; i < levels - 1; i++) {
            ArrayList<Vertex> levelVertices = new ArrayList<>();

            for (int j = 0; j < width; j++) {
                Vertex vertex = new Vertex(vertId);
                levelVertices.add(vertex);
                vertId++;

                if (!prevVertices.isEmpty()) {
                    Edge edge = new Edge(prevVertices.get(j), vertex);
                    edges.add(edge);
                } else {
                    Edge edge = new Edge(firstVirtex, vertex);
                    edges.add(edge);
                }
            }

            prevVertices = levelVertices;
            vertices.addAll(levelVertices);
        }

        graph.setVertices(vertices);
        graph.setEdges(edges);

        System.out.println("Created " + String.valueOf(vertices.size()) + " vertices with " + String.valueOf(edges.size()) + " edges");

        return graph;
    }
}
