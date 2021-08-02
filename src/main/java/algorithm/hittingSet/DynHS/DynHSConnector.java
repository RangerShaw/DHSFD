package algorithm.hittingSet.DynHS;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * translate FD Discovery into Hitting Set Enumeration on each RHS
 */
public class DynHSConnector {

    int nElements;

    List<DynHS> DynHSList = new ArrayList<>();          // DynHS algorithms on each RHS

    List<List<BitSet>> minFDs = new ArrayList<>();      // min HSs on each RHS


    public DynHSConnector() {
    }

    public void initiate(int nElements, List<Long> toCover) {
        this.nElements = nElements;
        Utils.sortLongSets(nElements, toCover);
        List<List<Long>> subsetParts = genSubsetRhss(toCover);

        for (int rhs = 0; rhs < nElements; rhs++) {
            DynHSList.add(new DynHS(nElements));
            DynHSList.get(rhs).initiate(subsetParts.get(rhs));
            minFDs.add(DynHSList.get(rhs).getMinCoverSets().stream().map(sb -> Utils.longToBitSet(nElements, sb)).collect(Collectors.toList()));
        }
    }


    public List<List<BitSet>> insertSubsets(List<Long> addedSets) {
        Utils.sortLongSets(nElements, addedSets);
        List<List<Long>> subsetParts = genSubsetRhss(addedSets);

        for (int rhs = 0; rhs < nElements; rhs++) {
            DynHSList.get(rhs).insertEdges(subsetParts.get(rhs));
            minFDs.set(rhs, DynHSList.get(rhs).getMinCoverSets().stream().map(sb -> Utils.longToBitSet(nElements, sb)).collect(Collectors.toList()));
        }
        return new ArrayList<>(minFDs);
    }

    public List<List<BitSet>> removeSubsets(List<Long> leftDiffs, Set<Long> removed) {
        // leftDiffs should be already sorted
        List<List<Long>> leftSubsetRhss = genSubsetRhss(leftDiffs);

        List<Long> rmvdDiffs = new ArrayList<>(removed);
        Utils.sortLongSets(nElements, rmvdDiffs);
        List<List<Long>> rmvdSubsetRhss = genSubsetRhss(rmvdDiffs);

        for (int rhs = 0; rhs < nElements; rhs++) {
            DynHSList.get(rhs).removeEdges(leftSubsetRhss.get(rhs), rmvdSubsetRhss.get(rhs));
            minFDs.set(rhs, DynHSList.get(rhs).getMinCoverSets().stream().map(sb -> Utils.longToBitSet(nElements, sb)).collect(Collectors.toList()));
        }
        return new ArrayList<>(minFDs);
    }


    List<List<Long>> genSubsetRhss(List<Long> subsets) {
        List<List<Long>> subsetParts = new ArrayList<>(nElements);

        for (int i = 0; i < nElements; i++)
            subsetParts.add(new ArrayList<>(subsets.size() / nElements));

        for (long set : subsets) {
            long tmp = set;
            int pos = 0;
            while (tmp > 0) {
                if ((tmp & 1) != 0) subsetParts.get(pos).add(set & ~(1L << pos));
                pos++;
                tmp >>>= 1;
            }
        }
        return subsetParts;
    }

    public List<List<BitSet>> getMinFDs() {
        return new ArrayList<>(minFDs);
    }

}
