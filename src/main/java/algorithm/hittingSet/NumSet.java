package algorithm.hittingSet;

import java.util.ArrayList;
import java.util.List;

public class NumSet {

    public static List<Integer> indicesOfOnes(long n) {
        List<Integer> res = new ArrayList<>();
        int pos = 0;
        while (n > 0) {
            if ((n & 1) != 0L) res.add(pos);
            pos++;
            n >>>= 1;
        }
        return res;
    }

    public static boolean isSubset(long a, long b) {
        return a == (a & b);
    }

    public static void sortLongSets(int nEles, List<Long> sets) {
        List<List<Long>> buckets = new ArrayList<>(nEles + 1);
        for (int i = 0; i <= nEles; i++)
            buckets.add(new ArrayList<>());

        for (long set : sets)
            buckets.get(Long.bitCount(set)).add(set);

        sets.clear();
        for (List<Long> bucket : buckets)
            sets.addAll(bucket);
    }

    public static boolean removeEmptyLongSetUnsorted(List<Long> sets) {
        boolean hasEmptySubset = false;
        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i) == 0) {
                hasEmptySubset = true;
                sets.remove(i);
                break;
            }
        }
        return hasEmptySubset;
    }


}
