package Graphs;

/**
 * All-pairs shortest paths
 *
 * Given an edge-weighted digraph, support queries of the form "Given a source vertex s
 * and a target vertex t, is there a path from s to t?"  If so, find a shortest such path
 * (one whose total weight is minimal)
 *
 * The code below solves the all-pairs shortest paths shortest paths problem, using time
 * and space proportional to EVlogV. It builds an array of {@link DijkstraSP} objects, one
 * for each vertex as the source. To answer a client query, it uses the source to access the
 * corresponding single-source shortest-paths object and then passes the target as argument
 * to the query.
 *
 * Created by WanGe on 2017/4/5.
 */
public class DijkstraAllPairsSP {
    private DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new DijkstraSP(G, v);
    }

    public Iterable<DirectedEdge> path(int s, int t) {return all[s].pathTo(t);}

    public double dist(int s, int t) {return all[s].distTo(t);}
}
