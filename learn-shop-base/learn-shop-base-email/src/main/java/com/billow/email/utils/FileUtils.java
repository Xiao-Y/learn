package com.billow.email.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * 文件操作工具类
 *
 * @author liuyongtao
 * @create 2020-01-08 14:04
 */
public class FileUtils {

    /**
     * 文件转String。
     *
     * @param file
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2020/1/8 14:29
     */
    public static String fileToString(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fileToString(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件转String。
     *
     * @param fis
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2020/1/8 14:29
     */
    public static String fileToString(InputStream fis) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            String attachmentBytes = Base64.getEncoder().encodeToString(bos.toByteArray());
            return attachmentBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
