package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

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
 * Created by WanGe on 2017/4/1.
 */
public class EdgeWeightedDigraph {
    private final int V;                        // number of vertices
    private int E;                              // number of edges
    private ArrayList<DirectedEdge>[] adj;       // adjacency lists

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (ArrayList<DirectedEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<>();
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
        adj[e.from()].add(e);
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
}
