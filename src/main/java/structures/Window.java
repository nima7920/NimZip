package structures;

import java.util.ArrayList;

public interface Window {

    public void add(byte b);
    public int search(ArrayList<Long> indices,byte[] buffer);
    public byte[] getByteBuffer(int size,int index);
    public long getHeadIndex();
}
