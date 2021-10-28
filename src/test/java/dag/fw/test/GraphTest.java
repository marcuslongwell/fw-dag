package dag.fw.test;

import dag.fw.Edge;
import dag.fw.Graph;
import dag.fw.Vertex;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {
    @Test
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

    @Test
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

    @Test
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
}
