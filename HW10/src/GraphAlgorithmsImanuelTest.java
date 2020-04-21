import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for HW10. Has as many tests as I could think of. Some of them are probably redundant.
 *
 * @author Manny Sonubi
 */
public class GraphAlgorithmsImanuelTest {
    private static final int TIMEOUT = 200;

    private Graph<Integer> directedGraph;                       // Same directed graph as in student tests
    private Graph<Character> undirectedGraph;                   // Same undirected graph as in student tests
    private Graph<Integer> emptyIntGraph;                       // Empty Integer graph
    private Graph<Character> emptyCharGraph;                    // Empty Character graph
    private Graph<Character> singleVertexGraph;                 // A graph with a single vertex and no edges
    private Graph<Character> singleVertexSelfLoopGraph;         // A graph with a single vertex and a self looping edge
    private Graph<Character> selfLoopGraph;                     // A graph with three vertices and a self looping edge on A
    private Graph<Character> parallelEdgeGraph;                 // A graph with a parallel edges from A to B
    private Graph<Character> disconnectedGraphMinEdges;         // A disconnected graph with exactly 2(|V| - 1) edges
    private Graph<Character> disconnectedGraphLessThanMinEdges; // A disconnected graph with less than 2(|V| - 1) edges

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();
        emptyIntGraph = new Graph<>(new HashSet<>(), new HashSet<>());
        emptyCharGraph = new Graph<>(new HashSet<>(), new HashSet<>());
        singleVertexGraph = createSingleVertexGraph();
        singleVertexSelfLoopGraph = createSingleVertexSelfLoopGraph();
        selfLoopGraph = createSelfLoopGraph();
        parallelEdgeGraph = createParallelEdgeGraph();
        disconnectedGraphMinEdges = createDisconnectedGraphMinEdges();
        disconnectedGraphLessThanMinEdges = createDisconnectedGraphLessThanMinEdges();
    }

    private Graph<Integer> createDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<Integer>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        return new Graph<Integer>(vertices, edges);
    }

    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('B'), 7);
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('C'), 5);
        addUndirectedEdge(edges, new Vertex<>('C'), new Vertex<>('D'), 2);
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('D'), 4);
        addUndirectedEdge(edges, new Vertex<>('D'), new Vertex<>('E'), 1);
        addUndirectedEdge(edges, new Vertex<>('B'), new Vertex<>('E'), 3);
        addUndirectedEdge(edges, new Vertex<>('B'), new Vertex<>('F'), 8);
        addUndirectedEdge(edges, new Vertex<>('E'), new Vertex<>('F'), 6);

        return new Graph<Character>(vertices, edges);
    }

    // This graph is disconnected but still has the 2(|V| - 1) number of edges
    // needed for a possible MST
    private Graph<Character> createDisconnectedGraphMinEdges() {
        Set<Vertex<Character>> vertices = new HashSet<>();

        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('C'), 5);
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('D'), 4);
        addUndirectedEdge(edges, new Vertex<>('B'), new Vertex<>('F'), 8);
        addUndirectedEdge(edges, new Vertex<>('C'), new Vertex<>('D'), 2);
        addUndirectedEdge(edges, new Vertex<>('D'), new Vertex<>('E'), 1);

        return new Graph<>(vertices, edges);
    }

    // This graph has less than 2(|V| - 1) edges
    private Graph<Character> createDisconnectedGraphLessThanMinEdges() {
        Set<Vertex<Character>> vertices = new HashSet<>();

        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('B'), 7);
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('D'), 4);
        addUndirectedEdge(edges, new Vertex<>('B'), new Vertex<>('F'), 8);
        addUndirectedEdge(edges, new Vertex<>('D'), new Vertex<>('E'), 1);

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createSingleVertexGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<>('A'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createSingleVertexSelfLoopGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<>('A'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 5));

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createSelfLoopGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();

        vertices.add(new Vertex<>('A'));
        vertices.add(new Vertex<>('B'));
        vertices.add(new Vertex<>('C'));


        Set<Edge<Character>> edges = new LinkedHashSet<>();
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('B'), 7);
        addUndirectedEdge(edges, new Vertex<>('B'), new Vertex<>('C'), 4);
        addUndirectedEdge(edges, new Vertex<>('C'), new Vertex<>('A'), 8);
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 5));

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createParallelEdgeGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();

        vertices.add(new Vertex<>('A'));
        vertices.add(new Vertex<>('B'));
        vertices.add(new Vertex<>('C'));


        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 9));
        addUndirectedEdge(edges, new Vertex<>('A'), new Vertex<>('C'), 7);
        addUndirectedEdge(edges, new Vertex<>('B'), new Vertex<>('C'), 4);

        return new Graph<>(vertices, edges);
    }

    private <T> void addUndirectedEdge(Set<Edge<T>> edges, Vertex<T> u, Vertex<T> v, int weight) {
        edges.add(new Edge<T>(u, v, weight));
        edges.add(new Edge<T>(v, u, weight));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBFSNullStartVertex() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(null, directedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBFSNullGraph() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(new Vertex<>(1), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBFSStartVertexDoesNotExist() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(new Vertex<>(420), directedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBFSEmptyGraph() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(new Vertex<>(1), emptyIntGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testBFSSingleVertexGraph() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(new Vertex<>('A'), singleVertexGraph);
        List<Vertex<Character>> bfsExpected = new ArrayList<>();

        bfsExpected.add(new Vertex<>('A'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testBFSSingleVertexSelfLoopGraph() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(new Vertex<>('A'), singleVertexSelfLoopGraph);
        List<Vertex<Character>> bfsExpected = new ArrayList<>();

        bfsExpected.add(new Vertex<>('A'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testBFSSelfLoopGraph() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(new Vertex<>('A'), selfLoopGraph);
        List<Vertex<Character>> bfsExpected = new ArrayList<>();

        bfsExpected.add(new Vertex<>('A'));
        bfsExpected.add(new Vertex<>('B'));
        bfsExpected.add(new Vertex<>('C'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testBFSParallelEdgeGraph() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(new Vertex<>('A'), parallelEdgeGraph);
        List<Vertex<Character>> bfsExpected = new ArrayList<>();

        bfsExpected.add(new Vertex<>('A'));
        bfsExpected.add(new Vertex<>('B'));
        bfsExpected.add(new Vertex<>('C'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDFSNullStartVertex() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(null, directedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDFSNullGraph() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(new Vertex<>(1), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDFSStartVertexDoesNotExist() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(new Vertex<>(420), directedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDFSEmptyGraph() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(new Vertex<>(1), emptyIntGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testDFSSingleVertexGraph() {
        List<Vertex<Character>> dfsActual = GraphAlgorithms.dfs(new Vertex<>('A'), singleVertexGraph);
        List<Vertex<Character>> dfsExpected = new ArrayList<>();

        dfsExpected.add(new Vertex<>('A'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFSSingleVertexSelfLoopGraph() {
        List<Vertex<Character>> dfsActual = GraphAlgorithms.dfs(new Vertex<>('A'), singleVertexSelfLoopGraph);
        List<Vertex<Character>> dfsExpected = new ArrayList<>();

        dfsExpected.add(new Vertex<>('A'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFSSelfLoopGraph() {
        List<Vertex<Character>> dfsActual = GraphAlgorithms.dfs(new Vertex<>('A'), selfLoopGraph);
        List<Vertex<Character>> dfsExpected = new ArrayList<>();

        dfsExpected.add(new Vertex<>('A'));
        dfsExpected.add(new Vertex<>('B'));
        dfsExpected.add(new Vertex<>('C'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFSParallelEdgeGraph() {
        List<Vertex<Character>> dfsActual = GraphAlgorithms.dfs(new Vertex<>('A'), parallelEdgeGraph);
        List<Vertex<Character>> dfsExpected = new ArrayList<>();

        dfsExpected.add(new Vertex<>('A'));
        dfsExpected.add(new Vertex<>('B'));
        dfsExpected.add(new Vertex<>('C'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullStartVertex() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(new Vertex<>('A'), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasStartVertexDoesNotExist() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(new Vertex<>('G'), undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasEmptyGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(new Vertex<>('A'), emptyCharGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasSingleVertexSelfLoopGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), singleVertexSelfLoopGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasSelfLoopGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), selfLoopGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 7);
        dijkExpected.put(new Vertex<>('C'), 8);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasParallelEdgeGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), parallelEdgeGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 6);
        dijkExpected.put(new Vertex<>('C'), 7);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDisconnectedGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), disconnectedGraphMinEdges);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), Integer.MAX_VALUE);
        dijkExpected.put(new Vertex<>('C'), 5);
        dijkExpected.put(new Vertex<>('D'), 4);
        dijkExpected.put(new Vertex<>('E'), 5);
        dijkExpected.put(new Vertex<>('F'), Integer.MAX_VALUE);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKruskalsNullGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(null);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsDisconnectedGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(disconnectedGraphMinEdges);
        assertNull(mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsDisconnectedGraphLessThanMinEdges() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(disconnectedGraphLessThanMinEdges);
        assertNull(mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsEmptyGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(emptyCharGraph);
        assertNull(mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsSingleVertexGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(singleVertexGraph);
        Set<Edge<Character>> mstExpected = new HashSet<>();

        assertEquals(mstExpected, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsSingleVertexSelfLoopGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(singleVertexSelfLoopGraph);
        Set<Edge<Character>> mstExpected = new HashSet<>();

        assertEquals(mstExpected, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsSelfLoopGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(selfLoopGraph);
        Set<Edge<Character>> mstExpected = new HashSet<>();

        addUndirectedEdge(mstExpected, new Vertex<>('B'), new Vertex<>('C'), 4);
        addUndirectedEdge(mstExpected, new Vertex<>('A'), new Vertex<>('B'), 7);

        assertEquals(mstExpected, mstActual);
    }
}