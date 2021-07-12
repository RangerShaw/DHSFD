package algorithm.hittingSet.BHMMCS;

import algorithm.hittingSet.NumSet;

import java.util.*;

public class DynHSNode {

    long elements;

    long cand;


    private List<Long> uncov;

    List<List<Long>> crit;


    private DynHSNode() {
    }

    /**
     * for initiation only
     */
    DynHSNode(int nEle, List<Long> setsToCover) {
        elements = 0;
        uncov = new ArrayList<>(setsToCover);

        cand = DynHS.elementsMask;

        crit = new ArrayList<>(nEle);
        for (int i = 0; i < nEle; i++)
            crit.add(new ArrayList<>());
    }

    long getElements() {
        return elements;
    }

    boolean isCover() {
        return uncov.isEmpty();
    }

    public boolean isGlobalMinimal() {
        int e = 0;
        long ele = elements;
        while (ele > 0) {
            if ((ele & 1) != 0L && crit.get(e).isEmpty()) return false;
            e++;
            ele >>>= 1;
        }
        return true;
    }


    long getAddCandidates() {
        Comparator<Long> cmp = Comparator.comparing(sb -> Long.bitCount(cand & sb));

        /* different strategies: min may be the fastest */
        // return cand & Collections.max(uncov, cmp);
        // return cand & uncov.get(0);
        return cand & Collections.min(uncov, cmp);
    }

    DynHSNode getChildNode(int e, long childCand) {
        DynHSNode childNode = new DynHSNode();
        childNode.updateContextFromParent(e, childCand, this);
        return childNode;
    }

    void updateContextFromParent(int e, long outerCand, DynHSNode parentNode) {
        elements = parentNode.elements | (1L << e);
        cand = outerCand;

        crit = new ArrayList<>();
        for (int i = 0; i < parentNode.crit.size(); i++) {
            List<Long> critI = new ArrayList<>();
            for (long set : parentNode.crit.get(i))
                if ((set & (1L << e)) == 0) critI.add(set);
            crit.add(critI);
        }

        uncov = new ArrayList<>();
        for (long sb : parentNode.uncov) {
            if ((sb & (1L << e)) != 0) crit.get(e).add(sb);
            else uncov.add(sb);
        }
    }


    boolean insertSubsets(List<Long> newSubsets, Set<Long> rmvMinSubsets) {
        List<Integer> eles = NumSet.indicesOfOnes(elements);

        for (int e : eles)
            crit.get(e).removeAll(rmvMinSubsets);

        for (long newSb : newSubsets) {
            int critCover = getCritCover(newSb);
            if (critCover == -1) uncov.add(newSb);
            else if (critCover >= 0) crit.get(critCover).add(newSb);
        }

        for (int e : eles)
            if (crit.get(e).isEmpty()) return false;
        return true;
    }

    void removeVertices(long newElements, long mask, Set<Long> removedSets, Integer[] removedVertices, List<Long> pending) {
        elements = newElements;

        cand = (~elements) & mask;

        for (List<Long> critI : crit)
            if (!critI.isEmpty()) critI.removeAll(removedSets);

        for (int e : removedVertices)
            crit.get(e).clear();

        for (long set : pending) {
            int critCover = getCritCover(set);
            if (critCover == -1) uncov.add(set);
            else if (critCover >= 0) crit.get(critCover).add(set);
        }
    }


    void resetCand(long mask) {
        cand = ~elements & mask;
    }

    /**
     * @return -1 if sb is NOT covered by this node; -2 if sb is covered by at least 2 elements
     */
    int getCritCover(long sb) {
        long and = sb & elements;
        if (and == 0L) return -1;

        int ffs = Long.numberOfTrailingZeros(and);
        return and == (1L << ffs) ? ffs : -2;
    }
}
