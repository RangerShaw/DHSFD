package algorithm.differenceSet;

import com.koloboke.collect.map.hash.*;
import me.tongfei.progressbar.ProgressBar;
import util.DataIO;
import util.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DifferenceSet {

    int nAttributes;

    int nTuples;

    List<Long> diffSet = new ArrayList<>();

    /**
     * hashcode of a Diff -> count of its occurrences
     */
    HashLongLongMap diffFreq = HashLongLongMaps.newMutableMap();

    long fullDiff = 0;


    public DifferenceSet() {
    }

    void initiateDataStructure(List<List<Integer>> inversePli) {
        nAttributes = inversePli.isEmpty() ? 0 : inversePli.get(0).size();

        for (int i = 0; i < nAttributes; i++)
            fullDiff |= 1L << i;
    }

    public Map<BitSet, Long> generateDiffSet(List<List<List<Integer>>> pli, List<List<Integer>> inversePli) {
        initiateDataStructure(inversePli);

        initInsertData(pli, inversePli);

        Map<BitSet, Long> diffSetMap = new HashMap<>();
        for (Map.Entry<Long, Long> df : diffFreq.entrySet())
            diffSetMap.put(Utils.longToBitSet(nAttributes, df.getKey()), df.getValue());

        return diffSetMap;
    }

    public List<Long> generateDiffSet(List<List<Integer>> inversePli, String diffFp) {
        initiateDataStructure(inversePli);
        nTuples = inversePli.size();

        Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(diffFp);

        diffSet.addAll(diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList()));
        Utils.sortLongSets(nAttributes, diffSet);

        for (Map.Entry<BitSet, Long> df : diffSetMap.entrySet())
            diffFreq.put(Utils.bitsetToLong(nAttributes, df.getKey()), (long) df.getValue());

        return new ArrayList<>(diffSet);
    }

    /**
     * @return new Diffs
     */
    public List<Long> insertData(List<List<List<Integer>>> pli, List<List<Integer>> inversePli) {
        long[] diffHash = new long[inversePli.size()];

        List<Long> newDiffs = new ArrayList<>();

        // for each newly inserted tuple, generate its diffs with all front tuples
        for (int t = nTuples; t < inversePli.size(); t++) {
            // reset diffHash
            for (int i = 0; i < t; i++)
                diffHash[i] = fullDiff;

            // update pli, generate diffHash
            for (int e = 0; e < nAttributes; e++) {
                List<List<Integer>> pliE = pli.get(e);
                int clstId = inversePli.get(t).get(e);

                if (clstId >= pliE.size())                      // new cluster
                    pliE.add(new ArrayList<>());
                else {                                          // existing cluster
                    long mask = ~(1L << e);
                    for (int neighbor : pliE.get(clstId))
                        diffHash[neighbor] &= mask;
                }

                pliE.get(clstId).add(t);
            }

            // generate new diff
            for (int i = 0; i < t; i++) {
                if (diffFreq.addValue(diffHash[i], 1L, 0L) == 1L)
                    newDiffs.add(diffHash[i]);
            }
        }

        diffSet.addAll(newDiffs);
        Utils.sortLongSets(nAttributes, diffSet);

        nTuples = inversePli.size();

        return newDiffs;
    }

    public List<Long> insertData1(List<List<List<Integer>>> pli, List<List<Integer>> inversePli) {
        HashIntLongMap diffMap = HashIntLongMaps.newMutableMap();   // neighbor -> diff with it
        List<Long> newDiffs = new ArrayList<>();
        long nFullDiff = 0;

        // for each newly inserted tuple, generate its diffs with all front tuples
        for (int t = nTuples; t < inversePli.size(); t++) {
            diffMap.clear();

            // update pli, generate diffHash
            for (int e = 0; e < nAttributes; e++) {
                List<List<Integer>> pliE = pli.get(e);
                int clstId = inversePli.get(t).get(e);

                if (clstId >= pliE.size())                      // new cluster
                    pliE.add(new ArrayList<>());
                else {                                          // existing cluster
                    long mask = -(1L << e);
                    for (int neighbor : pliE.get(clstId))
                        diffMap.addValue(neighbor, mask, fullDiff);
                }

                pliE.get(clstId).add(t);
            }

            // generate new diff
            for (long diff : diffMap.values()) {
                if (diffFreq.addValue(diff, 1L, 0L) == 1L)
                    newDiffs.add(diff);
            }

            nFullDiff += t - diffMap.size();
        }

        if (nFullDiff > 0 && nFullDiff == diffFreq.addValue(fullDiff, nFullDiff, 0L))
            newDiffs.add(fullDiff);

        diffSet.addAll(newDiffs);
        Utils.sortLongSets(nAttributes, diffSet);

        nTuples = inversePli.size();

        return newDiffs;
    }

    public List<Long> initInsertData(List<List<List<Integer>>> pli, List<List<Integer>> inversePli) {
        long[] diffHash = new long[inversePli.size()];

        List<Long> newDiffs = new ArrayList<>();

        ProgressBar.wrap(IntStream.range(nTuples, inversePli.size()), "Task").forEach(t -> {
            //for (int t = nTuples; t < inversePli.size(); t++) {
            // reset diffHash
            for (int i = 0; i < t; i++)
                diffHash[i] = fullDiff;

            // update pli, generate diffHash
            for (int e = 0; e < nAttributes; e++) {
                List<List<Integer>> pliE = pli.get(e);
                int clstId = inversePli.get(t).get(e);

                if (clstId >= pliE.size())                      // new cluster
                    pliE.add(new ArrayList<>());
                else {                                          // existing cluster
                    long mask = ~(1L << e);
                    for (int neighbor : pliE.get(clstId))
                        diffHash[neighbor] &= mask;
                }

                pliE.get(clstId).add(t);
            }

            // generate new diff
            for (int i = 0; i < t; i++) {
                if (diffFreq.addValue(diffHash[i], 1L, 0L) == 1L)
                    newDiffs.add(diffHash[i]);
            }
        });

        diffSet.addAll(newDiffs);
        Utils.sortLongSets(nAttributes, diffSet);

        nTuples = inversePli.size();

        return newDiffs;
    }

    /**
     * @return remaining Diffs
     */
    public Set<Long> removeData(List<List<List<Integer>>> pli, List<List<Integer>> inversePli,
                                List<Integer> removedData, boolean[] removed) {
        Set<Long> removedDiffs = new HashSet<>();
        long[] diffHash = new long[inversePli.size()];

        for (int t : removedData) {
            // reset diffHash
            Arrays.fill(diffHash, fullDiff);

            // generate diffHash
            for (int e = 0; e < nAttributes; e++) {
                long mask = ~(1L << e);
                for (int neighbor : pli.get(e).get(inversePli.get(t).get(e)))
                    diffHash[neighbor] &= mask;
            }

            // generate removed diff
            for (int i = 0; i < diffHash.length; i++) {
                if ((!removed[i] || i < t) && diffFreq.addValue(diffHash[i], -1L) == 0L) {
                    diffFreq.remove(diffHash[i]);
                    removedDiffs.add(diffHash[i]);
                }
            }
        }

        diffSet.removeAll(removedDiffs);
        nTuples -= removed.length;

        return removedDiffs;
    }

    public Set<Long> removeData1(List<List<List<Integer>>> pli, List<List<Integer>> inversePli,
                                 List<Integer> removedTuples, boolean[] removed) {
        Set<Long> removedDiffs = new HashSet<>();
        HashIntLongMap diffMap = HashIntLongMaps.newMutableMap();
        long nFullDiff = 0;

        for (int i = 0; i < removedTuples.size(); i++) {
            int t = removedTuples.get(i);

            // reset diffHash
            diffMap.clear();

            // generate diffHash
            for (int e = 0; e < nAttributes; e++) {
                long mask = -(1L << e);
                for (int neighbor : pli.get(e).get(inversePli.get(t).get(e))) {
                    if (!removed[neighbor] || neighbor < t)
                        diffMap.addValue(neighbor, mask, fullDiff);
                }
            }

            // generate removed diff
            for (long diff : diffMap.values()) {
                if (diffFreq.addValue(diff, -1L) == 0L) {
                    removedDiffs.add(diff);
                    diffFreq.remove(diff);
                }
            }

            nFullDiff += nTuples - removedTuples.size() + i - diffMap.size();
        }

        if (nFullDiff > 0 && 0L == diffFreq.addValue(fullDiff, -nFullDiff))
            removedDiffs.add(fullDiff);

        diffSet.removeAll(removedDiffs);
        nTuples -= removed.length;

        return removedDiffs;
    }

    public List<Long> getDiffSet() {
        return new ArrayList<>(diffSet);
    }

    public Map<Long, Long> getDiffFreq() {
        return diffFreq;
    }
}