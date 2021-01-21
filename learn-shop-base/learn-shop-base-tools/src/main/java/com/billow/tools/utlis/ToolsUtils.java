package com.billow.tools.utlis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 数据操作工具类
 *
 * @author liuyongtao
 * @date 2016年12月2日 下午2:45:41
 */
public class ToolsUtils {

    /**
     * 从一个List中获取指定属性的值
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param list  数据集
     * @param field 指定字段
     * @return 指定字段的结果
     * @date 2016年12月2日 下午2:45:37
     */
    public static <T, E> List<T> getListByFieldValue(List<E> list, String field) {
        List<T> fields = new ArrayList<>();
        for (E e : list) {
            Class<? extends Object> clazz = e.getClass();
            try {
                Field f = clazz.getDeclaredField(field);
                // 设置些属性是可以访问的
                f.setAccessible(true);
                T object = (T) f.get(e);
                fields.add(object);
            } catch (SecurityException | NoSuchFieldException | IllegalArgumentException |
                    IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return fields;
    }

    /**
     * 文件压缩
     *
     * @param dir
     * @param zipfile
     * @return
     * @throws IOException
     */
    public static void zipDirectory(String dir, String zipfile) throws IOException {
        // 建立压缩文件输入流
        File dFile = new File(dir);
        if (!dFile.isDirectory()) {
            throw new IllegalArgumentException("Exception: not a directory!");
        }
        String[] entries = dFile.list();
        // 设置缓冲区大小
        byte[] buffer = new byte[4096];
        int bytes_read;
        // 创建一个流来压缩数据并存入zipfile文件中
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
        // 遍历目录所有项
        int entriesLength = entries.length;
        for (int i = 0; i < entriesLength; i++) {
            File f = new File(dFile, entries[i]);
            if (f.isDirectory()) {
                System.out.println("this file is a directory!");
                continue;
            }
            // 只打包excel文件
            if (f.getName().indexOf(".zip") != -1) {
                continue;
            }
            FileInputStream in = new FileInputStream(f);
            ZipEntry entry = new ZipEntry(entries[i]);
            out.putNextEntry(entry);
            while ((bytes_read = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes_read);
            }
            in.close();
        }
        out.close();
        // 删除生成的excel文件
        File[] child = dFile.listFiles();
        if (child != null && child.length != 0) {
            for (int j = 0; j < child.length; j++) {
                if (child[j].getName().indexOf(".zip") != -1) {
                    continue;
                }
                child[j].delete();
            }
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() < 1) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty(Set<T> set) {
        if (set == null || set.size() < 1) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String[] items) {
        if (items == null || items.length < 1) {
            return true;
        }
        return false;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        if (map == null || map.size() < 1) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isNotEmpty(Set<T> set) {
        return !isEmpty(set);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(String[] items) {
        return !isEmpty(items);
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 判断所有的参数是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotEmpty(Field[] objects) {
        if (objects == null || objects.length < 1) {
            return false;
        }
        return true;
    }

    /**
     * 判断所有的参数是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotEmpty(Object... objects) {
        if (objects != null) {
            for (Object o : objects) {
                if (o == null || "".equals(o)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 传入原图名称，获得一个以时间格式的新名称
     *
     * @param fileName 　原图名称
     * @return
     */
    public static String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }
}
