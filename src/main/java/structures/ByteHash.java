package structures;

import java.util.ArrayList;
import java.util.Arrays;

public class ByteHash {
    private ArrayList<Long>[] indexHash;

    public ByteHash() {
        indexHash = new ArrayList[256];
    }

    public void addIndex(byte value, long index) {
        int i = value;
        if (indexHash[i] == null)
            indexHash[i] = new ArrayList<>();
        indexHash[i].add(index);

    }

    public void deleteFirstIndex(byte value) {
        indexHash[value].remove(0);
    }

    public int search(ArrayList<Long> result, byte[] buffer) {
        ArrayList<Long> indexes,tempIndexes;
        int size = 0;
        if (buffer.length == 0)
            return 0;
        if (indexHash[buffer[0]] != null) {
            indexes = (ArrayList) indexHash[buffer[0]].clone();
            if (indexes.size() == 0) {
                return 0;
            }
        } else {
            return 0;
        }
        size++;

        while (size < buffer.length ){
            tempIndexes = combineLists(indexes, indexHash[buffer[size]], size);
            if(tempIndexes.size()==0){
                result.addAll(indexes);
                return size;
            }else{
                indexes=tempIndexes;
            }
            size++;
        }
        result.addAll(indexes);
        return size;
    }

    ArrayList combineLists(ArrayList<Long> list1, ArrayList<Long> list2, int distance) {
        ArrayList<Long> result = new ArrayList<>();
        if (list2 == null)
            return result;
        int i = 0, j = 0;
        while (i < list1.size()) {
            while (j < list2.size() && list1.get(i) + distance > list2.get(j))
                j++;
            if (j < list2.size() && list1.get(i) + distance == list2.get(j)) {
                result.add(list1.get(i));
                j++;
            }
            i++;
        }
        return result;
    }

    @Override
    public String toString() {
        return "ByteHash{" +
                "indexHash=" + Arrays.toString(indexHash) +
                '}';
    }
}
