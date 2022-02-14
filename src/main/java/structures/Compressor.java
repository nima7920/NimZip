package structures;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Compressor {
    private File inputFile, outputFile;
    private int v, b, B, matchLimit;
    private byte[] buffer;
    private Window window;
    private String sign = "#";
    private FileReader reader;
    private FileWriter fileWriter;
    private Reader fileReader;

    public Compressor(int v, int b) {
        this.v = v;
        this.b = b;
        B = (int) Math.pow(2, b);
        window = new ListWindow(v);
        buffer = new byte[B];
        matchLimit = (int) Math.ceil((v + b) / 8.0) + 1;
    }

    public Compressor(File inputFile, File outputFile, int v, int b, int datatype) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.v = v;
        this.b = b;
        B = (int) Math.pow(2, b);
        if (datatype == 0)
            window = new HashWindow(v);
        else
            window = new ListWindow(v);
        buffer = new byte[B];
        matchLimit = (int) Math.ceil((v + b) / 8.0) + 1;
        initFiles();
    }

    private void initFiles() {
        try {
            reader = new FileReader(inputFile);
            fileWriter = new FileWriter(outputFile);
            fileReader = new BufferedReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compress() throws IOException {
        long i = 0;
        int j = 0;
        int size = 0;
        byte currentByte;
        ArrayList<Long> indices = new ArrayList<>();
        int data = fileReader.read();
        while (data != -1) {
            currentByte = (byte) data;
            if (i <= matchLimit) {
                window.add(currentByte);
                fileWriter.write((char) currentByte);
                i++;
                data = fileReader.read();
                continue;
            }
            if (j < B) {
                buffer[j] = currentByte;
                j++;
            } else {
                size = window.search(indices, buffer);
                if (size > matchLimit) {
                    fileWriter.write(sign +
                            mergeIndexSize(indices.get(indices.size() - 1) - window.getHeadIndex(), size) + sign + "");

                    for (int k = 0; k < size; k++) {
                        window.add(buffer[k]);
                    }
                    shiftBuffer(size);
                    j = j - size;
                    buffer[j] = currentByte;
                    j++;
                    indices.clear();
                } else {

                    fileWriter.write((char) buffer[0]);
                    window.add(buffer[0]);
                    shiftBuffer(1);
                    buffer[j - 1] = currentByte;
                }

            }
            i++;
            data = fileReader.read();
            fileWriter.flush();
        }
        size = window.search(indices, Arrays.copyOf(buffer, j));
        while (size > matchLimit) {
            fileWriter.write(sign +
                    mergeIndexSize(indices.get(indices.size() - 1) - window.getHeadIndex(), size) + sign + "");
            for (int k = 0; k < size; k++) {
                window.add(buffer[k]);
            }
            shiftBuffer(size);
            j = j - size;
            indices.clear();
            size = window.search(indices, Arrays.copyOf(buffer, j));
        }
        for (int k = 0; k < j; k++) {
            fileWriter.write((char) buffer[k]);
        }
        fileWriter.flush();
    }

    public String compress(String s) {

        int i = 0, j = 0;
        int size = 0;
        byte currentByte;
        ArrayList<Long> indices = new ArrayList<>();
        String result = "";
        while (i < s.length()) {
            currentByte = (byte) s.charAt(i);
            if (i <= matchLimit) {
                window.add(currentByte);
                result = result + (char) currentByte;
                i++;
                continue;
            }
            if (j < B) {
                buffer[j] = currentByte;
                j++;
            } else {
                size = window.search(indices, buffer);
                if (size > matchLimit) {

                    result = result + sign;
                    result = result + mergeIndexSize(indices.get(indices.size() - 1) - window.getHeadIndex(), size);
                    result = result + sign;
                    for (int k = 0; k < size; k++) {
                        window.add(buffer[k]);
                    }
                    shiftBuffer(size);
                    j = j - size;
                    buffer[j] = currentByte;
                    j++;
                    indices.clear();
                } else {

                    result = result + (char) buffer[0];
                    window.add(buffer[0]);
                    shiftBuffer(1);
                    buffer[j - 1] = currentByte;
                }

            }
            i++;
        }
        size = window.search(indices, Arrays.copyOf(buffer, j));
        System.out.println("size: " + size + "; j : " + j);
        System.out.println(Arrays.toString(buffer));
        while (size > matchLimit) {
            result = result + sign;
            result = result + mergeIndexSize(indices.get(indices.size() - 1) - window.getHeadIndex(), size);
            result = result + sign;
            for (int k = 0; k < size; k++) {
                window.add(buffer[k]);
            }
            shiftBuffer(size);
            j = j - size;
            indices.clear();
            size = window.search(indices, Arrays.copyOf(buffer, j));
        }
        for (int k = 0; k < j; k++) {
            result = result + (char) buffer[k];
        }
        return result;
    }

    public void shiftBuffer(int shift) {
        for (int i = shift; i < buffer.length; i++) {
            buffer[i - shift] = buffer[i];
        }

    }

    private long mergeIndexSize(long index, int size) {
        return index * (2 * B) + size;
    }

    public void decompress() throws IOException {
        int i = 0;
        char currentChar;
        String result = "";
        int data = fileReader.read();
        while (data != -1) {

            currentChar = (char) data;
            if (currentChar == sign.charAt(0)) {
                String number = "";
                i++;
                data = fileReader.read();
                currentChar = (char) data;
                while (currentChar != sign.charAt(0)) {
                    number = number + currentChar;
                    i++;
                    data = fileReader.read();
                    currentChar = (char) data;
                }
                int size = extractSize(number), index = extractIndex(number);
                byte[] buffer = window.getByteBuffer(size, index);
                for (int j = 0; j < buffer.length; j++) {
                    fileWriter.write((char) buffer[j]);
                    window.add(buffer[j]);
                }
            } else {
                window.add((byte) currentChar);
                fileWriter.write(currentChar);
            }
            data = fileReader.read();
            i++;
            fileWriter.flush();
        }


    }

    public String decompress(String s) {
        int i = 0;
        char currentChar;
        String result = "";
        while (i < s.length()) {

            currentChar = s.charAt(i);
            if (currentChar == sign.charAt(0)) {
                String number = "";
                i++;
                currentChar = s.charAt(i);
                while (currentChar != sign.charAt(0)) {
                    number = number + currentChar;
                    i++;
                    currentChar = s.charAt(i);
                }
                int size = extractSize(number), index = extractIndex(number);
                byte[] buffer = window.getByteBuffer(size, index);
                for (int j = 0; j < buffer.length; j++) {
                    result = result + ((char) buffer[j]);
                    window.add(buffer[j]);
                }
            } else {
                window.add((byte) currentChar);
                result = result + currentChar;
            }
            i++;

        }

        return result;
    }


    public int extractIndex(String s) {
        return (int) Long.parseLong(s) / (2 * B);
    }

    public int extractSize(String s) {
        return (int) (Long.parseLong(s) % (2 * B));
    }

}
