package algorithm.hittingSet.DynHS;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class DynHS {

    private final int nElements;

    long elementsMask;

    /**
     * true iff there's an empty edge to cover (which could never be covered).
     * return no cover if true but walk down without the empty set
     */
    boolean hasEmptyEdge;

    List<Long> minEdges;

    List<DynHSNode> coverNodes;


    public DynHS(int nEle) {
        nElements = nEle;

        for (int i = 0; i < nEle; i++)
            elementsMask |= 1L << i;
    }

    public void initiate(List<Long> edges) {
        // edges should be sorted by cardinality
        if (removeEmptyLongSet(edges)) hasEmptyEdge = true;
        minEdges = Utils.findMinLongSets(edges);
        coverNodes = walkDown(new DynHSNode(nElements, elementsMask, minEdges));
    }


    List<DynHSNode> walkDown(DynHSNode root) {
        List<DynHSNode> newCoverNodes = new ArrayList<>();
        walkDown(root, newCoverNodes, new HashSet<>());
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
            nd.clearCrit();
            newNodes.add(nd);
            return;
        }

        // configure cand for child nodes
        long childCand = nd.cand;
        long addCandidates = nd.getAddCandidates();
        childCand &= ~(addCandidates);

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


    public void insertEdges(List<Long> insertedEdges) {
        /* insertedEdges should be already sorted */
        if (removeEmptyLongSet(insertedEdges)) hasEmptyEdge = true;

        List<Long> newMinEdges = new ArrayList<>();
        Set<Long> rmvMinEdges = new HashSet<>();
        minEdges = findMinEdges(minEdges, insertedEdges, newMinEdges, rmvMinEdges);

        List<DynHSNode> coverNodes1 = new ArrayList<>();
        for (DynHSNode prevNode : coverNodes) {
            if (prevNode.insertEdges(minEdges))
                coverNodes1.add(prevNode);
        }

        coverNodes = walkDown(coverNodes1);
    }

    List<Long> findMinEdges(List<Long> oldMinEdges, List<Long> newEdges, List<Long> newMinEdges, Set<Long> rmvMinEdges) {
        List<Long> allMinSets = new ArrayList<>();    // min sets of all current sets

        boolean[] notMinNew = new boolean[newEdges.size()];
        boolean[] notMinOld = new boolean[oldMinEdges.size()];
        int[] newCars = newEdges.stream().mapToInt(Long::bitCount).toArray();
        int[] oldCars = oldMinEdges.stream().mapToInt(Long::bitCount).toArray();

        for (int i = 0, j = 0, car = 1; car <= nElements; car++) {          // for each layer of cardinality
            if (i == oldMinEdges.size() && j == newEdges.size()) break;

            for (; i < oldMinEdges.size() && oldCars[i] == car; i++) {   // use old min to filter new min
                long sbi = oldMinEdges.get(i);
                if (notMinOld[i]) rmvMinEdges.add(sbi);
                else {
                    allMinSets.add(sbi);
                    for (int k = newEdges.size() - 1; k >= 0 && car < newCars[k]; k--)
                        if (!notMinNew[k] && Utils.isSubset(sbi, newEdges.get(k))) notMinNew[k] = true;
                }
            }

            for (; j < newEdges.size() && newCars[j] == car; j++) {          // use new min to filter old and new min
                if (notMinNew[j]) continue;
                long sbj = newEdges.get(j);
                allMinSets.add(sbj);
                newMinEdges.add(sbj);
                for (int k = oldMinEdges.size() - 1; k >= 0 && car < oldCars[k]; k--)
                    if (!notMinOld[k] && Utils.isSubset(sbj, oldMinEdges.get(k))) notMinOld[k] = true;
                for (int k = newEdges.size() - 1; k >= 0 && car < newCars[k]; k--)
                    if (!notMinNew[k] && Utils.isSubset(sbj, newEdges.get(k))) notMinNew[k] = true;
            }
        }

        return allMinSets;
    }


    public void removeEdges(List<Long> leftEdges, List<Long> rmvdEdges) {
        /* leftEdges and rmvdEdges should be already sorted */
        if (removeEmptyLongSet(leftEdges)) hasEmptyEdge = true;
        if (removeEmptyLongSet(rmvdEdges)) hasEmptyEdge = false;

        // 1 remove obsolete min Edges from minEdges
        List<Long> minRmvdEdges = findRemovedMinEdges(rmvdEdges);

        // 2 find all min exposed Edges in leftEdges and add to minEdges
        Set<Long> minExposedSets = findMinExposedLongSets(minRmvdEdges, leftEdges);
        minEdges.addAll(minExposedSets);
        Utils.sortLongSets(nElements, minEdges);

        // 3 update Hs(F) by removing affected vertices from all nodes
        coverNodes = removeVerticesFromNodes(minRmvdEdges);

        // 4 walk down from each node in the updated Hs(F)
        coverNodes = walkDown(coverNodes);
    }

    List<Long> findRemovedMinEdges(List<Long> removedEdges) {
        Set<Long> removed = new HashSet<>(removedEdges);
        List<Long> rmvdMinEdges = new ArrayList<>();
        List<Long> leftMinEdges = new ArrayList<>();

        for (long set : minEdges) {
            if (removed.contains(set)) rmvdMinEdges.add(set);
            else leftMinEdges.add(set);
        }

        minEdges = leftMinEdges;
        return rmvdMinEdges;
    }

    Set<Long> findMinExposedLongSets(List<Long> minRemovedSets, List<Long> leftSubsets) {
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

    List<DynHSNode> removeVerticesFromNodes(List<Long> minRmvdEdges) {
        long affected = 0;
        for (long minRmvdEdge : minRmvdEdges)
            affected |= minRmvdEdge;

        Set<Long> walked = new HashSet<>();
        List<DynHSNode> newCoverNodes = new ArrayList<>();

        for (DynHSNode nd : coverNodes) {
            long parentElements = nd.elements & ~affected;
            if (walked.add(parentElements)) {
                nd.removeVertices(parentElements, elementsMask, minEdges);
                newCoverNodes.add(nd);
            }
        }

        return newCoverNodes;
    }


    public List<Long> getSortedMinCoverSets() {
        return hasEmptyEdge ? new ArrayList<>() : coverNodes.stream()
                .map(DynHSNode::getElements)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Long> getMinCoverSets() {
        return hasEmptyEdge ? new ArrayList<>() : coverNodes.stream().map(DynHSNode::getElements).collect(Collectors.toList());
    }

    public boolean removeEmptyLongSet(List<Long> sets) {
        // sets should be sorted already
        if (sets.isEmpty() || sets.get(0) != 0) return false;
        sets.remove(0);
        return true;
    }

}
