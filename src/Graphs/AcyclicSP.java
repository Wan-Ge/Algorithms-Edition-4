package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.Stack;

/**
 * Acyclic edge-weighted digraphs
 *
 * 1. Solves the single-source problem in linear time
 * 2. Handles negative edge weights
 * 3. Solve related problems, such as finding longest paths
 *
 * Specifically, vertex relaxation, in combination with topological sorting,
 * immediately presents a solution to the single-source shortest-paths problem
 * for edge-weighted DAGs.
 *
 * Created by WanGe on 2017/4/5.
 */
public class AcyclicSP {
    private double[] distTo;        // distTo[v] = distance of shortest s->v path
    private DirectedEdge[] edgeTo;  // edgeTo[v] = last edge on shortest s->v path

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        Topological top = new Topological(G);
        if (!top.hasOrder())
            throw  new IllegalArgumentException("Digraph is not acyclic");
        for (int v : top.order())
            for (DirectedEdge e : G.adj(v))
                relax(e);
    }

    // relax edge e
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v) {return distTo[v];}

    public boolean hasPathTo(int v) {return distTo[v] < Double.POSITIVE_INFINITY;}

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))  return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);

        return path;
    }

    /**
     * Unit tests the {@code AcyclicSP} data type
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // find shortest path from s to each other vertex in DAG
        AcyclicSP sp = new AcyclicSP(G, s);
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
