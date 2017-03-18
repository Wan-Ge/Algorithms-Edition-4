/****************************************************************************
 *
 *  Sort:  "Algorithm Edition 4"  Chapter 2
 *  Dependencies: algs4.jar
 *  Data files: http://algs4.cs.princeton.edu/code/algs4.jar
 *
 *  Basic sorting algorithm of "Algorithm Edition 4"
 *
 *  The code contains visualiztion windows of every sort algorithm
 *  Just for my own interest, if you like and need, hope you can enjoy it!
 *
 *  These code are very rough, if you have better idea, you can modify it ^_^
 *
 *  Trace sort track use this method  "traceSortTrack(Comparable[] a, int...args)"
 *
 **************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

/**
 * Created by WanGe on 2017/3/13.
 */

public class Sort extends DrawTrack {
    static Sort t = new Sort();
    private static Comparable[] aux;   //the auxiliary arrays that merge sort used

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }

    public static boolean isSorted(Comparable[] a) {
        //测试数组中所有的元素都是有序的
        //Test whether the array elements are ordered
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))   return false;
        return true;
    }

    //下面所有的排序算法都将数组按 升序排序
    //All the sorting algorithms below are arrange the array ins ascending order
    public static void selectSort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            //交换从 a[i] 到 a[i+1..N] 中最小的数字，和 a[i] 进行交换
            //Exchange the smallest element between a[i] and a[i+1..N]
            int min = i;
            for (int j = i+1; j < N; j++)
                if (less(a[j], a[min])) min = j;
            exch(a, i, min);
            t.traceSortTrack(a, i, min);
        }
    }

    public static void insertSort(Comparable[] a) {
        int N = a.length;
        int j_tmp = 0;
        for (int i = 1; i < N; i++) {
            //insert a[i] into a[i-1], a[i-2]....
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--){
                exch(a, j, j - 1);
                j_tmp = j;
            }
            t.traceSortTrack(a, j_tmp, j_tmp - 1);
        }
    }

    public static void shellSort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N/3)
            h = 3*h + 1;    //1, 4, 13, 40, 121, 364, 1093, ...  'h = N/3 + 1' is also feasible
        while (h >= 1) {
            //将数组变为 h 有序
            //Change the array yo h orderly
            for (int i = h; i < N; i++) {
                //将 a[i] 插入到 a[i-h], a[i-2*h], a[i-3*h]...之中
                //Insert a[i] into a[i-h], a[i-2*h], a[i-3*h]...
                for (int j = i; j >= h && less(a[j], a[j-h]); j-=h) {
                    exch(a, j, j-h);
                    //t.traceSortTrack(a, j, j-h);
                }
            }
            h = h / 3;
        }
    }

    //Auxiliary code of merge sort (Merge the two sub array)
    private static void merge_inSuit(Comparable[] a, int low, int mid, int high) {
        //将 a[low..mind] 和 a[mid..high] 归并
        //Merge the a[low..mind] and a[mid..high]
        int i = low, j = mid + 1;

        //These code just for create the arguments for the traceSortTrack function
        //but it has some effects on the performance of sort
        int low_tmp = low, mid_tmp = j;
        int[] draw = new int[j - i];
        for (int k = 0; k < draw.length && low_tmp < mid_tmp; k++, low_tmp++)
            draw[k] = low_tmp;

        //manual copy a[low..high] to aux[low..high] or use the standard library
        /*for (int k = low; k <= high; k++)
            aux[k] = a[k];*/
        aux = Arrays.copyOf(a, a.length);

        for (int k = low; k <= high; k++) {
            if      (i > mid)               a[k] = aux[j++];
            else if (j > high)              a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
            //t.traceSortTrack(a, draw);   //if you write this line code outside for loop, it will draw more quickly.
        }
    }

    public static void mergeSort(Comparable[] a, int low, int high) {
        //Sort the a[low..high]
        if (high <= low)    return;
        int mid = low + (high - low)/2;
        mergeSort(a, low, mid);
        mergeSort(a, mid+1, high);
        merge_inSuit(a, low, mid, high);
    }

    //Main entrance of merge sort (Top-down merge sort)
    public static void mergeSort(Comparable[] a) {
        aux = new Comparable[a.length];
        mergeSort(a, 0, a.length - 1);
    }

    //Another method of merge sort (Bottom-Up merge sort)
    public static void mergeSortBU(Comparable[] a) {
        //Do logN times merge
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz += sz) {      //size of "sz" sub arrays
            for (int low = 0; low < N-sz; low += sz+sz)          //low:sub array index
                merge_inSuit(a, low, low+sz-1, Math.min(low+sz+sz-1, N-1));
        }
    }

    //Auxiliary code of quick sort
    public static int partition(Comparable[] a, int low, int high) {
        //divide the array into a[low..i-1],  a[i],  a[i+1..high]
        int i = low, j = high + 1;           //  left/right scan-pointer
        Comparable v = a[low];                //split elements
        while (true) {
            // Scan left and right, check whether the scan is over and swap the elements
            while (less(a[++i], v))     if (i == high)  break;
            while (less(v, a[--j]))     if (j == low)   break;
            if (i >= j)     break;
            exch(a, i, j);
            //t.traceSortTrack(a, i, j);
        }
        exch(a, low, j);       //put v = a[j] into a correct position
        //t.traceSortTrack(a, low, j);
        return j;              // a[low..j-1] <= a[j] <= a[j+1..high] reached
    }

    //Main entrance of quick sort
    public static void quickSort(Comparable[] a) {
        //Eliminate dependency on input (Rearranges the elements of the specified array in uniformly random order.)
        //we all know that when a array is already ordered
        //actually quick sort is slower than other method
        StdRandom.shuffle(a);
        quickSort(a, 0, a.length - 1);
    }

    public static void quickSort(Comparable[] a, int low, int high) {
        if (high <= low)    return;
        int j = partition(a, low, high);
        quickSort(a, low, j-1);       //sort the left part a[low..j-1]
        quickSort(a, j+1, high);      //sort the right part a[j+1..high]
    }
}

