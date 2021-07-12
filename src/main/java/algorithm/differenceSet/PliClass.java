package algorithm.differenceSet;

import java.util.*;

public class PliClass {

    public int nTuples = 0;

    public int nAttributes;

    /**
     * pliMap[e]["abc"] -> on attribute e, to which cluster "abc" belongs
     */
    List<Map<String, Integer>> pliMap = new ArrayList<>();

    /**
     * pli[e]: clusters on attribute e;
     * pli[e][j]: on attribute e, cluster j contains what tuples
     */
    List<List<List<Integer>>> pli = new ArrayList<>();

    /**
     * inversePli[t][e]: inverse mapping tuple t on attribute e to its cluster ID
     */
    List<List<Integer>> inversePli = new ArrayList<>();


    public PliClass() {
    }

    void initiateDataStructure(List<List<String>> data) {
        nTuples = data.size();
        nAttributes = data.isEmpty() ? 0 : data.get(0).size();

        for (int e = 0; e < nAttributes; e++) {
            pli.add(new ArrayList<>());
            pliMap.add(new HashMap<>());
        }

        for (int t = 0; t < nTuples; t++)
            inversePli.add(new ArrayList<>(nAttributes));
    }

    public List<List<List<Integer>>> generatePLI(List<List<String>> data) {
        initiateDataStructure(data);

        for (int e = 0; e < nAttributes; e++) {
            Map<String, Integer> pliMapE = pliMap.get(e);
            List<List<Integer>> pliE = pli.get(e);

            for (int t = 0; t < data.size(); t++) {
                Integer clstId = pliMapE.get(data.get(t).get(e));
                if (clstId == null) {       // new cluster
                    clstId = pliE.size();
                    pliMapE.put(data.get(t).get(e), clstId);
                    pliE.add(new ArrayList<>());
                }
                pliE.get(clstId).add(t);
                inversePli.get(t).add(clstId);
            }
        }

        return pli;
    }

    public List<List<List<Integer>>> genPliMapAndInversePli(List<List<String>> data) {
        nAttributes = data.isEmpty() ? 0 : data.get(0).size();

        for (int e = 0; e < nAttributes; e++) {
            pli.add(new ArrayList<>());
            pliMap.add(new HashMap<>());
        }

        insertData(data);

        return pli;
    }


    public void insertData(List<List<String>> insertedData) {
        /* update pliMap and inversePli, pli will be updated in DifferenceSet */
        for (int i = 0; i < insertedData.size(); i++)
            inversePli.add(new ArrayList<>());

        for (int e = 0; e < nAttributes; e++) {
            Map<String, Integer> pliMapE = pliMap.get(e);
            for (int t = 0; t < insertedData.size(); t++) {
                Integer clstId = pliMapE.get(insertedData.get(t).get(e));
                if (clstId == null) {       // new cluster
                    clstId = pliMapE.size();
                    pliMapE.put(insertedData.get(t).get(e), clstId);
                }
                inversePli.get(t + nTuples).add(clstId);
            }
        }

        nTuples += insertedData.size();
    }


    public void removeData(List<Integer> removedTuples, boolean[] removed) {
        // update inversePli, generate newId for remaining tuples
        int[] newId = new int[inversePli.size()];

        removedTuples.add(removedTuples.size(), inversePli.size());     // add pseudo tail
        for (int i = 0; i < removedTuples.size() - 1; i++) {
            int l = removedTuples.get(i), r = removedTuples.get(i + 1);
            newId[l] = -1;
            for (int j = l + 1; j < r; j++) {
                newId[j] = j - i - 1;
                inversePli.set(newId[j], inversePli.get(j));
            }
        }
        removedTuples.remove(removedTuples.size() - 1);         // remove pseudo tail

        inversePli.subList(inversePli.size() - removedTuples.size(), inversePli.size()).clear();

        // update pli
        for (List<List<Integer>> pliE : pli) {
            for (List<Integer> clst : pliE) {
                clst.removeIf(i -> removed[i]);
                for (int i = 0; i < clst.size(); i++) {
                    int firstRemovedIndex = removedTuples.get(0);
                    if (clst.get(i) > firstRemovedIndex) clst.set(i, newId[clst.get(i)]);
                }
            }
        }

        nTuples -= removedTuples.size();
    }


    public List<List<List<Integer>>> getPli() {
        return pli;
    }

    public List<List<Integer>> getInversePli() {
        return inversePli;
    }

    public int getTupleCount() {
        return nTuples;
    }

}
