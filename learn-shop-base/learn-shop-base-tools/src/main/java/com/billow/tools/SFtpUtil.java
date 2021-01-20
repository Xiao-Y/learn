package com.billow.tools;

import com.billow.tools.utlis.ToolsUtils;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 远程登陆SFTP
 *
 * @author liuyongtao
 * @date 2016年9月20日 下午5:38:39
 */
public class SFtpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SFtpUtil.class);

    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return com.jcraft.jsch.ChannelSftp
     * @author LiuYongTao
     * @date 2018/7/16 14:11
     */
    public static ChannelSftp connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        LOGGER.info("\n Session created...");

        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        LOGGER.info("\n Session connected...");

        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        LOGGER.info("\n Opening Channel...");

        ChannelSftp sftp = (ChannelSftp) channel;
        LOGGER.info("\n Connected to " + host + "...");

        return sftp;
    }

    /**
     * 连接命令服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return com.jcraft.jsch.ChannelSftp
     * @author LiuYongTao
     * @date 2018/7/16 14:11
     */
    public static ChannelExec getChannelExec(String host, int port, String username, String password) throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(username, host, port);
        LOGGER.info("\n Session created...");

        session.setPassword(password);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        LOGGER.info("\n Session connected...");

        Channel channel = session.openChannel("exec");
//        channel.connect();
        LOGGER.info("\n Opening Channel...");

        ChannelExec exec = (ChannelExec) channel;
        exec.connect();
        LOGGER.info("\n Connected to " + host + "...");
        return exec;
    }

    /**
     * 断开FTP连接
     *
     * @param channel
     * @return void
     * @author LiuYongTao
     * @date 2018/7/20 17:59
     */
    public static void disconnect(Channel channel) throws Exception {
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        LOGGER.info("\n Session close...........");
    }

    /**
     * 上传文件
     *
     * @param sftp
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     * @return void
     * @author LiuYongTao
     * @date 2018/7/16 14:12
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp) throws Exception {
        LOGGER.info("\n upload uploadFile:{}", uploadFile);
        File file = new File(uploadFile);
        upload(directory, file.getName(), new FileInputStream(file), sftp);
    }

    /**
     * 上传文件
     *
     * @param sftp
     * @param directory   上传的目录
     * @param fileName    要上传的文件名
     * @param inputStream 要上传的文件流
     * @return void
     * @author LiuYongTao
     * @date 2018/7/16 14:12
     */
    public static void upload(String directory, String fileName, InputStream inputStream, ChannelSftp sftp) throws Exception {
        try {
            LOGGER.info("\n upload directory:{}", directory);
            LOGGER.info("\n upload fileName:{}", fileName);
            sftp.cd(directory);
            sftp.put(inputStream, fileName);
            LOGGER.info("\n upload success...........");
        } catch (Exception e) {
            LOGGER.error("\n upload error...........\n", e);
            throw e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp
     * @return void
     * @author LiuYongTao
     * @date 2018/7/16 14:14
     */
    public static void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            LOGGER.info("\n download directory:{}", directory);
            LOGGER.info("\n download fileName:{}", downloadFile);
            sftp.cd(directory);
            File file = new File(saveFile);
            fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile, fileOutputStream);
            LOGGER.info("\n download success...........");
        } catch (Exception e) {
            LOGGER.error("\n download error...........\n", e);
            throw e;
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     * @return void
     * @author LiuYongTao
     * @date 2018/7/16 14:14
     */
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) throws SftpException {
        LOGGER.info("\n delete directory:{}", directory);
        LOGGER.info("\n delete fileName:{}", deleteFile);
        sftp.cd(directory);
        sftp.rm(deleteFile);
        LOGGER.info("\n delete success...........");
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param sftp
     * @return java.util.Vector
     * @author LiuYongTao
     * @date 2018/7/16 14:14
     */
    public static Vector<ChannelSftp.LsEntry> listFiles(String directory, ChannelSftp sftp) throws SftpException {
        Vector<ChannelSftp.LsEntry> listFiles = new Vector<>();
        try {
            listFiles = sftp.ls(directory);
        } catch (Exception e) {
            LOGGER.error("\n file not exist path: {} ....", directory);
            LOGGER.error("\n file path exception:\n", e);
        }
        return listFiles;
    }

    /**
     * 通过文件名称获取文件流
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param filePath 文件路径
     * @param connect  fpt 连接
     * @return
     * @throws SftpException
     * @author LiuYongTao
     * @date 2018/7/16 14:14
     */
    public static InputStream getInputStream(String filePath, ChannelSftp connect) throws SftpException {
        return connect.get(filePath);
    }

    /**
     * 通过文件名称获取文件内容,一行一换行
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param filePath 文件路径
     * @param connect  fpt 连接
     * @return
     * @throws SftpException
     * @author LiuYongTao
     * @date 2018/7/16 14:14
     */
    public static String getString(String filePath, ChannelSftp connect) throws SftpException {
        InputStream inputStream = connect.get(filePath);
        String result = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n "));
        return result;
    }

    /**
     * 通过文件名称获取文件内容,每行数据都放入List中
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param filePath 文件路径
     * @param connect  fpt 连接
     * @return
     * @throws SftpException
     * @author LiuYongTao
     * @date 2018/7/16 14:14
     */
    public static List<String> getContentList(String filePath, ChannelSftp connect) throws SftpException {
        InputStream inputStream = connect.get(filePath);
        List<String> result = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.toList());
        return result;
    }

    /**
     * 查检当前文件或者文件夹是否存在
     *
     * @param path    文件或者文件夹路径
     * @param connect fpt 连接
     * @return boolean
     * @author LiuYongTao
     * @date 2018/7/20 18:02
     */
    public static boolean isExists(String path, ChannelSftp connect) throws Exception {
        if (connect == null || connect.isClosed()) {
            throw new RuntimeException("channel sftp is closeed");
        }
        if (ToolsUtils.isEmpty(path)) {
            return false;
        }
        boolean isExists = true;
        try {
            SftpATTRS attr = connect.stat(path);
            long fileSize = attr.getSize();
            LOGGER.info("file size:{} B", fileSize);
        } catch (SftpException e) {
            LOGGER.error("file path:{} not exists", path);
            isExists = false;
        }
        return isExists;
    }

    /**
     * 执行shell 命令，返回结果，会阻塞
     *
     * @param command
     * @param channelExec
     * @return java.lang.String
     * @author billow
     * @date 2020/5/1 21:06
     */
    public static String executeCommand(String command, ChannelExec channelExec) throws Exception {
        LOGGER.info("执行命令: {} ", command);

        StringBuilder result = new StringBuilder();
        try {
            channelExec.setInputStream(null);
            channelExec.setErrStream(System.err);
            channelExec.setCommand(command);
            InputStream in = channelExec.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            LOGGER.error("\n command error: {} ", command, e);
        }
        return result.toString();
    }
}
