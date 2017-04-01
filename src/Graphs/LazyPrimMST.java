package Graphs;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * This implementation of Prim's algorithm uses a priority queue. to hold crossing edges,
 * a vertex-in-dexed arrays to mark tree vertices, and queue to hold MST edges. This
 * implementation is a lazy approach where we leave ineligible edges in the priority queue.
 *
 * Created by WanGe on 2017/3/31.
 */
public class LazyPrimMST {
    private boolean[] marked;             // MST vertices
    private ArrayList<Edge> mst;          // MST edges
    private PriorityQueue<Edge> pq;       // crossing (and ineligible) edges

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new PriorityQueue<>();
        marked = new boolean[G.V()];
        mst = new ArrayList<>();

        visit(G, 0);      // assumes G is connected
        while (!pq.isEmpty()) {
            Edge e =
        }
    }

}
