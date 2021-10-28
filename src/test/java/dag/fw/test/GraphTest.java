package dag.fw.test;

import dag.fw.Edge;
import dag.fw.Graph;
import dag.fw.GraphFactory;
import dag.fw.Vertex;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {
//    @Test
    public void singleEntrySingleExit() throws Exception {
        Graph graph = new Graph();

        Vertex a = new Vertex(0, 'A');
        Vertex b = new Vertex(1, 'B');
        Vertex c = new Vertex(2, 'C');
        Vertex d = new Vertex(3, 'D');
        Vertex e = new Vertex(4, 'E');
        Vertex f = new Vertex(5, 'F');
        Vertex h = new Vertex(6, 'H');
        Vertex g = new Vertex(7, 'G');
        Vertex j = new Vertex(8, 'J');
        Vertex k = new Vertex(9, 'K');

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(h);
        graph.addVertex(g);
        graph.addVertex(j);
        graph.addVertex(k);

        graph.addEdge(new Edge(a, b));
        graph.addEdge(new Edge(b, c));
        graph.addEdge(new Edge(c, d));
        graph.addEdge(new Edge(c, e));
        graph.addEdge(new Edge(e, f));
        graph.addEdge(new Edge(d, g));
        graph.addEdge(new Edge(f, h));
        graph.addEdge(new Edge(h, g));
        graph.addEdge(new Edge(a, j));
        graph.addEdge(new Edge(j, k));
        graph.addEdge(new Edge(k, g));

        long ag = graph.longestPath(a, g);
        long jg = graph.longestPath(j, g);
        long ch = graph.longestPath(c, h);
        long cEnd = graph.longestPath(c);

        assertEquals(6, ag);
        assertEquals(2, jg);
        assertEquals(3, ch);
        assertEquals(4, cEnd);
    }

//    @Test
    public void multipleEntryMultipleExit() throws Exception {
        Graph graph = new Graph();

        Vertex a = new Vertex(0, 'A');
        Vertex b = new Vertex(1, 'B');
        Vertex c = new Vertex(2, 'C');
        Vertex d = new Vertex(3, 'D');
        Vertex e = new Vertex(4, 'E');
        Vertex f = new Vertex(5, 'F');
        Vertex h = new Vertex(6, 'H');
        Vertex g = new Vertex(7, 'G');
        Vertex j = new Vertex(8, 'J');
        Vertex k = new Vertex(9, 'K');

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(j);
        graph.addVertex(k);

        graph.addEdge(new Edge(a, j));
        graph.addEdge(new Edge(b, j));
        graph.addEdge(new Edge(j, k));
        graph.addEdge(new Edge(j, d));
        graph.addEdge(new Edge(k, c));
        graph.addEdge(new Edge(k, g));
        graph.addEdge(new Edge(d, g));
        graph.addEdge(new Edge(c, e));
        graph.addEdge(new Edge(e, h));
        graph.addEdge(new Edge(h, f));
        graph.addEdge(new Edge(c, g));

        long af = graph.longestPath(a, f);
        long aEnd = graph.longestPath(a);
        long kh = graph.longestPath(k, h);
        long bf = graph.longestPath(b, f);
        long bEnd = graph.longestPath(b);
        long dEnd = graph.longestPath(d);
        long ag = graph.longestPath(a, g);

        assertEquals(6, af);
        assertEquals(af, aEnd);
        assertEquals(3, kh);
        assertEquals(af, bf);
        assertEquals(aEnd, bEnd);
        assertEquals(1, dEnd);
        assertEquals(4, ag);
    }

//    @Test
    public void singleEntryMultipleExit() throws Exception {
        Graph graph = new Graph();

        Vertex a = new Vertex(0, 'A');
        Vertex c = new Vertex(2, 'C');
        Vertex d = new Vertex(3, 'D');
        Vertex e = new Vertex(4, 'E');
        Vertex f = new Vertex(5, 'F');
        Vertex h = new Vertex(6, 'H');
        Vertex g = new Vertex(7, 'G');
        Vertex j = new Vertex(8, 'J');
        Vertex k = new Vertex(9, 'K');

        graph.addVertex(a);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(j);
        graph.addVertex(k);

        graph.addEdge(new Edge(a, j));
        graph.addEdge(new Edge(j, k));
        graph.addEdge(new Edge(k, c));
        graph.addEdge(new Edge(k, g));
        graph.addEdge(new Edge(g, d));
        graph.addEdge(new Edge(c, e));
        graph.addEdge(new Edge(e, h));
        graph.addEdge(new Edge(h, f));
        graph.addEdge(new Edge(c, g));

        long af = graph.longestPath(a, f);
        long aEnd = graph.longestPath(a);
        long kg = graph.longestPath(k, g);
        long jd = graph.longestPath(j, d);
        long cEnd = graph.longestPath(c);

        assertEquals(6, af);
        assertEquals(af, aEnd);
        assertEquals(2, kg);
        assertEquals(4, jd);
        assertEquals(3, cEnd);
    }

//    @Test
    public void multipleEntrySingleExit() throws Exception {
        Graph graph = new Graph();

        Vertex c = new Vertex(0, 'C');
        Vertex a = new Vertex(1, 'A');
        Vertex d = new Vertex(2, 'D');
        Vertex e = new Vertex(3, 'E');
        Vertex f = new Vertex(4, 'F');
        Vertex h = new Vertex(5, 'H');
        Vertex g = new Vertex(6, 'G');
        Vertex j = new Vertex(7, 'J');
        Vertex k = new Vertex(8, 'K');
        Vertex b = new Vertex(9, 'B');

        graph.addVertex(a);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(j);
        graph.addVertex(k);
        graph.addVertex(b);

        graph.addEdge(new Edge(c, k));
        graph.addEdge(new Edge(a, j));
        graph.addEdge(new Edge(b, j));
        graph.addEdge(new Edge(j, d));
        graph.addEdge(new Edge(j, k));
        graph.addEdge(new Edge(k, g));
        graph.addEdge(new Edge(d, g));
        graph.addEdge(new Edge(g, e));
        graph.addEdge(new Edge(g, f));
        graph.addEdge(new Edge(e, h));
        graph.addEdge(new Edge(h, f));

        long af = graph.longestPath(a, f);
        long aEnd = graph.longestPath(a);
        long kg = graph.longestPath(k, g);
        long cf = graph.longestPath(c, f);
        long cEnd = graph.longestPath(c);
        long bEnd = graph.longestPath(b);
        long dEnd = graph.longestPath(d);
        long ad = graph.longestPath(a, d);

        assertEquals(6, af);
        assertEquals(af, aEnd);
        assertEquals(1, kg);
        assertEquals(5, cf);
        assertEquals(cf, cEnd);
        assertEquals(aEnd, bEnd);
        assertEquals(4, dEnd);
        assertEquals(2, ad);
//        assertEquals(4, jd);
//        assertEquals(3, cEnd);
    }

    // generate 1,000 graphs of 1,000 vertices each and get their longest path to make sure
    // that we're not encountering any unexpected issues with random graph generation
//    @Test
    public void randomGraphsAreValid() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Graph graph = GraphFactory.createRandom(10, 100);
            graph.longestPath(graph.getVertex(0));
        }
    }

    // goal: make sure a graph of 25 vertices doesn't exceed 100ms to create and traverse
