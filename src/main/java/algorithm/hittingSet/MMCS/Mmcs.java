package algorithm.hittingSet.MMCS;

import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * original MMCS algorithm for discovering minimal hitting sets
 */
public class Mmcs {

    int nElements;

    static long elementsMask;

    private List<MmcsNode> coverNodes = new ArrayList<>();

    /**
     * true iff there's an empty subset to cover (which could never be covered).
     * return no cover set if true but walk down without the empty subset
     */
    private boolean hasEmptySubset = false;


    public Mmcs(int nEle) {
        nElements = nEle;

        for (int i = 0; i < nEle; i++)
            elementsMask |= 1L << i;
    }

    public void initiate(List<Long> subsets) {
        if (Utils.removeEmptyLongSetUnsorted(subsets)) hasEmptySubset = true;
        coverNodes = walkDown(new MmcsNode(nElements, subsets));
    }

    public void initiate(List<Long> subsets, boolean sorted, boolean enableFmin) {
        if (enableFmin) {
            if (!sorted) Utils.sortLongSets(nElements, subsets);
            hasEmptySubset = Utils.removeEmptyLongSetSorted(subsets);
            subsets = Utils.findMinLongSets(subsets);
        } else {
            if (sorted) hasEmptySubset = Utils.removeEmptyLongSetSorted(subsets);
            else hasEmptySubset = Utils.removeEmptyLongSetUnsorted(subsets);
        }

        coverNodes = walkDown(new MmcsNode(nElements, subsets));
    }

    List<MmcsNode> walkDown(MmcsNode root) {
        List<MmcsNode> newCoverNodes = new ArrayList<>();
        walkDown(root, newCoverNodes);
        return newCoverNodes;
    }

    void walkDown(MmcsNode nd, List<MmcsNode> newNodes) {
        if (nd.isCover()) {
            nd.resetCand(elementsMask);
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
                MmcsNode childNode = nd.getChildNode(e, childCand);
                if (childNode.isGlobalMinimal()) {
                    walkDown(childNode, newNodes);
                    childCand |= 1L << e;
                }
            }
            e++;
            addCandidates >>>= 1;
        }

    }

    public List<Long> getMinCoverSets() {
        //return coverNodes.stream().map(MmcsNode::getElements).collect(Collectors.toList());

        return hasEmptySubset ? new ArrayList<>() : coverNodes.stream()
                .map(MmcsNode::getElements)
                .collect(Collectors.toList());
    }


}
