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
    public ArrayList<Vertex> kahnSort() throws Exception {
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

        if (unaccountedEdges.size() > 0) throw new Exception("Invalid DAG. Not all edges are connected to vertices.");

//        setVertices(sorted);
        return sorted;
    }

    // https://www.youtube.com/watch?v=TXkDpqjDMHA
        // sorts verticies
        // iterates through each vertex
        // finds attached edges
        // checks to see if that edge is the shortest way to get to vertex
    public long extremePath(Vertex a, Vertex b, PathStrategy strategy) throws Exception {
        if (vertices.size() < 2) throw new Exception("Invalid DAG. Must have at least two nodes");
        if (edges.size() < 1) throw new Exception("Invalid DAG. Must have at least one edge");

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
        if (vertexBIndex <= vertexAIndex) throw new Exception("Vertex " + a + " has no path to vertex " + b);

        // have to keep track of ancestral nodes so that we don't
        // include nodes in the search that were sorted after our starting node
        // but didn't come from that node
        ArrayList<Vertex> ancestralNodes = new ArrayList<>();
        ancestralNodes.add(a);

        for (int i = vertexAIndex; i < vertexBIndex; i++) {
            Vertex currVertex = sortedVertices.get(i);
            if (!ancestralNodes.contains(currVertex)) continue;

            for (int j = i + 1; j < vertexBIndex + 1; j++) {
                Vertex checkVertex = sortedVertices.get(j);
                if (edgeExists(currVertex, checkVertex)) {
                    ancestralNodes.add(checkVertex);
                    // the following assumes there's no weighted edges
                    // (would need to multiply by weight if that were the case)
                    if (vertexJumps[i] + shortOrLong < vertexJumps[j] || vertexJumps[j] == 0) {
                        vertexJumps[j] = vertexJumps[i] + shortOrLong;
                    }
                }
            }
        }

        long highestJumps = shortOrLong * vertexJumps[vertexBIndex];
        if (highestJumps < 1) throw new Exception("Invalid DAG. No nodes connected to " + b);

        return highestJumps;
    }

    // wasn't sure if we needed to find the longest path of any path
    // or if longest path specifically from point a to b
    // so I decided to implement a function that would do the former
    // with more time, I would find a way to do this with a single function
    // that is re-used by these two functions
    public long extremePath(Vertex vertex, PathStrategy strategy) throws Exception {
        // it's likely that in a production environment, we'd want to just mutate the list on the graph
        // sorting once rather than any time we needed to update the path
        ArrayList<Vertex> sortedVertices = kahnSort();

        long[] vertexJumps = new long[sortedVertices.size()];
        int shortOrLong = strategy.equals(PathStrategy.SHORTEST) ? 1 : -1;

        int vertexIndex = -1;
        for (int i = 0; i < sortedVertices.size(); i++) {
            vertexJumps[i] = 0;
            if (sortedVertices.get(i).equals(vertex)) vertexIndex = i;
        }

        if (vertexIndex < 0) throw new Exception("Could not find vertex: " + vertex);

        // have to keep track of ancestral nodes so that we don't
        // include nodes in the search that were sorted after our starting node
        // but didn't come from that node
        ArrayList<Vertex> ancestralNodes = new ArrayList<>();
        ancestralNodes.add(vertex);

        for (int i = vertexIndex; i < sortedVertices.size() - 1; i++) {
            Vertex currVertex = sortedVertices.get(i);
            if (!ancestralNodes.contains(currVertex)) continue;

            for (int j = i + 1; j < sortedVertices.size(); j++) {
                Vertex checkVertex = sortedVertices.get(j);
                if (edgeExists(currVertex, checkVertex)) {
                    ancestralNodes.add(checkVertex);
                    // the following assumes there's no weighted edges
                    // (would need to multiply by weight if that were the case)
                    if (vertexJumps[i] + shortOrLong < vertexJumps[j] || vertexJumps[j] == 0) {
                        vertexJumps[j] = vertexJumps[i] + shortOrLong;
                    }
                }
            }
        }

        long highestJumps = 0;
        for (long jump : vertexJumps) {
            if (jump < highestJumps) highestJumps = jump;
        }

        highestJumps *= shortOrLong;

        if (highestJumps < 1) throw new Exception("Invalid DAG. No nodes connected from " + vertex);

        return highestJumps;
    }


    // returns the longest path possible between two vertices a and b
    public long longestPath(Vertex a, Vertex b) throws Exception {
        return extremePath(a, b, PathStrategy.LONGEST);
    }

    public long longestPath(Vertex vertex) throws Exception {
        return extremePath(vertex, PathStrategy.LONGEST);
    }

    // returns the shortest path possible between two vertices a and b
    // had to make sure that shortest path was working before could flip to longest path
    public long shortestPath(Vertex a, Vertex b) throws Exception {
        return extremePath(a, b, PathStrategy.SHORTEST);
    }
}
