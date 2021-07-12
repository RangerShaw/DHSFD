package algorithm.hittingSet.DynHS;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class DynHS {

    private final int nElements;

    static long elementsMask;


    /**
     * true iff there's an empty int to cover (which could never be covered).
     * return no cover set if true but walk down without the empty set
     */
    boolean hasEmptySubset;

    List<Long> minSubsets;


    List<DynHSNode> coverNodes;


    public DynHS(int nEle) {
        nElements = nEle;

        for (int i = 0; i < nEle; i++)
            elementsMask |= 1L << i;
    }

    public void initiate(List<Long> subsets) {
        // subsets should be sorted by cardinality
        if (removeEmptyLongSet(subsets)) hasEmptySubset = true;

        minSubsets = findMinLongSets(subsets);

        coverNodes = walkDown(new DynHSNode(nElements, minSubsets));
    }

    public List<Long> findMinLongSets(List<Long> sets) {
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


    List<DynHSNode> walkDown(DynHSNode root) {
        Set<Long> walked = new HashSet<>();
        List<DynHSNode> newCoverNodes = new ArrayList<>();

        walkDown(root, newCoverNodes, walked);

        return newCoverNodes;
    }

    List<DynHSNode> walkDown(List<DynHSNode> oldCoverNodes) {
        Set<Long> walked = new HashSet<>();
        List<DynHSNode> newCoverNodes = new ArrayList<>();

        for (DynHSNode oldNode : oldCoverNodes)
            walkDown(oldNode, newCoverNodes, walked);

        return newCoverNodes;
    }

    void walkDown(DynHSNode nd, List<DynHSNode> newNodes, Set<Long> walked) {
        if (!walked.add(nd.elements)) return;

        if (nd.isCover()) {
            nd.resetCand(elementsMask);
            newNodes.add(nd);
            return;
        }

        // configure cand for child nodes
        long childCand = nd.cand;
        long addCandidates = nd.getAddCandidates();
        childCand &= ~(addCandidates);

        {
            int e = 0;
            while (addCandidates > 0) {
                if ((addCandidates & 1) != 0L) {
                    DynHSNode childNode = nd.getChildNode(e, childCand);
                    if (childNode.isGlobalMinimal()) {
                        walkDown(childNode, newNodes, walked);
                        childCand |= 1L << e;
                    }
                }
                e++;
                addCandidates >>>= 1;
            }
        }
    }


    public void insertSubsets(List<Long> insertedSubsets) {
        /* insertedSubsets should be already sorted */
        if (removeEmptyLongSet(insertedSubsets)) hasEmptySubset = true;

        List<Long> newMinSubsets = new ArrayList<>();
        Set<Long> rmvMinSubsets = new HashSet<>();
        minSubsets = findMinLongSets(minSubsets, insertedSubsets, newMinSubsets, rmvMinSubsets);

        List<DynHSNode> coverNodes1 = new ArrayList<>();
        for (DynHSNode prevNode : coverNodes) {
            if (prevNode.insertSubsets(newMinSubsets, rmvMinSubsets))
                coverNodes1.add(prevNode);
        }

        coverNodes = walkDown(coverNodes1);
    }

    public List<Long> findMinLongSets(List<Long> oldMinSets, List<Long> newSets, List<Long> newMinSets, Set<Long> removed) {
        List<Long> allMinSets = new ArrayList<>();    // min sets of all current sets

        boolean[] notMinNew = new boolean[newSets.size()];
        boolean[] notMinOld = new boolean[oldMinSets.size()];
        int[] newCars = newSets.stream().mapToInt(Long::bitCount).toArray();
        int[] oldCars = oldMinSets.stream().mapToInt(Long::bitCount).toArray();

        for (int i = 0, j = 0, car = 1; car <= nElements; car++) {          // for each layer of cardinality
            if (i == oldMinSets.size() && j == newSets.size()) break;

            for (; i < oldMinSets.size() && oldCars[i] == car; i++) {   // use old min to filter new min
                long sbi = oldMinSets.get(i);
                if (notMinOld[i]) removed.add(sbi);
                else {
                    allMinSets.add(sbi);
                    for (int k = newSets.size() - 1; k >= 0 && car < newCars[k]; k--)
                        if (!notMinNew[k] && Utils.isSubset(sbi, newSets.get(k))) notMinNew[k] = true;
                }
            }

            for (; j < newSets.size() && newCars[j] == car; j++) {          // use new min to filter old and new min
                if (notMinNew[j]) continue;
                long sbj = newSets.get(j);
                allMinSets.add(sbj);
                newMinSets.add(sbj);
                for (int k = oldMinSets.size() - 1; k >= 0 && car < oldCars[k]; k--)
                    if (!notMinOld[k] && Utils.isSubset(sbj, oldMinSets.get(k))) notMinOld[k] = true;
                for (int k = newSets.size() - 1; k >= 0 && car < newCars[k]; k--)
                    if (!notMinNew[k] && Utils.isSubset(sbj, newSets.get(k))) notMinNew[k] = true;
            }
        }

        return allMinSets;
    }


    public void removeSubsets(List<Long> leftSubsets, List<Long> rmvdSubsets) {
        /* leftSubsets and rmvdSubsets should be already sorted */
        if (removeEmptyLongSet(leftSubsets)) hasEmptySubset = true;
        if (removeEmptyLongSet(rmvdSubsets)) hasEmptySubset = false;

        // 1 remove obsolete min subsets from minSubsets
        List<Long> minRmvdSubsets = findRemovedMinLongSets(rmvdSubsets);
        Set<Long> minRemoved = new HashSet<>(minRmvdSubsets);

        // 2 find all min exposed subsets in leftSubsets and add to minSubsets
        Set<Long> minExposedSets = findMinExposedLongSets(minRmvdSubsets, leftSubsets);
        minSubsets.addAll(minExposedSets);
        Utils.sortLongSets(nElements, minSubsets);

        // 3 update Hs(F) by removing affected vertices from all nodes
        coverNodes = removeVerticesFromNodes(minRmvdSubsets, minRemoved);

        // 4 walk down from each node in the updated Hs(F)
        coverNodes = walkDown(coverNodes);
    }

    public List<Long> findRemovedMinLongSets(List<Long> removedSets) {
        Set<Long> removed = new HashSet<>(removedSets);
        List<Long> minRmvdSubsets = new ArrayList<>();
        List<Long> newMinSets = new ArrayList<>(Math.max(10, minSubsets.size() - removed.size() / 2));

        for (long set : minSubsets) {
            if (removed.contains(set)) minRmvdSubsets.add(set);
            else newMinSets.add(set);
        }

        minSubsets = newMinSets;

        return minRmvdSubsets;
    }

    public Set<Long> findMinExposedLongSets(List<Long> minRemovedSets, List<Long> leftSubsets) {
        Set<Long> minExposedSets = new HashSet<>();
        int[] leftCar = leftSubsets.stream().mapToInt(Long::bitCount).toArray();

        for (long minRemovedSet : minRemovedSets) {
            int car = Long.bitCount(minRemovedSet);
            for (int j = leftSubsets.size() - 1; j >= 0 && leftCar[j] > car; j--) {
                if (Utils.isSubset(minRemovedSet, leftSubsets.get(j)) && !minExposedSets.contains(leftSubsets.get(j))) {
                    int k = 0;
                    for (; leftCar[k] < leftCar[j] && !Utils.isSubset(leftSubsets.get(k), leftSubsets.get(j)); k++) ;
                    if (leftCar[k] >= leftCar[j]) minExposedSets.add(leftSubsets.get(j));
                }
            }
        }
        return minExposedSets;
    }

    List<DynHSNode> removeVerticesFromNodes(List<Long> minRmvdSubsets, Set<Long> removedSets) {
        long affected = 0;
        for (long minRmvdSubset : minRmvdSubsets)
            affected |= minRmvdSubset;

        List<Long> pending = new ArrayList<>();
        for (long set : minSubsets) {
            if ((set & affected) != 0) pending.add(set);
        }

        Set<Long> walked = new HashSet<>();
        List<DynHSNode> newCoverNodes = new ArrayList<>(coverNodes.size());

        Integer[] removedEles = Utils.indicesOfOnes(affected).toArray(new Integer[0]);
        for (DynHSNode nd : coverNodes) {
            long parentElements = nd.elements & ~affected;
            if (walked.add(parentElements)) {
                nd.removeVertices(parentElements, elementsMask, removedSets, removedEles, pending);
                newCoverNodes.add(nd);
            }
        }

        return newCoverNodes;
    }


    public List<Long> getSortedMinCoverSets() {
        return hasEmptySubset ? new ArrayList<>() : coverNodes.stream()
                .map(DynHSNode::getElements)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Long> getMinCoverSets() {
        return hasEmptySubset ? new ArrayList<>() : coverNodes.stream()
                .map(DynHSNode::getElements)
                .collect(Collectors.toList());
    }

    public boolean removeEmptyLongSet(List<Long> sets) {
        // sets should be sorted already
        if (sets.isEmpty() || sets.get(0) != 0) return false;
        sets.remove(0);
        return true;
    }

}
