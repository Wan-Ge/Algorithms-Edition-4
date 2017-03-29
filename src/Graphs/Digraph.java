package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

/**
 * Directed graph(Digraph) data type
 *
 * Reversing a digraph: Digraph also adds to the API a method reverse() which retruns
 *                      a copy of the digraph, with all edges reversed.
 *
 * Created by WanGe on 2017/3/29.
 */
public class Digraph {
    private final int V;
    private int E;
    private ArrayList<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<Integer>();
    }

    public Digraph(In in) {
        this(in.readInt());      // Read V and construct this graph
        int E = in.readInt();    // Read E
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() {return V;}
    public int E() {return E;}
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {return adj[v];}

    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                R.addEdge(w, v);
        return R;
    }
}
