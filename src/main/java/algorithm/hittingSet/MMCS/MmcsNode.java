package algorithm.hittingSet.MMCS;

import util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MmcsNode {

    private int nElements;

    Long elements;

    Long cand;

    private List<Long> uncov;

    private ArrayList<ArrayList<Long>> crit;


    private MmcsNode(int nEle) {
        nElements = nEle;
    }

    /**
     * for initiation only
     */
    MmcsNode(int nEle, List<Long> subsetsToCover) {
        nElements = nEle;
        elements = 0L;
        uncov = new ArrayList<>(subsetsToCover);

        cand = Mmcs.elementsMask;

        crit = new ArrayList<>(nElements);
        for (int i = 0; i < nElements; i++)
            crit.add(new ArrayList<>());
    }

    Long getElements() {
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

    /**
     * find an uncovered subset with the optimal intersection with cand,
     * return its intersection with cand
     */
    long getAddCandidates() {
        Comparator<Long> cmp = Comparator.comparing(sb -> Long.bitCount(cand & sb));

        /* different strategies: min may be the fastest */
        // return cand & Collections.max(uncov, cmp);
        // return cand & uncov.get(0);
        long m = Collections.min(uncov, cmp);
        return cand & Collections.min(uncov, cmp);
    }

    MmcsNode getChildNode(int e, Long childCand) {
        MmcsNode childNode = new MmcsNode(nElements);
        childNode.cloneContext(childCand, this);
        childNode.updateContextFromParent(e, this);
        return childNode;
    }

    void cloneContext(Long outerCand, MmcsNode originalNode) {
        elements = originalNode.elements;
        cand = outerCand;

        crit = new ArrayList<>(nElements);
        for (int i = 0; i < nElements; i++)
            crit.add(new ArrayList<>(originalNode.crit.get(i)));
    }

    void updateContextFromParent(int e, MmcsNode parentNode) {
        uncov = new ArrayList<>();

        for (long sb : parentNode.uncov) {
            if ((sb & (1L << e)) != 0) crit.get(e).add(sb);
            else uncov.add(sb);
        }

        for (int u : Utils.indicesOfOnes(elements))
            crit.get(u).removeIf(F -> (F & (1L << e)) != 0);

        elements |= 1L << e;
    }

    void resetCand(long mask) {
        cand = ~elements & mask;
    }
}
