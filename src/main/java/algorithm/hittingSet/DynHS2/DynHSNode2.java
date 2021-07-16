//package algorithm.hittingSet.DynHS2;
//
//import java.util.*;
//
//public class DynHSNode2 {
//
//    long elements;
//
//    long cand;
//
//    private List<Long> uncov;
//
//    List<List<Long>> crit;
//
//
//    private DynHSNode2() {
//    }
//
//    /* for initiation only */
//    DynHSNode2(int nEle, long mask, List<Long> edges) {
//        elements = 0;
//        uncov = new ArrayList<>(edges);
//        cand = mask;
//
//        crit = new ArrayList<>(nEle);
//        for (int i = 0; i < nEle; i++)
//            crit.add(new ArrayList<>());
//    }
//
//    long getElements() {
//        return elements;
//    }
//
//    boolean isCover() {
//        return uncov.isEmpty();
//    }
//
//    public boolean isGlobalMinimal() {
//        int e = 0;
//        long ele = elements;
//        while (ele > 0) {
//            if ((ele & 1) != 0L && crit.get(e).isEmpty()) return false;
//            e++;
//            ele >>>= 1;
//        }
//        return true;
//    }
//
//
//    long getAddCandidates() {
//        Comparator<Long> cmp = Comparator.comparing(sb -> Long.bitCount(cand & sb));
//
//        /* different strategies: min may be the fastest */
//        // return cand & Collections.max(uncov, cmp);
//        // return cand & uncov.get(0);
//        return cand & Collections.min(uncov, cmp);
//    }
//
//    DynHSNode2 getChildNode(int e, long childCand) {
//        DynHSNode2 childNode = new DynHSNode2();
//        childNode.updateContextFromParent(e, childCand, this);
//        return childNode;
//    }
//
//    void updateContextFromParent(int e, long outerCand, DynHSNode2 parentNode) {
//        elements = parentNode.elements | (1L << e);
//        cand = outerCand;
//
//        crit = new ArrayList<>();
//        for (int i = 0; i < parentNode.crit.size(); i++) {
//            List<Long> critI = new ArrayList<>();
//            for (long edge : parentNode.crit.get(i))
//                if ((edge & (1L << e)) == 0) critI.add(edge);
//            crit.add(critI);
//        }
//
//        uncov = new ArrayList<>();
//        for (long edge : parentNode.uncov) {
//            if ((edge & (1L << e)) != 0) crit.get(e).add(edge);
//            else uncov.add(edge);
//        }
//    }
//
//
//    boolean insertEdges(List<Long> newEdges, Set<Long> rmvdMinEdges) {
//        for (List<Long> critI : crit)
//            if (!critI.isEmpty()) critI.removeAll(rmvdMinEdges);
//
//        for (long newEdge : newEdges) {
//            int critCover = getCritCover(newEdge);
//            if (critCover == -1) uncov.add(newEdge);
//            else if (critCover >= 0) crit.get(critCover).add(newEdge);
//        }
//
//        for (int e = 0, size = crit.size(); e < size; e++) {
//            if ((elements & (1L << e)) != 0 && crit.get(e).isEmpty())
//                return false;
//        }
//        return true;
//    }
//
//    void removeVertices(long newElements, long mask, Set<Long> removedEdges, Integer[] removedVertices, List<Long> pending) {
//        elements = newElements;
//        cand = (~elements) & mask;
//
//        for (List<Long> critI : crit)
//            if (!critI.isEmpty()) critI.removeAll(removedEdges);
//
//        for (int e : removedVertices)
//            crit.get(e).clear();
//
//        for (long edge : pending) {
//            int critCover = getCritCover(edge);
//            if (critCover == -1) uncov.add(edge);
//            else if (critCover >= 0) crit.get(critCover).add(edge);
//        }
//    }
//
//
//    void resetCand(long mask) {
//        cand = ~elements & mask;
//    }
//
//    /**
//     * @return -1 if sb is NOT covered by this node; -2 if sb is covered by at least 2 elements
//     */
//    int getCritCover(long sb) {
//        long and = sb & elements;
//        if (and == 0L) return -1;
//        int ffs = Long.numberOfTrailingZeros(and);
//        return and == (1L << ffs) ? ffs : -2;
//    }
//
//}
