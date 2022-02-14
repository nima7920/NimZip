package structures;

import java.util.ArrayList;

public class HashWindow implements Window{
    private int v, w;
    private long i = -1, j = -1;
    private ArrayList<Byte> characters;
    private ByteHash byteHash;

    public HashWindow(int v) {
        this.v = v;
        w = (int) Math.pow(2, v);
        characters = new ArrayList<>();
        byteHash = new ByteHash();
    }

    public void add(byte b) {
        if (i == -1) {
            i = 0;
            j = 0;
            characters.add(b);
            byteHash.addIndex(b, 0);
        } else {
            j++;
            characters.add(b);
            byteHash.addIndex(b, j);
            if (j - i + 1 > w) {
                byteHash.deleteFirstIndex(characters.remove(0));
                i++;
            }
        }
    }

    public int search(ArrayList<Long> result,byte[] buffer){
        return byteHash.search(result,buffer);
    }

    public byte[] getByteBuffer(int size,int index){
        byte[] buffer=new byte[size];
        for (int k = index; k <index+size ; k++) {
            buffer[k-index]=characters.get(k);
        }
        return buffer;
    }

    public long getHeadIndex(){
        return i;
    }


    /**
     *  methods for testing
     */
    public void print(){
        System.out.println("Queue: " + characters.toString());
    }
}
