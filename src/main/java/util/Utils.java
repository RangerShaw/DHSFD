package util;

import java.util.*;

public class Utils {

    /**
     * Compare two BitSets from the lowest bit
     */
    public static Comparator<BitSet> BitsetComparator() {
        return (a, b) -> {
            if (a.equals(b)) return 0;

            BitSet xor = (BitSet) a.clone();
            xor.xor(b);
            int lowestDiff = xor.nextSetBit(0);

            if (lowestDiff == -1) return 0;
            return b.get(lowestDiff) ? 1 : -1;
        };
    }

    public static Comparator<Map.Entry<BitSet, Long>> BitsetMapComparator() {
        return (a, b) -> {
            if (a.equals(b)) return 0;

            BitSet xor = (BitSet) a.getKey().clone();
            xor.xor(b.getKey());
            int lowestDiff = xor.nextSetBit(0);

            if (lowestDiff == -1) return 0;
            return b.getKey().get(lowestDiff) ? 1 : -1;
        };
    }

    public static BitSet longToBitSet(int nAttributes, long n) {
        BitSet bs = new BitSet(nAttributes);
        for (int i = 0; i < nAttributes; i++)
            if ((n & (1L << i)) != 0) bs.set(i);
        return bs;
    }

    public static long bitsetToLong(int nAttributes, BitSet bs) {
        long x = 0;
        for (int i = 0; i < nAttributes; i++)
            if (bs.get(i)) x |= (1L << i);
        return x;
    }

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

    public static boolean removeEmptyLongSetSorted(List<Long> sets) {
        // sets should be sorted already
        if (sets.isEmpty() || sets.get(0) != 0) return false;
        sets.remove(0);
        return true;
    }

    public static List<Long> findMinLongSets(List<Long> sets) {
        /* sets should be already sorted */
        List<Long> minSets = new ArrayList<>();

        boolean[] notMin = new boolean[sets.size()];
        int[] cardinalities = sets.stream().mapToInt(Long::bitCount).toArray();

        for (int i = 0, size = sets.size(); i < size; i++) {
            if (!notMin[i]) {
                long setI = sets.get(i);
                minSets.add(setI);
                for (int j = sets.size() - 1; j > i && cardinalities[j] > cardinalities[i]; j--) {
                    if (!notMin[j] && Utils.isSubset(setI, sets.get(j)))
                        notMin[j] = true;
                }
            }
        }

        return minSets;
    }

    public static List<Long> genEdgeRhs(int e, List<Long> edges) {
        List<Long> edgeRhs = new ArrayList<>();
        long mask = 1L << e;
        for (long edge : edges)
            if ((edge & mask) != 0) edgeRhs.add(edge & ~mask);
        return edgeRhs;
    }

}
