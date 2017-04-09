package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;

/**
 * Computes longest paths in an edge-weighted acyclic digraph.
 *
 * Remark: should probably check that graph is a DAG before running.
 *
 *  % java AcyclicLP tinyEWDAG.txt 5
 *  5 to 0 (2.44)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93   4->0  0.38
 *  5 to 1 (0.32)  5->1  0.32
 *  5 to 2 (2.77)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93   4->7  0.37   7->2  0.34
 *  5 to 3 (0.61)  5->1  0.32   1->3  0.29
 *  5 to 4 (2.06)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93
 *  5 to 5 (0.00)
 *  5 to 6 (1.13)  5->1  0.32   1->3  0.29   3->6  0.52
 *  5 to 7 (2.43)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93   4->7  0.37
 *
 * Created by WanGe on 2017/4/9.
 */
public class AcyclicLP {
    private double[] distTo;              // distTo[v] = distance of longest s->v path
    private DirectedEdge[] edgeTo;        // edgeTo[v] = last edge on longest s-> path

    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        for (int v  =0; v < G.V(); v++)
            distTo[v] = Double.NEGATIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in topological order
        Topological topo = new Topological(G);
        if (!topo.hasOrder())
            throw new IllegalArgumentException("Digraph is not acyclic.");
        for (int v : topo.order()) {
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }
    }

    // relax edge e, but update if you find a longer path
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] < distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
        }
    }

    /**
     * Returns the length of a longest path from the source vertex {@code s}
     * to vertex {@code v}.
     * @param v the destination vertex
     * @return the length
     */
    public double distTo(int v) {return distTo[v];}

    public boolean hasPathTo(int v) {return distTo[v] > Double.NEGATIVE_INFINITY;}

    /**
     * Returns the longest path from the source vertex {@code s} to vertex {@code v}.
     * @param v the destination vertex
     * @return the whole path
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))  return null;
        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    /**
     * Unit tests the {@code AcyclicLP} data type.
     * @param args the command-line
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        AcyclicLP lp = new AcyclicLP(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (lp.hasPathTo(v)) {
                System.out.printf("%d to %d (%.2f)  ", s, v, lp.distTo(v));
                for (DirectedEdge e : lp.pathTo(v)) {
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
