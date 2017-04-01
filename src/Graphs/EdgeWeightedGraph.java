package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

/**
 * This implementation maintains a vertex-indexed array of lists of edges.
 * As with Graph, every edge appears twice: if an edge connects v and w, it
 * appears both in v's list and in w's list.
 *
 * Created by WanGe on 2017/3/31.
 */
public class EdgeWeightedGraph {
    private final int V;             // number of vertices
    private int E;                   // number of edges
    private ArrayList<Edge>[] adj;      // adjacency lists

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Edge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<Edge>();
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public int V() {return V;}
    public int E() {return E;}

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> edges() {
        ArrayList<Edge> b = new ArrayList<>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj[v])
                if (e.other(v) > v) b.add(e);
        return b;
    }
}
