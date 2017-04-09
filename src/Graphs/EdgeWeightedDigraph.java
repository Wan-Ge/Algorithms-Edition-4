package Graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The {@code EdgeWeightedDigraph} class represents a edge-weighted digraph
 * of vertices named 0 through V-1, where each directed edge is of type
 * {@link DirectedEdge} and has a real-valued weight.
 * Parallel edges and self-loops are permitted.
 *
 * This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of {@link java.util.ArrayList} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident from a given vertex, which takes
 *  time proportional to the number of such edges.
 *
 *  Data File: tinyEWDAG.txt
 *
 * Created by WanGe on 2017/4/1.
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;                        // number of vertices
    private int E;                              // number of edges
    private LinkedList<DirectedEdge>[] adj;       // adjacency lists

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (LinkedList<DirectedEdge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<>();
    }

    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0)  throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i  =0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    public int V() {return V;}
    public int E() {return E;}

    public void addEdge(DirectedEdge e) {
        adj[e.from()].push(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {return adj[v];}

    public Iterable<DirectedEdge> edges() {
        ArrayList<DirectedEdge> arrayList = new ArrayList<>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge e : adj[v])
                arrayList.add(e);
        return arrayList;
    }

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Unit tests the {@code EdgeWeightedDigraph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);
    }
}
