package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by WanGe on 2017/3/31.
 */
public class SymbolDigraph {
    private HashMap<String, Integer> map;  // string -> index
    private String[] keys;                // index -> string
    private Digraph digraph;               // the underlying digraph

    public SymbolDigraph(String filename, String delimiter) {
        map = new HashMap<>();

        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                if (!map.containsKey(a[i]))
                    map.put(a[i], map.size());
            }
        }

        // inverted index to get string keys in an array
        keys = new String[map.size()];
        for (String name : map.keySet())
            keys[map.get(name)] = name;

        // second pass builds the digraph by connecting first vertex on each
        // line to all others
        digraph = new Digraph(map.size());
        in  = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = map.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = map.get(a[i]);
                digraph.addEdge(v, w);
            }
        }
    }

    public Digraph digraph() {return digraph;}

    /**
     * Does the digraph contain the vertex named {@code s}?
     * @param s the name of vertex
     */
    public boolean contains(String s) {return map.containsKey(s);}

    /**
     * Returns the integer associated with the vertex name {@code s}.
     */
    public int indexOf(String s) {return map.get(s);}

    /**
     * Returns the name of the vertex associated with the integer {@code v}.
     * @param  v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
     * @return the name of the vertex associated with the integer {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = digraph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }


    /**
     *  Test
     * @param args routes.txt " "
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String filename = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Digraph graph = sg.digraph();

        // Here input like this
        // input:  JFK
        // output:   MCO ATL ORD
        while (in.hasNextLine()) {
            String source = in.nextLine();
            for (int w : graph.adj(sg.indexOf(source)))
                System.out.println("   " + sg.nameOf(w));
        }
    }
}
