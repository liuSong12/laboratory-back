package com.study.labsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@SpringBootTest
public class RandomRead {
    @Test
    void testSkip() throws Exception {
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("b.txt"));
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("a.txt"));
//        File file = new File("b.txt");
//        System.out.println(file.length());
//        long skip = bufferedInputStream.skip(6);
//        System.out.println("结果："+skip);
//
//        int read = bufferedInputStream.read();
//        System.out.println((char) read);
//        bufferedInputStream.skip(-5);
////        System.out.println("结果："+skip);
//
//
//        int read2 = bufferedInputStream.read();
//        System.out.println((char) read2);

        RandomAccessFile rw = new RandomAccessFile("b.txt", "rw");
        long length = rw.length();
        System.out.println(length);
        rw.seek(5);
        int read = rw.read();
        System.out.println((char) read);
        rw.seek(2);
//        rw.
        int read2 = rw.read();
        System.out.println((char) read2);

    }

    @Test
    void test() {
        File file = new File("D:\\JavaScript\\laboratory\\laboratory-back\\upload\\dir\\0c461506ef6e6ea2d03794b83da60b831b0196ce");
        File[] files = file.listFiles();
//        Arrays.sort(files, Comparator.comparing(File::getName));
        Arrays.sort(files, (o1, o2) -> {
            String s = o1.getName().split("-")[1];
            String s2 = o2.getName().split("-")[1];
            return Integer.parseInt(s) - Integer.parseInt(s2);
        });
        for (File f : files) {
            System.out.println(f.getName());
        }
    }

    @Test
    public void test2() throws Exception {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("b.txt"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("a.txt"));
        byte[] bytes = new byte[4];
        int len;
        while ((len = bufferedInputStream.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len));
            bufferedOutputStream.write(bytes, 0, len);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        bufferedInputStream.close();

    }

    @Test
    public void t() throws Exception {
        //sliceFile(new File("upload\\file\\sum.mp4"), 1024 * 1024 * 20); //文件切分单线程
       // sliceFileWithPool(new File("upload\\file\\sum.mp4"), 1024 * 1024 * 20); //文件切分多线程

        File inputFile = new File("upload\\file");
        File[] files = inputFile.listFiles();
        File targetFile = new File(inputFile, "mergedFile.mp4");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        long l = System.currentTimeMillis();
        mergeFiles(files,targetFile,countDownLatch);
        countDownLatch.await();
        long l1 = System.currentTimeMillis();
        System.out.println("文件合并完成,共耗时："+(l1-l)); //6938ms
    }


    void mergeFiles(File[] files, File targerFile, CountDownLatch countDownLatch) throws FileNotFoundException {
        final int readSize = 1024 * 1024 * 4;
        List<File> list = Arrays.stream(files).filter(file -> !file.getName().equals("sum.mp4")).sorted((o1, o2) -> {
            String num1 = o1.getName().split("\\.")[0];
            String num2 = o2.getName().split("\\.")[0];
            return Integer.parseInt(num1) - Integer.parseInt(num2);
        }).toList();
        BufferedOutputStream w = new BufferedOutputStream(new FileOutputStream(targerFile));
        new Thread(()->{
            for (File file : list) {
                try {
                    BufferedInputStream r = new BufferedInputStream(new FileInputStream(file));
                    byte[] bytes = new byte[readSize];
                    int len;
                    System.out.println("正在合并"+file.getName());
                    while ((len = r.read(bytes) )!=-1){
                        w.write(bytes,0,len);
                    }
                    r.close(); //不关流删除不了
                    boolean delete = file.delete();
                    System.out.println("删除"+file.getName()+(delete?"成功":"失败"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            System.out.println("2s后开始删除文件");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            for (File file : list) {
//                boolean delete = file.delete();
//                System.out.println("删除"+file.getName()+(delete?"成功":"失败"));
//            }
            countDownLatch.countDown();
        }).start();

    }

    void sliceFileWithPool(File fileName, int sliceSize) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                6,
                12,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        final int readSize = 1024 * 1024 * 6;
        int sliceCount = 0;
        if (fileName.length() % sliceSize != 0) {
            sliceCount = (int) (fileName.length() / sliceSize) + 1;
        } else {
            sliceCount = (int) (fileName.length() / sliceSize);
        }
        System.out.println("需要分" + sliceCount + "片");
        CountDownLatch countDownLatch = new CountDownLatch(sliceCount);

        long start = System.currentTimeMillis();
        int i = 0;
        while (i < fileName.length()) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                int startPos = finalI;
                try {
                    int part = 0;
                    if (startPos < fileName.length()) {
                        part = startPos / sliceSize;
                    } else {
                        part = (int) (fileName.length() / sliceSize + 1);
                    }
                    File file = new File(fileName.getParentFile(), part + ".mp4");
                    BufferedOutputStream w = new BufferedOutputStream(new FileOutputStream(file));
                    BufferedInputStream r = new BufferedInputStream(new FileInputStream(fileName));
                    r.skip(startPos);
                    byte[] bytes = new byte[readSize];
                    int len = 0;
                    int sum = 0;
                    System.out.println("正在切分第" + (finalI / sliceSize + 1) + "个文件");
                    while ((len = r.read(bytes)) != -1) {
                        if (sum + len > sliceSize) {
                            w.write(bytes, 0, sliceSize - sum - 1);
                            break;
                        }
                        w.write(bytes, 0, len);
                        sum += len;
                    }
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threadPoolExecutor.submit(thread);
            i += sliceSize;
        }

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("总共耗时：" + (end - start) + "ms"); //用多线程2684ms


    }

    void sliceFile(File fileName, int sliceSize) throws Exception {
        RandomAccessFile r = new RandomAccessFile(fileName, "r");
        byte[] bytes = new byte[1024 * 1024 * 6];
        int index = 0;
        long start = System.currentTimeMillis();
        int i = 0;
        while (i < fileName.length()) {
            File fileItem = new File(fileName.getParentFile(), index + "-" + fileName.getName());
            RandomAccessFile w = new RandomAccessFile(fileItem, "rw");
            r.seek(i);
            int sum = 0;
            int len = 0;
            System.out.println("正在分第" + index + "个文件");
            while ((len = r.read(bytes)) != -1) {
                if (sum + len > sliceSize) {
                    w.write(bytes, 0, sliceSize - sum);
                    break;
                }
                w.write(bytes, 0, len);
                sum += len;
            }
            w.close();
            i += sliceSize;
            index++;
        }
        r.close();
        long end = System.currentTimeMillis();
        System.out.println("总共耗时：" + (end - start) + "ms"); //不用多线程20684ms


    }


    /**
     * 大佬写的
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        final int THREAD_NUM = 4; // 线程数

        //分割
//        File inputFile = new File("upload\\file\\sum.mp4"); // 待拆分文件
//        long fileSize = inputFile.length(); // 文件大小
//        long partSize = fileSize / THREAD_NUM; // 每个线程处理的大小
//        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
//        for (int i = 0; i < THREAD_NUM; i++) {
//            long startPos = i * partSize; // 当前线程处理的起始位置
//            long endPos = (i == THREAD_NUM - 1) ? fileSize - 1 : (i + 1) * partSize - 1; // 当前线程处理的结束位置
//
//            File fileItem = new File(inputFile.getParentFile(), "part-" + i + ".mp4");
//            Thread t = new SplitterThread(inputFile, startPos, endPos, fileItem.getAbsolutePath(),countDownLatch); // 创建线程  dat
//            t.start(); // 启动线程
//        }
//        countDownLatch.await();
//        System.out.println("切割完成");

        //合并
        List<String> partFiles = new ArrayList<>();
        File inputFile = new File("upload\\file\\sum.mp4"); // 待拆分文件
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            File fileItem = new File(inputFile.getParentFile(), "part-" + i + ".mp4");
            partFiles.add(fileItem.getAbsolutePath()); // 待合并的小文件
        }

        File outputFile = new File("upload\\file\\mergedFile.mp4");

        Thread t = new CombinerThread(partFiles, outputFile, countDownLatch); // 创建线程
        t.start(); // 启动线程
        countDownLatch.await();
        System.out.println("合并完成");
    }

    private static class CombinerThread extends Thread {
        final int BUFFER_SIZE = 1024 * 1024 * 10; // 缓冲区大小
        private List<String> partFiles;
        private File outputFile;
        private CountDownLatch countDownLatch;

        public CombinerThread(List<String> partFiles, File outputFile, CountDownLatch countDownLatch) {
            this.partFiles = partFiles;
            this.outputFile = outputFile;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int len;
                for (String partFile : partFiles) {
                    System.out.println("正在合并" + partFile + "片文件");
                    try (FileInputStream fis = new FileInputStream(partFile)) {
                        while ((len = fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                    }
                    countDownLatch.countDown();
                    new File(partFile).delete(); // 删除小文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SplitterThread extends Thread {
        final int BUFFER_SIZE = 1024 * 1024 * 10; // 缓冲区大小
        private File inputFile;
        private long startPos;
        private long endPos;
        private String outputFile;
        private CountDownLatch countDownLatch;

        public SplitterThread(File inputFile, long startPos, long endPos, String outputFile, CountDownLatch countDownLatch) {
            this.inputFile = inputFile;
            this.startPos = startPos;
            this.endPos = endPos;
            this.outputFile = outputFile;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int len;

                fis.skip(startPos); // 跳过起始位置之前的字节
                System.out.println("正在切割" + startPos + "~" + endPos + "片文件");
                while ((len = fis.read(buffer)) != -1 && fis.getChannel().position() <= endPos) {
                    fos.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }


}
