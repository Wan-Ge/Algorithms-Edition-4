package Graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by WanGe on 2017/3/29.
 *
 */
public class SymbolGraph {
    private HashMap<String, Integer> map;         // String -> index
    private String[] keys;                        // index -> String
    private Graph G;                              // the graph

    public SymbolGraph(String stream, String sp) {
        map = new HashMap<>();
        In in = new In(stream);         // First pass
        while (in.hasNextLine()) {      // builds the index
            // by reading strings to associate each distinct string with an index
            String[] a = in.readLine().split(sp);
            for (int i = 0; i < a.length; i++)
                if (!map.containsKey(a[i]))
                    map.put(a[i], map.size());
        }

        keys = new String[map.size()];     //Inverted index to get string keys is an array
        for (String name : map.keySet())
            keys[map.get(name)] = name;

        G = new Graph(map.size());
        in = new In(stream);        // Second pass
        while (in.hasNextLine()) {         // build the graph
            //by connecting the first vertex on each line to all the others.
            String[] a = in.readLine().split(sp);
            int v = map.get(a[0]);
            for (int i = 1; i < a.length; i++)
                G.addEdge(v, map.get(a[i]));
        }
    }

    public boolean contains(String s) {return map.containsKey(s);}
    public int index(String s) {return map.get(s);}
    public String name(int v) {return keys[v];}
    public Graph G() {return G;}

    /**
     *  Test
     * @param args routes.txt " "
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String filename = args[0];
        String delim = args[1];
        SymbolGraph sg = new SymbolGraph(filename, delim);
        Graph G = sg.G();

        // Here input like this
        // input:  JFK
        // output:   MCO ATL ORD
        while (in.hasNextLine()) {
            String source = in.nextLine();
            for (int w : G.adj(sg.index(source)))
                System.out.println("   " + sg.name(w));
        }
    }
}
