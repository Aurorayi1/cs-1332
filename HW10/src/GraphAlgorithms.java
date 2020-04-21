import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Wenye Yi
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        boolean exists = graph.getAdjList().containsKey(start);

        if (!exists) {
            throw new IllegalArgumentException(
                    "Vertex does not exist in the graph");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjacent =
                graph.getAdjList();

        List<Vertex<T>> vertices = new ArrayList<>();
        vertices.add(start);

        Queue<Vertex<T>> nodes = new LinkedList<>();
        nodes.add(start);

        int index = 0;

        while (!nodes.isEmpty()) {
            Vertex<T> searching = nodes.remove();
            List<VertexDistance<T>> adjacentVertices =
                    adjacent.get(searching);

            for (VertexDistance<T> vertexDistance : adjacentVertices) {
                Vertex<T> child = vertexDistance.getVertex();
                if (!vertices.contains(child)) {
                    nodes.add(child);
                    vertices.add(child);
                }
            }
            index++;
        }
        return vertices;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        List<Vertex<T>> vertices = new ArrayList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjacent =
                graph.getAdjList();
        Set<Vertex<T>> vertexSet = new HashSet<>();
        dfsHelper(adjacent, start, vertexSet, vertices);
        return vertices;
    }

    /**
     * the recursive helper method for the dfs helper
     *
     * @param adjacent  the list of adjacent vertices
     * @param start     the starting vertex
     * @param vertexSet the vertex Set that's visited
     * @param vertices  the vertices that are visited
     * @param <T>       The generic type stored
     */
    private static <T> void dfsHelper(Map<Vertex<T>,
            List<VertexDistance<T>>> adjacent, Vertex<T> start,
            Set<Vertex<T>> vertexSet, List<Vertex<T>> vertices) {

        if (!vertexSet.contains(start)) {
            vertexSet.add(start);
            vertices.add(start);

            if (adjacent.get(start) == null) {
                throw new IllegalArgumentException("Start not in dfs");
            }

            List<VertexDistance<T>> vertexDistanceList = adjacent.get(start);
            for (VertexDistance<T> vertexDistance : vertexDistanceList) {
                Vertex<T> child = vertexDistance.getVertex();
                dfsHelper(adjacent, child, vertexSet, vertices);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        boolean exists = graph.getAdjList().containsKey(start);
        if (!exists) {
            throw new IllegalArgumentException(
                    "Vertex does not exist in the graph");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjacentList
                = graph.getAdjList();
        Map<Vertex<T>, Integer> newMap = new HashMap<>();

        for (Vertex<T> vertex : adjacentList.keySet()) {
            newMap.put(vertex, Integer.MAX_VALUE);
        }

        newMap.put(start, 0);

        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();
        queue.add(new VertexDistance<T>(start, 0));

        while (!queue.isEmpty()) {
            VertexDistance<T> current = queue.remove();
            List<VertexDistance<T>> distancePairs
                    = graph.getAdjList().get(current.getVertex());
            for (VertexDistance<T> vertexDistance : distancePairs) {
                int distance = current.getDistance()
                        + vertexDistance.getDistance();
                if (distance < newMap.get(vertexDistance.getVertex())) {
                    newMap.put(vertexDistance.getVertex(), distance);
                    VertexDistance<T> newPair = new VertexDistance<>(
                            vertexDistance.getVertex(), distance);
                    queue.add(newPair);
                }
            }
        }
        return newMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input is null!");
        } else {
            if (graph.getVertices().isEmpty()) {
                return null;
            }
            Set<Edge<T>> mST = new HashSet<Edge<T>>();
            DisjointSet<Vertex<T>> dS =
                    new DisjointSet<Vertex<T>>(graph.getVertices());
            PriorityQueue<Edge<T>> pQ =
                    new PriorityQueue<Edge<T>>();
            for (Edge<T> ed : graph.getEdges()) {
                pQ.add(ed);
            }
            while (!pQ.isEmpty() && mST.size() < graph.getEdges().size() - 1) {
                Edge<T> tmp = pQ.remove();
                if (!dS.find(tmp.getU()).equals(dS.find(tmp.getV()))) {
                    mST.add(tmp);
                    mST.add(new Edge<T>(tmp.getV(), tmp.getU(),
                            tmp.getWeight()));
                    dS.union(tmp.getU(), tmp.getV());
                }
            }
            Vertex<T> ve = graph.getVertices().iterator().next();
            for (Vertex<T> t : graph.getVertices()) {
                if (!dS.find(t).equals(dS.find(ve))) {
                    return null;
                }
            }
            return mST;
        }
    }
}

