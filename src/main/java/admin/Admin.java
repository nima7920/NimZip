package admin;

import structures.Compressor;

import java.io.File;
import java.io.IOException;

public class Admin {

    private Compressor compressor;

    public Admin() {

    }

    public boolean compress(File inputFile, File outputFile, int v, int b, int dataType) {
        compressor = new Compressor(inputFile, outputFile, v, b,dataType);
        try {
            compressor.compress();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean decompress(File inputFile, File outputFile, int v, int b, int dataType) {

        compressor = new Compressor(inputFile, outputFile, v, b,dataType);
        try {
            compressor.decompress();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
