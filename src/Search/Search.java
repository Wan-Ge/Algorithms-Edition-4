/****************************************************************************
 *
 *  Search:  "Algorithm Edition 4"  Chapter 3
 *  Dependencies: algs4.jar
 *  Data files: http://algs4.cs.princeton.edu/code/algs4.jar
 *
 *  Basic search algorithm of "Algorithm Edition 4", but there maybe have
 *  some minor changes.
 *
 *  The code contains visualiztion windows of every search algorithm
 *  Just for my own interest, if you like and need, hope you can enjoy it!
 *
 *  These code are very rough, if you have better idea, you can modify it ^_^
 *
 *  Trace sort track use this method  "traceSortTrack(Comparable[] a, int...args)"
 *
 **************************************************************************/

package Search;

import edu.princeton.cs.algs4.*;

/**
 * Created by WanGe on 2017/3/21.
 *
 */


public class Search{
    private Comparable[] keys;     //member of Search class, we can pass a less parameter
    private int N;

    public Search(Comparable[] a, int N) {
        this.keys = a;
        this.N = N;
    }

    public static void  show(Comparable[] a) {
        for (Comparable e : a)
            System.out.println(e);
    }

    /**
     * About binary search, except for the one(two ways to achieve) that we are always use
     * another way called  <em>"interpolation search"</em>, we just need to modify
     * "mid = low + (low+high)/2 to
     * mid = low + (high-low)*(key.compareTo(keys[low]))/ (keys[high].compareTo(keys[low]));
     *
     * For some large list, and the distribution of keywords is more uniform. The average
     * performance of the interpolation search algorithm is much better than the binary search
     *
     * Recursive binary search
     *
     * @return the index of key
     */
    public int BinarySearchRe(Comparable key, int low, int high) {
        if (high < low) return low;
        int mid = low + (high - low)/2;
        int cmp = key.compareTo(keys[mid]);
        if (cmp < 0)
            return BinarySearchRe(key, low, mid - 1);
        else if (cmp > 0)
            return BinarySearchRe(key, mid + 1, high);
        else
            return mid;
    }

    /**
     * Iterative binary search
     */
    public int BinarySearchIt(Comparable key) {
        int low = 0, high = N-1;
        while (low <= high) {
            int mid = low + (high - low)/2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0)    high = mid - 1;
            else if (cmp > 0)   low = mid + 1;
            else    return mid;
        }

        return low;
    }
}
