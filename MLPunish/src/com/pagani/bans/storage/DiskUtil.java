package com.pagani.bans.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DiskUtil {

    /**
     * Description: Overlays the readFile and converts the bytes to a string.
     *
     * @param file File we want to convert to string.
     * @return The entire to string.
     * @throws IOException
     */
    public static String read(File file) throws IOException {
        if (readFile(file) == null) return "";
        return new String(readFile(file), StandardCharsets.UTF_8);
    }

    /**
     * Description: Overlays the writeFile.
     *
     * @param file File we want to convert to string.
     * @param text Text we want to write.
     */
    public static void write(String file, String text) throws IOException {
        writeFile(new File(file), text.getBytes(StandardCharsets.UTF_8), false);
    }

    /**
     * Description: Overlays the writeFile.
     *
     * @param file File we want to convert to string.
     * @param text Text we want to write.
     */
    public static void write(File file, String text) throws IOException {
        writeFile(file, text.getBytes(StandardCharsets.UTF_8), false);
    }

    /**
     * Description: Writes data to a file using the FileOutPutStream.
     *
     * @param file          The file we want to to write out data to.
     * @param bytes         What is being added into the file.
     * @param addLineToFile I can't recall what it's called when you add somthing to the end of the file people correct
     * @throws IOException
     */
    private static void writeFile(File file, byte[] bytes, boolean addLineToFile) {
        try {
            if (!file.exists())
                file.createNewFile();


            FileOutputStream fileOutputStream = new FileOutputStream(file, addLineToFile);

            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: Reads a file using the FileInputStream then loop through the file
     *
     * @param file File we want to read.
     * @return The returned bytes of the file.
     * @throws IOException
     * @throws FileNotFoundException
     */

    private static byte[] readFile(File file) throws IOException, FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (file.length() == 0) return null;

        byte[] buffer = new byte[(int) file.length()];
        int read;
        while ((read = fileInputStream.read(buffer)) != -1)
            byteArrayOutputStream.write(buffer, 0, read);


        byteArrayOutputStream.close();
        fileInputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

}