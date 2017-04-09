package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *  Bellman-Ford shortest path algorithm. Computes the shortest path tree in
 *  edge-weighted digraph G from vertex s, or finds a negative cost cycle
 *  reachable from s.
 *
 *  The efficiency is very low, but the code is easy to write.
 *
 *  This implementation of the Bellman-Ford algorithm uses a version of realx()
 *  that puts vertices pointed to by edges that successfully relax on a FIFO
 *  queue(avoding duplicates) and periodically checks for a negative cycle in
 *  edgeTo[].
 *
 *  % java BellmanFordSP tinyEWDn.txt 0
 *  0 to 0 ( 0.00)
 *  0 to 1 ( 0.93)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   4->5  0.35   5->1  0.32
 *  0 to 2 ( 0.26)  0->2  0.26
 *  0 to 3 ( 0.99)  0->2  0.26   2->7  0.34   7->3  0.39
 *  0 to 4 ( 0.26)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25
 *  0 to 5 ( 0.61)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   4->5  0.35
 *
 *  % java BellmanFordSP tinyEWDnc.txt 0
 *  4->5  0.35
 *  5->4 -0.66
 *
 * Created by WanGe on 2017/4/9.
 */

public class BellmanFordSP {
    private double[] distTo;                  // distTo[v] = distance of shortest s->v path
    private DirectedEdge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private boolean[] onQueue;                // onQueue[v] = is v currently on the queue?
    private ArrayList<Integer> queue;         // queue of vertices to relax
    private int cost;                         // number of calls to relax()
    private Iterable<DirectedEdge> cycle;     // negative cycle (or null if no such cycle)

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo  = new double[G.V()];
        edgeTo  = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // Bellman-Ford algorithm
        queue = new ArrayList<>();
        queue.add(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.remove(0);
            onQueue[v] = false;
            relax(G, v);
        }
    }

    // relax vertex v and put other endpoints on queue if changed
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.getWeight()) {
                distTo[w] = distTo[v] + e.getWeight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.add(w);
                    onQueue[w] = true;
                }
            }
            if (cost++ % G.V() == 0){
                findNegativeCycle();
                if (hasNegativeCycle())    return;      // found a negative cycle
            }
        }
    }

    /**
     * Is there a negative cycle reachable from the source vertex {@code s}.
     * @return true or false
     */
    public boolean hasNegativeCycle() {return cycle != null;}

    public Iterable<DirectedEdge> negativeCycle() {return cycle;}

    // by finding a cycle in predecessor gtaph
    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);

        EdgeWeightedDirectedCycle  finder = new EdgeWeightedDirectedCycle (spt);
        cycle = finder.cycle();
    }

    public double distTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists.");
        return distTo[v];
    }

    public boolean hasPathTo(int v) {return distTo[v] < Double.POSITIVE_INFINITY;}

    public Iterable<DirectedEdge> pathTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists.");
        if (!hasPathTo(v))  return null;
        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);

        return path;
    }

    /**
     * Unit tests the {@code BellemanFordSP} data type
     * @param args the command-line
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        BellmanFordSP sp = new BellmanFordSP(G, s);

        // print negative cycle
        if (sp.hasNegativeCycle()) {
            for (DirectedEdge e : sp.negativeCycle())
                System.out.println(e);
        }

        // print shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    System.out.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        System.out.print(e + "   ");
                    }
                    System.out.println();
                }
                else {
                    System.out.printf("%d to %d         no path\n", s, v);
                }
            }
        }
    }
}
