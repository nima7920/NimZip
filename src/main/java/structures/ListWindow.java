package structures;

import java.util.ArrayList;
import java.util.Arrays;

public class ListWindow implements Window {

    private int v, w;
    private long i = -1, j = -1;
    private ArrayList<Byte> characters;

    public ListWindow(int v) {
        this.v = v;
        w = (int) Math.pow(2, v);
        characters = new ArrayList<>();
    }

    @Override
    public void add(byte b) {
        if (i == -1) {
            i = 0;
            j = 0;
            characters.add(b);
        } else {
            j++;
            characters.add(b);
            if (j - i + 1 > w) {
                characters.remove(0);
                i++;
            }
        }
    }

    @Override
    public int search(ArrayList<Long> indices, byte[] buffer) {
        int k=0;
        long index=contains(buffer);
        while(index ==-1 && k<buffer.length){
            k++;
            index=contains(Arrays.copyOf(buffer,buffer.length-k));

        }
        if(index==-1){
            return 0;
        }else{
            indices.add(index);
            return (buffer.length-k);
        }
    }

    @Override
    public byte[] getByteBuffer(int size, int index) {
        byte[] buffer = new byte[size];
        for (int k = index; k < index + size; k++) {
            buffer[k - index] = characters.get(k);
        }
        return buffer;
    }

    @Override
    public long getHeadIndex() {
        return i;
    }

    public long contains(byte[] buffer) {

        if(buffer.length==0){
            return -1;
        }
        int bufferLen = buffer.length-1;
        int t = 0;
        int len = characters.size();

        // Iterate from 0 to len - 1
        for (int k = len - 1; k >= 0; k--) {
            if (t == buffer.length) {
                return k + 1;
            }
            if (characters.get(k) == buffer[bufferLen-t])
            t++;
            else
            t = 0;
        }

        return t < buffer.length ? -1 : 0;
    }
}
