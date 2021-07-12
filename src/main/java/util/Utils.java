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

    public static int boolArrayToInt(boolean[] bools) {
        int x = 0;
        for (int i = 0; i < bools.length; i++)
            if (bools[i]) x |= (1 << i);
        return x;
    }

    public static BitSet intToBitSet(int nAttributes, int n) {
        BitSet bs = new BitSet(nAttributes);
        for (int i = 0; i < nAttributes; i++)
            if ((n & (1 << i)) != 0) bs.set(i);
        return bs;
    }

    public static BitSet longToBitSet(int nAttributes, long n) {
        BitSet bs = new BitSet(nAttributes);
        for (int i = 0; i < nAttributes; i++)
            if ((n & (1L << i)) != 0) bs.set(i);
        return bs;
    }

    public static BitSet boolArrayToBitSet(boolean[] bools) {
        BitSet bs = new BitSet(bools.length);
        for (int i = 0; i < bools.length; i++)
            if (bools[i]) bs.set(i);
        return bs;
    }

    public static int bitsetToInt(int nAttributes, BitSet bs) {
        int x = 0;
        for (int i = 0; i < nAttributes; i++)
            if (bs.get(i)) x |= (1 << i);
        return x;
    }

    public static long bitsetToLong(int nAttributes, BitSet bs) {
        long x = 0;
        for (int i = 0; i < nAttributes; i++)
            if (bs.get(i)) x |= (1L << i);
        return x;
    }

}
