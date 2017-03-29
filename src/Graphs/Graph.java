/****************************************************************************
 *
 *  Graphs:  "Algorithm Edition 4"  Chapter 4
 *  Dependencies: algs4.jar
 *  Data files: http://algs4.cs.princeton.edu/code/algs4.jar
 *
 *  Basic search algorithm of "Algorithm Edition 4", but there maybe have
 *  some minor changes.
 *
 *  These code are very rough, if you have better idea, you can modify it ^_^
 *
 **************************************************************************/

package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

/**
 * This class has been implemented in the original "algs4.jar" file
 * but for the future to look convenient, i decided to rewrite it.
 */
public class Graph {
    private final int V;                   // number of vertices
    private int E;                         // number of edges
    private ArrayList<Integer>[] adj;      // adjacency lists

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Integer>[]) new ArrayList[V];    // Create array of lists
        for (int v = 0; v < V; v++)                      // Initialize all lists to empty
            adj[v] = new ArrayList<Integer>();
    }

    public Graph(In in) {
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
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {return adj[v];}
}
