import edu.princeton.cs.algs4.*;

/**
 * Created by WanGe on 2017/3/13.
 */
public class Sort extends DrawTrack {
    static Sort t = new Sort();
    private static Comparable[] aux;   //the auxiliary arrays that merge sort used

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            System.out.printf(a[i] + " ");
        System.out.println()
    }

    private static boolean isSorted(Comparable[] a) {
        //测试数组中所有的元素都是有序的
        //Test whether the array elements are ordered
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))   return false;
        return true;
    }

    //下面所有的排序算法都将数组按 升序排序
    //All the sorting algorithms below are arrange the array ins ascending order
    public static void SelectSort(Comparable[] a) {
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

    public static void InsertSort(Comparable[] a) {
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

    public static void ShellSort(Comparable[] a) {
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
                    t.traceSortTrack(a, j, j-h);
                }
            }
            h = h / 3;
        }
    }

    //Auxiliary code of merge sort
    private static void merge_inSuit(Comparable[] a, int low, int mid, int high) {
        //将 a[low..mind] 和 a[mid..high] 归并
        //Merge the a[low..mind] and a[mid..high]
        int i = low, j = mid + 1;

        //These code just for create the arguments for the traceSortTrack function
        int low_tmp = low, mid_tmp = mid + 1;
        int[] draw = new int[j - i];
        for (int k = 0; k < draw.length && low_tmp < mid_tmp; k++, low_tmp++)
            draw[k] = low_tmp;

        for (int k = low; k <= high; k++)
            aux[k] = a[k];    //copy a[low..high] to aux[low..high]

        for (int k = low; k <= high; k++) {
            if      (i > mid)               a[k] = aux[j++];
            else if (j > high)              a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }
        t.traceSortTrack(a, draw);
    }

    public static void MergeSort(Comparable[] a, int low, int high) {
        //Sort the a[low..high]
        if (high <= low)    return;
        int mid = low + (high - low)/2;
        MergeSort(a, low, mid);
        MergeSort(a, mid+1, high);
        merge_inSuit(a, low, mid, high);
    }

    //Main entrance of merge sort
    private static void MergeSort(Comparable[] a) {
        aux = new Comparable[a.length];
        MergeSort(a, 0, a.length - 1);
    }

    public static void unitTest() {
        //Just for test and draw one array
        DrawTrack t = new DrawTrack(){};
        int N = 30;
        Double[] a = new Double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(2.0, 50.0);
        MergeSort(a);                 //Modify the sort method that you want to see
        assert isSorted(a);
        show(a);
    }

    public static void comparisonTest() {
        SortCompare t = new SortCompare();
        /*double t1 = t.timeRandomInput("SelectSort", 100, 1000);
        System.out.println("SelectSort: " + t1);*/

        double t2 = t.timeRandomInput("ShellSort_2", 100, 100000);
        System.out.println("ShellSort_2: " + t2);

        double t3 = t.timeRandomInput("ShellSort", 100, 100000);
        System.out.println("ShellSort: " + t3);

        System.out.printf("%.1f times faster than InsertSort\n", t3/t2);
    }

    public static void main(String[] args) {
        unitTest();
        //comparisonTest();
    }
}

