import edu.princeton.cs.algs4.*;

/**
 * Created by WanGe on 2017/3/16.
 */
public class SortCompare {
    public static double time(String alg, Double[] a) {
        Sort t= new Sort();
        Stopwatch timer = new Stopwatch();
        if (alg.equals("InsertSort"))   t.InsertSort(a);
        if (alg.equals("SelectSort"))   t.SelectSort(a);
        if (alg.equals("ShellSort"))   t.ShellSort(a);
        return timer.elapsedTime();
    }

    public static double timeRandomInput(String alg, int N, int T) {
        //使用 "alg" 算法对 T 个长度为 N 的数组排序
        //Use algorithm "alg" to sort T arrays of length N
        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) {
            //Run a test (generate an array and sort it)
            for (int i = 0; i < N; i++)
                a[i] = StdRandom.uniform();
            if (t % 100 == 0)
            total += time(alg, a);
        }
        return total;
    }
}
