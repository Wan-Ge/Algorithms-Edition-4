package Graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.UF;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The {@code LazyPrimMST} class represents a data type for computing a minimum spanning
 * in an edge-weighted graph.
 * The edge weights can be positive, zero, or negative and need not be distinct.
 *
 * This implementation of Prim's algorithm uses a priority queue. to hold crossing edges,
 * a vertex-in-dexed arrays to mark tree vertices, and queue to hold MST edges. This
 * implementation is a lazy approach where we leave ineligible edges in the priority queue.
 *
 * Data files: tinyEWG.txt
 *             mediumEWG.txt
 *
 * % java LazyPrimMST tinyEWG.txt
 *  0-7 0.16000
 *  1-7 0.19000
 *  0-2 0.26000
 *  2-3 0.17000
 *  5-7 0.28000
 *  4-5 0.35000
 *  6-2 0.40000
 *  1.81000
 *
 *  % java LazyPrimMST mediumEWG.txt
 *  0-225   0.02383
 *  49-225  0.03314
 *  44-49   0.02107
 *  44-204  0.01774
 *  49-97   0.03121
 *  202-204 0.04207
 *  176-202 0.04299
 *  176-191 0.02089
 *  68-176  0.04396
 *  58-68   0.04795
 *  10.46351
 *
 * Created by WanGe on 2017/3/31.
 */
public class LazyPrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;                // total weight of MST
    private boolean[] marked;             // MST vertices
    private ArrayList<Edge> mst;          // MST edges
    private PriorityQueue<Edge> pq;       // crossing (and ineligible) edges

    public LazyPrimMST(EdgeWeightedGraph G) {
        mst = new ArrayList<>();
        pq = new PriorityQueue<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)      // run Prim from all vertices to
            if (!marked[v]) prim(G, v);      // get a minimum spanning forest

        // check optimality conditions
        assert check(G);
    }

    // run Prim's algorithm
    private void prim(EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {                           // better to stop when mst has V-1 edges
            Edge e = pq.remove();                         // smallest edge on pq
            int v = e.either(), w = e.other(v);           // two endpoint
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) continue;         // lazy, both v and w already scanned
            mst.add(e);                                   // add e to MST
            weight += e.getWeight();
            if (!marked[v]) scan(G, v);                   // v becomes part of tree
            if (!marked[w]) scan(G, w);                   // w becomes part of tree
        }
    }

    // add all edges e incident to v onto pq if the other endpoint has not yet been scanned
    private void scan(EdgeWeightedGraph G, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)])    pq.add(e);
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest)
     * @return as iterable of edges
     */
    public Iterable<Edge> edges() {return mst;}

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest)
     * @return the sum
     */
    public double weight() {return weight;}

    private boolean check(EdgeWeightedGraph G) {
        //check weight
        double totalWeight = 0.0;
        for (Edge e : edges())
            totalWeight += e.getWeight();
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        // TODO: modify the UF to own class
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {
            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            //check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.getWeight() < e.getWeight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Unit tests the {@code LazyPrimMST} data type
     *
     * @param the command-line arguments (tinyEWG.txt / mediumEWG.txt)
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.edges())
            System.out.println(e);
        System.out.printf("%.5f\n", mst.weight());
    }
}
