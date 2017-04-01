package Graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.UF;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The {@code KruskalMST} class represents a data type for computing a MST in an edge-weighted graph.
 *
 * This implementation uses <em>Kruskal's algorithm</em> and the union-find data type.
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
 * Created by WanGe on 2017/4/1.
 */
public class KruskalMST {
    private double weight;
    private ArrayList<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (Edge e : G.edges())    pq.add(e);   // create a completely priority queue
        UF uf = new UF(G.V());

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.remove();             // Get min weight edge on pq and its vertices
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) continue;     // Ignore ineligible edges
            uf.union(v, w);                       // Merge components
            mst.add(e);                           // Add edge to mst
            weight += e.getWeight();
        }

        // also can do "assert check(G)" like before
    }

    public Iterable<Edge> edges() {return mst;}

    public double weight() {return weight;}

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.edges())
            System.out.println(e);
        System.out.printf("%.5f\n", mst.weight());
    }
}
