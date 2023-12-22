package com.study.labsystem.controller;

import com.study.labsystem.common.Result;
import com.study.labsystem.pojo.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/adminapi")
public class UploadController {
    private volatile Integer count = 0;
    @PostMapping("/upload")
    public Result<HashMap<String,String>> upload(@RequestParam("fileName") String fileName,
                                 @RequestParam("fileChunk") MultipartFile fileChunk,
                                 @RequestParam("fileHash") String fileHash,
                                 @RequestParam("chunkIndex") String chunkIndex) throws Exception {

        File file = new File("upload\\dir", fileHash);
        if (!file.exists()) {
            file.mkdir();
        }

        InputStream inputStream = fileChunk.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(file, chunkIndex)));
        byte[] bytes = new byte[1024];
        while ((bufferedInputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes);
        }
        bufferedOutputStream.close();
        bufferedInputStream.close();
        HashMap<String, String> map = new HashMap<>();
        map.put("chunkIndex",chunkIndex);
        map.put("msg","上传成功");
        return Result.success(map);
    }

    @PostMapping("/merge")
    public Result<String> merge(@RequestBody UploadFile uploadFile) throws Exception {
        String fileHash = uploadFile.getFileHash();
        String fileName = uploadFile.getFileName();
        if (fileHash == null) return Result.err("文件hash为空");
        File file = new File("upload\\dir", fileHash);
        File tarFile = new File("upload\\file", fileHash);
        if (!file.exists()) return Result.err("请先上传文件");

        if (tarFile.exists() && file.isFile()) return Result.err("请勿重复合并");

        if (file.exists() && file.isDirectory()) {
            if (file.listFiles() == null) {
                return Result.err("请先上传文件");
            }
        }
        new Thread(()->{
            try {
                mergerFiles(file, tarFile, uploadFile.getFileName()); //我写的方法
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

//        File[] fileNames = file.listFiles();
//        Arrays.sort(fileNames, (o1, o2) -> {
//            String s = o1.getName().split("-")[1];
//            String s2 = o2.getName().split("-")[1];
//            return Integer.parseInt(s) - Integer.parseInt(s2);
//        });
//        log.info("请求合并{}",uploadFile.getFileName());
//        log.info("文件列表：{}",fileNames);
        //mergeFiles(fileNames, tarFile, uploadFile.getFileName()); //gpt写的方法

        return Result.success();
    }

    void mergerFiles(File file, File targetFile, String fileName) throws Exception {
        //太耗时了
        String[] files = file.list();
        List<String> list = Arrays.stream(files).sorted((f, f2) -> {
            String[] split = f.split("-");
            String[] split2 = f2.split("-");
            String index2 = split2[1];
            String index = split[1];
            return Integer.parseInt(index) - Integer.parseInt(index2);
        }).toList();
        log.info(String.valueOf(list));
        String extend = fileName.substring(fileName.lastIndexOf("."));
        File file1 = new File(targetFile.getAbsoluteFile() + extend);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file1));
        for (String fileItem : list) {
            File tempFile = new File(file, fileItem);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(tempFile));
            byte[] bytes = new byte[1024 * 1024 * 5];
            int i = 0;
            while ((i = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, i);
            }
            bufferedInputStream.close();
            boolean delete = tempFile.delete();
            if (!delete){
                System.out.println(tempFile.getName()+ "删除失败");
            }
        }
        bufferedOutputStream.close();
        boolean delete = file.delete();
        if (!delete){
            System.out.println(file.getName()+ "删除失败");
        }
    }


    /**
     * 合并不能开新线程，会导致文件合并失败
     * @param fileNames
     * @param mergedFileName
     * @param fileName
     * @throws InterruptedException
     */
    public void mergeFiles(File[] fileNames, File mergedFileName, String fileName) throws InterruptedException {
        log.info("countdownlatchLenght：{}",fileNames.length);
        CountDownLatch countDownLatch = new CountDownLatch(fileNames.length);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                20,
                40,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            String extend = fileName.substring(fileName.lastIndexOf("."));
            File file = new File(mergedFileName.getAbsoluteFile() + extend);
            BufferedOutputStream mergedWriter = new BufferedOutputStream(new FileOutputStream(file));
            for (File fileN : fileNames) {
                Thread thread = new Thread(() -> {
                    try {
                        mergeFile(fileN, mergedWriter,countDownLatch);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                pool.submit(thread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.await();

            log.info("文件合并完成，开始删除文件片段");
            File parentFile = fileNames[0].getParentFile();
            log.info("父文件：{}",parentFile.getAbsoluteFile());
            for (File file : fileNames) {
                log.info("删除子文件：" + file.getAbsoluteFile());
                file.delete();
            }
            log.info("删除子文件成功");
            parentFile.delete();
            log.info("删除父文件成功");
        }
    }

    /**
     * 合并不能开新线程，会导致文件合并失败
     * @param fileName
     * @param mergedWriter
     * @param countDownLatch
     * @throws InterruptedException
     */
    public void mergeFile(File fileName, BufferedOutputStream mergedWriter, CountDownLatch countDownLatch) throws InterruptedException {
        BufferedInputStream reader = null;
        try {
            synchronized (mergedWriter) {
                String s = fileName.getName().split("-")[1];
                int sort = Integer.parseInt(s);
                while (sort != count){
                    mergedWriter.wait();
                }
                log.info("开始合并文件：" + fileName.getName());
                reader = new BufferedInputStream(new FileInputStream(fileName));
                byte[] bytes = new byte[1024];
                int len;
                while ((len = (reader.read(bytes))) != -1) {
                    // 同步写入到合并文件
                    mergedWriter.write(bytes, 0, len);
                }
                count++;
                countDownLatch.countDown();
                mergedWriter.notifyAll();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception w) {
                    w.printStackTrace();
                }
            }
        }
    }

    @PostMapping("/verify")
    public Result<Object> verifyFileItem(@RequestBody UploadFile uploadFile) {
        if (uploadFile.getFileHash() == null) return Result.err("文件hash为空");
        String completeName = null;
        if (StringUtils.isEmpty(uploadFile.getExtend())) {
            completeName = uploadFile.getFileHash();
        } else {
            completeName = uploadFile.getFileHash() + uploadFile.getExtend();
        }
        File file1 = new File("upload\\file", completeName);

        if (file1.exists()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("fileHash", uploadFile.getFileHash());
            map.put("needUpload", false);
            map.put("alreadyUploadItem", null);
            return Result.success(map);
        }

        File file = new File("upload\\dir", uploadFile.getFileHash());
        if (file.exists()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("fileHash", uploadFile.getFileHash());
            map.put("needUpload", true);
            map.put("alreadyUploadItem", Arrays.asList(file.list()));
            return Result.success(map);
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("fileHash", uploadFile.getFileHash());
            map.put("needUpload", true);
            map.put("alreadyUploadItem", null);
            return Result.success(map);
        }

    }

}

















