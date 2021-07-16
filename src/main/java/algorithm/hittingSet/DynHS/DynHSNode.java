package algorithm.hittingSet.DynHS;

import java.util.*;

public class DynHSNode {

    long elements;

    long cand;

    private List<Long> uncov;

    List<List<Long>> crit;


    private DynHSNode() {
    }

    /* for initiation only */
    DynHSNode(int nEle, long mask, List<Long> edges) {
        elements = 0;
        uncov = new ArrayList<>(edges);
        cand = mask;

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
            for (long edge : parentNode.crit.get(i))
                if ((edge & (1L << e)) == 0) critI.add(edge);
            crit.add(critI);
        }

        uncov = new ArrayList<>();
        for (long edge : parentNode.uncov) {
            if ((edge & (1L << e)) != 0) crit.get(e).add(edge);
            else uncov.add(edge);
        }
    }


    boolean insertEdges(List<Long> edges) {
        for (long edge : edges) {
            int critCover = getCritCover(edge);
            if (critCover == -1) uncov.add(edge);
            else if (critCover >= 0) crit.get(critCover).add(edge);
        }

        for (int e = 0, size = crit.size(); e < size; e++) {
            if ((elements & (1L << e)) != 0 && crit.get(e).isEmpty())
                return false;
        }
        return true;
    }

    void removeVertices(long newElements, long mask, List<Long> edges) {
        elements = newElements;
        cand = (~elements) & mask;

        for (long edge : edges) {
            int critCover = getCritCover(edge);
            if (critCover == -1) uncov.add(edge);
            else if (critCover >= 0) crit.get(critCover).add(edge);
        }
    }


    void resetCand(long mask) {
        cand = ~elements & mask;
    }

    void clearCrit() {
        for (List<Long> c : crit)
            c.clear();
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
