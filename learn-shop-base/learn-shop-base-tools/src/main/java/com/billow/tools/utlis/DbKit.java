package com.billow.tools.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbKit
{
    private static Logger log = LoggerFactory.getLogger(DbKit.class);

    /**
     * 执行数据备份
     *
     * @param hostIP        mysql ip 地址
     * @param userName      mysql 用户名
     * @param password      mysql 密码
     * @param databaseName  mysql 数据库名
     * @param savePath      保存的路径
     * @param fileName      文件名
     * @param mysqldumpPath mysql dump 工具的路径（如：C:/Program Files/MySQL/MySQL Server 5.6/bin/mysqldump）
     * @return String 保存sql后的路径
     * @author 千面
     * @date 2021/12/13 8:50
     */
    public static String dataBakExec(String hostIP, String userName, String password, String databaseName,
                                     String mysqldumpPath, String savePath, String fileName)
    {
        File saveFile = new File(savePath);
        // 如果目录不存在
        if (!saveFile.exists())
        {
            // 创建文件夹
            saveFile.mkdirs();
        }
        if (!savePath.endsWith(File.separator))
        {
            savePath = savePath + File.separator;
        }
        // sql 文件保存的路径
        String sqlFilePath = savePath + fileName;

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try
        {
            //导出指定数据库指定表的结构和数据
            Process process =
                    Runtime.getRuntime().exec(mysqldumpPath +
                            " -h" + hostIP +
                            " -u" + userName +
                            " -p" + password +
                            " --default-character-set=UTF8" +
                            " --lock-all-tables " + databaseName);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf8"));

            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(sqlFilePath), "utf8"));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                printWriter.println(line);
            }
            printWriter.flush();
            //0 表示线程正常终止。
            if (process.waitFor() == 0)
            {
                log.info("备份数据成功");
            }
        }
        catch (Exception e)
        {
            log.error("导出异常：", e);
        }
        finally
        {
            try
            {
                if (bufferedReader != null)
                {
                    bufferedReader.close();
                }
                if (printWriter != null)
                {
                    printWriter.close();
                }
            }
            catch (IOException e)
            {
                log.error("关闭流异常：", e);
            }
        }
        log.info("导出文件的路径：{}", sqlFilePath);
        return sqlFilePath;
    }

    public static void main(String[] args)
    {
        String hostIP = "127.0.0.1";
        String userName = "root";
        String password = "root";
        String databaseName = "ry-vue";
        String savePath = "D:/bak";
        String fileName = databaseName + "-bak" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".sql";
        String mysqldumpPath = "D:\\install\\mysql-8.0.27-winx64\\bin\\mysqldump";
        dataBakExec(hostIP, userName, password, databaseName, mysqldumpPath, savePath, fileName);
    }
}
