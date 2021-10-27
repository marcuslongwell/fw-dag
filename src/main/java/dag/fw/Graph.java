package dag.fw;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Graph {
    public enum PathStrategy {
        SHORTEST,
        LONGEST
    }



    // determines if the vertex has an incoming edge given the supplied edges
    public static boolean hasIncomingEdge(ArrayList<Edge> unaccountedEdges, Vertex vertex) {
        for (Edge edge : unaccountedEdges) {
            if (edge.to.equals(vertex)) return true;
        }

        return false;
    }



    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;



    // constructors
    public Graph() {
        setVertices(new ArrayList<>());
        setEdges(new ArrayList<>());
    }



    // getters/setters
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public Vertex getVertex(int index) {
        return this.vertices.get(index);
    }

    public Vertex getVertex(char letter) throws Exception {
        for (Vertex vertex : vertices) {
            if (vertex.getLetter() == letter) return vertex;
        }

        throw new Exception("Could not find vertex with letter: " + letter);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public Edge getEdge(int index) {
        return this.edges.get(index);
    }



    // determines whether this vertex is a starting vertex
        // (i.e. it has no incoming edges)
    public boolean isStartingVertex(Vertex vertex) {
        for (Edge edge : edges) {
            if (edge.to.equals(vertex)) return false;
        }

        return true;
    }

    // returns a list of vertices that are starting verticies
        // (these are verticies that begin the graph, e.g. they have no incoming edges)
    public ArrayList<Vertex> getStartingVertices() {
        ArrayList<Vertex> startingVerticies = new ArrayList<>();

        for (Vertex vertex : vertices) {
            if (isStartingVertex(vertex)) {
                startingVerticies.add(vertex);
            }
        }

        return startingVerticies;
    }

    // determines whether there is an edge on this graph from the from vertex to the to vertex
        // does NOT check for a path, just a single edge
    public boolean edgeExists(Vertex from, Vertex to) {
        for (Edge edge : edges) {
            if (edge.from.equals(from) && edge.to.equals(to)) {
                return true;
            }
        }

        return false;
    }

    // https://en.wikipedia.org/wiki/Topological_sorting
        // works by shifting finding edge nodes, adding to list in order
        // chopping off edges at each level of depth (thus creating new edge nodes)
        // iterating until there are no more edges to chop off
    public ArrayList<Vertex> kahnSort() {
        ArrayList<Vertex> sorted = new ArrayList<>();
        ArrayList<Vertex> startingVertices = getStartingVertices();
        ArrayList<Edge> unaccountedEdges = new ArrayList<>(edges);

        while (!startingVertices.isEmpty()) {
            Vertex startingVertex = startingVertices.remove(0);
            sorted.add(startingVertex);

            for (int i = 0; i < unaccountedEdges.size(); i++) {
                Edge unaccountedEdge = unaccountedEdges.get(i);

                if (unaccountedEdge.from.equals(startingVertex)) {
                    // would like to iterate via index to reduce complexity, possibly
                    unaccountedEdges.remove(unaccountedEdge);
                    i--;

                    if (!hasIncomingEdge(unaccountedEdges, unaccountedEdge.to)) {
                        startingVertices.add(unaccountedEdge.to);
                    }
                }
            }
        }

//        setVertices(sorted);
        return sorted;
    }

    // https://www.youtube.com/watch?v=TXkDpqjDMHA
        // sorts verticies
        // iterates through each vertex
        // finds attached edges
        // checks to see if that edge is the shortest way to get to vertex
    public long extremePath(Vertex a, Vertex b, PathStrategy strategy) throws Exception {
        // it's likely that in a production environment, we'd want to just mutate the list on the graph
            // sorting once rather than any time we needed to update the path
        ArrayList<Vertex> sortedVertices = kahnSort();

        long[] vertexJumps = new long[sortedVertices.size()];
        int shortOrLong = strategy.equals(PathStrategy.SHORTEST) ? 1 : -1;

        int vertexAIndex = -1;
        int vertexBIndex = -1;
        for (int i = 0; i < sortedVertices.size(); i++) {
            vertexJumps[i] = 0;
            if (sortedVertices.get(i).equals(a)) vertexAIndex = i;
            if (sortedVertices.get(i).equals(b)) vertexBIndex = i;
        }

        if (vertexAIndex < 0) throw new Exception("Could not find vertex: " + a);
        if (vertexBIndex < 0) throw new Exception("Could not find vertex: " + b);

        for (int i = vertexAIndex; i < vertexBIndex; i++) {
            Vertex currVertex = sortedVertices.get(i);

            for (int j = i + 1; j < vertexBIndex + 1; j++) {
                Vertex checkVertex = sortedVertices.get(j);
                if (edgeExists(currVertex, checkVertex)) {
                    // the following assumes there's no weighted edges
                    // (would need to multiply by weight if that were the case)
                    if (vertexJumps[i] + shortOrLong < vertexJumps[j] || vertexJumps[j] == 0) {
                        vertexJumps[j] = vertexJumps[i] + shortOrLong;
                    }
                }
            }
        }

        return shortOrLong * vertexJumps[vertexBIndex];
    }

    // returns the longest path possible between two vertices a and b
    public long longestPath(Vertex a, Vertex b) throws Exception {
        return extremePath(a, b, PathStrategy.LONGEST);
    }

    // returns the shortest path possible between two vertices a and b
    public long shortestPath(Vertex a, Vertex b) throws Exception {
        return extremePath(a, b, PathStrategy.SHORTEST);
    }
}
