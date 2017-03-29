package Graphs;

/**
 * Contains several small algorithm of DFS, it can avoid writing too many
 * classes in a package. but the code may looks like a little messy.
 *
 * - 1. Judge if G is acyclic.(assumes no self-loops or parallel edges)
 * - 2. Judge if G is bipartite.(two-colorable)
 *
 * Tips: Two-colorability.
 *      Can the vertices of a given graph be assigned one of two colors in
 *      such a way that no edge connects vertices of the same color? which
 *      is equivalent to this question: Is the graph bipartite?
 *
 * Created by WanGe on 2017/3/29.
 */
public class DepathFirstSearch {
    private boolean[] marked;
    private boolean[] cycle_marked;   //for hasCycle()
    private boolean hasCycle;
    private boolean[] color_marked;   //for isBipartite()
    private boolean[] color;
    private boolean isTwoColorable = true;
    private Graph G;
    private int count;

    /**
     * @param G graph for search
     * @param s source
     */
    public DepathFirstSearch(Graph G, int s) {
        this.G = G;
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean marked(int w) {return marked[w];}

    public int count() {return count;}

    /**
     * Judge if G is acyclic.(assumes no self-loops or parallel edges)
     * @return true if it has cycle.
     */
    public boolean hasCycle() {
        cycle(G);
        return hasCycle;
    }

    private void cycle(Graph G) {
        cycle_marked = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!cycle_marked[s])
                dfsCycle(G, s, s);
    }

    private void dfsCycle(Graph G, int v, int u) {
        cycle_marked[v] = true;
        for (int w : G.adj(v)) {
            if (!cycle_marked[w])   dfsCycle(G, w, v);
            else if (w != u)    hasCycle = true;
        }
    }

    /**
     * Judge if G is bipartite.(two-colorable)
     *
     * @return true if G is bipartite
     */
    public boolean isBipartite() {
        twoColor(G);
        return isTwoColorable;
    }

    private void twoColor(Graph G) {
        color_marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!color_marked[s])
                dfsColor(G, s);
    }

    private void dfsColor(Graph G, int v) {
        color_marked[v] = true;
        for (int w : G.adj(v)) {
            if (!color_marked[w]) {
                color[w] = !color[v];
                dfsColor(G, w);
            }
            else if (color[w] == color[v])  isTwoColorable = false;
        }
    }
}
