package com.qiniu.qdownload.core.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class FileOutput {
    private static final String TAG = "FileOutput";
    private static final int BUFFER_SIZE = 1024;

    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private File file;
    private InputStream inputStream;
    private FileChannel fileChannel;

    public FileOutput(File file, InputStream inputStream) {
        System.out.println("hk ----- file : " + file.getAbsolutePath());

        this.file = file;
        this.inputStream = inputStream;
        try {
            this.fos = new FileOutputStream(file);
            this.bos = new BufferedOutputStream(fos);
            this.fileChannel = fos.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void seek(long position) {
        System.out.println("hk ----- seek : " + position);

        try {
            fileChannel.position(position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        int len = 0;
        byte[] bytes = new byte[BUFFER_SIZE];
        long total = 0;
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                total += len;
            }
            System.out.println("hk -------- total : " + total);
            bos.flush();
            bos.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void write() {
//        int len = 0;
//        byte[] bytes = new byte[BUFFER_SIZE];
//        long total = 0;
//        try {
//            while ((len = inputStream.read(bytes)) != -1) {
//                bos.write(bytes, 0, len);
//                total += len;
//            }
//            System.out.println("hk -------- total : " + total);
//            bos.flush();
//            bos.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
