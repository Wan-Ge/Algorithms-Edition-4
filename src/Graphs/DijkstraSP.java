package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;

/**
 * The {@code DijkstraSP} class represent a data type for solving the single-source
 * shortest paths problem in edge-weighted digraphs where the edge weights are nonnegative
 *
 * Online Data files:   http://algs4.cs.princeton.edu/44sp/tinyEWD.txt
 *                      http://algs4.cs.princeton.edu/44sp/mediumEWD.txt
 *                      http://algs4.cs.princeton.edu/44sp/largeEWD.txt
 *
 * Data files: tinyEWG.tx
 *             tinyEWG_2.tx
 *             mediumEWG.txt
 *
 * Created by WanGe on 2017/4/1.
 */
public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq.insert(s, 0.0);
        while (!pq.isEmpty())
            relax(G, pq.delMin());
    }

    // Edge relaxation
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
        }
    }

    // Vertex relaxation
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.getWeight()) {
                distTo[w] = distTo[v] + e.getWeight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the length of shortest path from the source vertex {@code s} to vertex {@code v}.
     * @param v the destination vertex
     * @return the length
     */
    public double distTo(int v) {return distTo[v];}

    /**
     * Returns true if there is a path form the source vertex {@code s} to vertex {@code v}.
     * @param v the destination vertex
     * @return true or false
     */
    public boolean hasPathTo(int v) {return distTo[v] < Double.POSITIVE_INFINITY;}

    /**
     * Returns a shortest path from the source vertex {@code s} to {@code v}.
     * @param v the destination vertex
     * @return a shortest path
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))  return null;
        // There's a bug in JDK's Stack class, so we use LinkedList
        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    /**
     * Unit tests the {@code DijkstraSP} data type
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);

        // compute shortest path
        DijkstraSP sp = new DijkstraSP(G, s);

        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                System.out.printf("%d->%d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t))
                    System.out.print(e + "   ");
                System.out.println();
            }
            else {
                System.out.printf("%d->%d              no path\n", s, t);
            }
        }
    }
}