//    @Test(timeout = 100)
    public void smallTraversal() throws Exception {
        Graph graph = GraphFactory.createRandom(5, 5);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make sure a graph of 10,000 vertices doesn't exceed 1 second to create and traverse
//    @Test(timeout = 1000)
    public void mediumTraversal() throws Exception {
        Graph graph = GraphFactory.createRandom(100, 100);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make sure a graph of ~500k vertices doesn't exceed 3 seconds to create and traverse
//    @Test(timeout = 3000)
    public void largeTraversal() throws Exception {
        Graph graph = GraphFactory.createRandom(750, 750);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make sure a graph of a million vertices doesn't exceed 5 seconds to create and traverse
//    @Test(timeout = 5000)
    public void hugeTraversal() throws Exception {
        Graph graph = GraphFactory.createRandom(1000, 1000);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make sure a very tall dag (lots of levels, less nodes per level) doesn't exceed 5 seconds to create and traverse
//    @Test(timeout = 5000)
    public void hugeTallTraversal() throws Exception {
        Graph graph = GraphFactory.createRandom(10000, 100);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make sure a very wide dag (less levels, lots of nodes per level) doesn't exceed 5 seconds to create and traverse
//    @Test(timeout = 5000)
    public void hugeWideTraversal() throws Exception {
        Graph graph = GraphFactory.createRandom(100, 10000);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make a dag that looks like a tree with lots of edges (all nodes connected to parent level nodes) doesn't exceed 10 seconds to create and traverse
    // this has 961 vertices and 37,790 edges, scaling levels up significantly increases that number
//    @Test(timeout = 10000)
    @Test
    public void hugeTree() throws Exception {
        Graph graph = GraphFactory.createTree(31);
        graph.longestPath(graph.getVertex(0));
    }

    // goal: make a dag that looks like a waterfall (nodes only connected to parent node at same index on level) and doesn't exceed 10 seconds to create and traverse
    // this has 2,971 vertices and 2,970 edges
//    @Test(timeout = 10000)
    @Test
    public void hugeWaterfall() throws Exception {
        Graph graph = GraphFactory.createWaterfall(55, 55);
        graph.longestPath(graph.getVertex(0));
    }


}

