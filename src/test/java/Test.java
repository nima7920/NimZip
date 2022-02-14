import graphics.AppScreen;
import structures.Compressor;
import structures.HashWindow;
import structures.ListWindow;
import structures.Window;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    //    static HashWindow window = new HashWindow(3);
    static ListWindow window = new ListWindow(3);
    static byte[] buffer = new byte[]{1, 2, 3, 4, 5, 6, 6};
    static Compressor compressor = new Compressor(30, 7);
    static String string = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    public static void main(String[] args) {
//        windowTest();
//        compressorTest();
//        shiftBuffer(3);
//        System.out.println(Arrays.toString(buffer));
//        decompressionTest();
        windowTest();
    }

    static void windowTest() {
        ArrayList<Long> indices = new ArrayList<>();
        window.add((byte) 4);
        window.add((byte) 2);
        window.add((byte) 1);
        window.add((byte) 5);
        window.add((byte) 6);
        window.add((byte) 4);
        window.add((byte) 4);
        window.add((byte) 3);
        window.add((byte) 3);
        window.add((byte) 5);
        window.add((byte) 1);
        window.add((byte) 1);
        window.add((byte) 2);
        window.add((byte) 3);
        window.add((byte) 6);
//        searchTest();
        compressorTest();
        //        System.out.println(window.search(indices, buffer));
//        System.out.println(indices.toString());
    }

    static void compressorTest() {
        System.out.println(compressor.compress(string));

    }

    public static void shiftBuffer(int shift) {
        for (int i = shift; i < buffer.length; i++) {
            buffer[i - shift] = buffer[i];
        }
    }

    public static void decompressionTest() {
        System.out.println(compressor.decompress("aaab#22#c#43#"));
    }

    public static void graphicsTest() {
        AppScreen appScreen = new AppScreen();
    }

    public static void searchTest(){
        ArrayList<Long> indices=new ArrayList<>();
        System.out.println(window.search(indices,buffer));
        System.out.println(indices.toString());
    }
}
