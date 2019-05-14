package com.reven.uitl.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.reven.uitl.DateUtil;

/**
 * 
 * SFTP 上传下载实现类
 * 项目名称:一级渠道运营分析平台
 * 包名称:  com.aspire.cmop.iodd.ftp
 * 类名称:  SftpClientImpl
 * 创建人:  guojinggan
 * 创建时间:2018年7月28日 下午1:57:12
 * 版本：    V1.0.0
 */
public class SftpClientCommon extends AbstractFtpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpClientCommon.class);

    private String separator = File.separator;

    private ChannelSftp sftp;

    private boolean isReady = false;

    private ServerConfig config;

    /**
     * 当前工作目录，每次关闭连接要回复到null，因为当前类是单例类
     */
    private String directory = "";

    private Session sshSession;

    /**
     * 
     * Creates a new instance of SftpClientImpl.
     * @param config
     * @throws Exception
     */
    public SftpClientCommon(ServerConfig config) throws Exception {
        this.config = config;
        // 设置当前工作目录
        directory = StringUtils.isEmpty(config.getRootPath()) ? "" : config.getRootPath();
        LOGGER.info("正在初始化连接信息......");
        initConnection();
        this.setRootPath(directory);
        LOGGER.info("完成初始化连接信息");
    }

    /**
     * 
     * 初始化FTP
     * @throws JSchException 
     */
    private void initConnection() throws JSchException   {
        try {
            if (!isReady) {
                JSch jsch = new JSch();
                sshSession = jsch.getSession(config.getUsername(), config.getHost(), config.getPort());
                sshSession.setPassword(config.getPassword());
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                sshSession.setConfig(sshConfig);
                sshSession.setConfig("userauth.gssapi-with-mic", "no");
                isReady = true;
            }
            if (sshSession != null && !sshSession.isConnected()) {
                sshSession.connect();
                Channel channel = sshSession.openChannel("sftp");
                channel.connect();
                LOGGER.info("已经建立连接渠道...");
                sftp = (ChannelSftp) channel;
            }

        } catch (JSchException e) {
            this.close();
            LOGGER.error("sftp连接服务器出错,host:" + config.getHost(), e);
            throw e;
        }
    }

    public void mkdir(String dirName) throws SftpException {
        SftpATTRS attrs = null;
        attrs = sftp.stat(dirName);
        if (attrs == null) {
            sftp.mkdir(dirName);
        }
        sftp.cd(dirName);
    }

    @Override
    public void mkdirs(String directory) throws SftpException {
        String directoryTmp = directory;
        if (directoryTmp.contains(separator)) {
            if (!directoryTmp.endsWith(separator)) {
                directoryTmp = directoryTmp + separator;
            }
            String dir = directoryTmp.substring(0, directoryTmp.lastIndexOf(separator));
            String[] dirs = dir.split(separator);
            for (String realDir : dirs) {
                if (StringUtils.isNotBlank(realDir)) {
                    mkdir(realDir);
                }
            }
        } else {
            mkdir(directoryTmp);
        }
    }

    @Override
    public boolean changeWorkingDir(String path) throws SftpException {

        this.setRootPath(directory);
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(path);
            isDirExistFlag = true;
            isDirExistFlag = sftpATTRS.isDir();
        } catch (Exception e) {
            if ("no such file".equals(e.getMessage().toLowerCase())) {
                isDirExistFlag = false;
            }
        }

        if (isDirExistFlag) {
            sftp.cd(path);
        }

        return isDirExistFlag;
    }

    @Override
    public void setRootPath(String rootPath) throws SftpException {
        if (StringUtils.isNotEmpty(rootPath)) {
            sftp.cd(rootPath);
        }
    }

    @Override
    public void download(String remotePath, String localPath) throws SftpException {

        LOGGER.info("download.remotePath   = {}", remotePath);
        LOGGER.info("download.localPath    = {}", localPath);

        boolean isWorkingDir = this.changeWorkingDir(remotePath);
        if (isWorkingDir) {
            this.setRootPath(directory);
            @SuppressWarnings("unchecked")
            Vector<LsEntry> sftpFiles = sftp.ls(remotePath);
            sftp.cd(remotePath);
            if (sftpFiles != null && sftpFiles.size() > 0) {
                File locaFilePath = new File(localPath);
                if (!locaFilePath.exists()) {
                    boolean isCreate = locaFilePath.mkdirs();
                    LOGGER.info("locaFilePath Create = {}", isCreate);
                } else {
                    LOGGER.info("locaFilePath exists = {}", true);
                }

                OutputStream outputStream = null;
                File locaFile = null;
                for (LsEntry ftpFile : sftpFiles) {
                    String fileName = ftpFile.getFilename();
                    // TODO 为什么会读到目录呢 ？，坑 ？
                    if (ftpFile.getAttrs().isDir()) {
                        LOGGER.error("下载文件：{} 是目录直接跳过", fileName);
                        continue;
                    }
                    try {
                        locaFile = new File(localPath + fileName);
                        Boolean ftpFileisDel = false;

                        try {
                            outputStream = new FileOutputStream(locaFile);
                            sftp.get(fileName, outputStream);
                            if (locaFile.exists()) {
                                sftp.rm(fileName);
                            }
                            ftpFileisDel = true;
                            // TODO 怎样知道远程已经删除成功
                        } catch (Exception e) {
                            LOGGER.error("下载文件：{}时出现错误", fileName, e);
                            continue;
                        } finally {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Exception e) {
                                    LOGGER.error("下载文件：{}时关闭outputStream 失败", fileName, e);
                                }
                            }
                        }

                        if (!ftpFileisDel && locaFile.isFile()) {
                            locaFile.delete();
                            continue;
                        }
                    } catch (Exception e) {
                        LOGGER.error("下载文件：{} 失败", fileName, e);
                        continue;
                    }
                }
            }
        } else {
            LOGGER.error("远程目录:{}不存在", remotePath);
        }
    }

    @Override
    public boolean upload(String localPath, String remotePath, String localBakPath) throws SftpException {

        LOGGER.info("upload.localPath    = {}", localPath);
        LOGGER.info("upload.remotePath   = {}", remotePath);
        LOGGER.info("upload.localBakPath = {}", localBakPath);

        boolean isWorkingDir = this.changeWorkingDir(remotePath);
        if (!isWorkingDir) {
            this.mkdirs(remotePath);
            isWorkingDir = this.changeWorkingDir(remotePath);
            if (!isWorkingDir) {
                LOGGER.error("upload.远程目录{}不存在", remotePath);
                return false;
            } else {
                LOGGER.error("upload.远程目录{}不存在创建成功", remotePath);
            }
        }

        if (StringUtils.isNotEmpty(localBakPath)) {
            File localBakFilePath = new File(localBakPath);
            if (!localBakFilePath.exists()) {
                boolean isCreate = localBakFilePath.mkdirs();
                LOGGER.info("upload.localBakFilePath Create = {}", isCreate);
            } else {
                LOGGER.info("upload.localBakFilePath exists = {}", true);
            }
        }

        File locaFilePath = new File(localPath);
        if (locaFilePath.exists()) {
            File[] files = locaFilePath.listFiles();
            boolean rst = false;
            if (files != null && files.length > 0) {
                InputStream inputStream = null;
                for (File file : files) {
                    String fileName = file.getName();
                    try {
                        inputStream = new FileInputStream(file);
                        sftp.put(inputStream, file.getName());
                        rst = true;
                    } catch (Exception e) {
                        LOGGER.error("上传文件：{}时出现错误", fileName, e);
                        continue;
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e) {
                                LOGGER.error("上传文件：{}时关闭inputStream 失败", fileName, e);
                            }
                        }
                    }

                    if (rst && StringUtils.isNotEmpty(localBakPath)) {
                        try {
                            FileUtils.moveFile(file, new File(localBakPath + fileName));
                        } catch (Exception e) {
                            LOGGER.error("FileUtils.moveFile:{}失败", fileName);
                            try {
                                FileUtils.moveFile(file, new File(localBakPath + fileName + "_"
                                        + DateUtil.dateToString(new Date(), "yyyymmsshhMMss")));
                            } catch (Exception ee) {
                                boolean isdelete = file.delete();
                                LOGGER.error("FileUtils.moveFile:{}失败 ,file.delete():{}", fileName, isdelete, e);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeFile(String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
        return true;
    }

    @Override
    public boolean rename(String from, String to) throws SftpException {
        sftp.rename(from, to);
        return true;
    }

    @SuppressWarnings("unused")
    private long getFileSize(String srcSftpFilePath) throws SftpException {
        long filesize = 0;// 文件大于等于0则存在
        try {
            SftpATTRS sftpATTRS = sftp.lstat(srcSftpFilePath);
            filesize = sftpATTRS.getSize();
        } catch (Exception e) {
            filesize = -1;// 获取文件大小异常
            if ("no such file".equalsIgnoreCase(e.getMessage())) {
                filesize = -2;// 文件不存在
            }
        }
        return filesize;
    }

    @Override
    public String[] listFiles() throws JSchException, SftpException  {
        initConnection();
        Vector<?> files = sftp.ls(directory);
        String[] fileNameArr = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            LsEntry entery = (LsEntry) files.get(i);
            fileNameArr[i] = entery.getFilename();
        }
        return fileNameArr;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    @Override
    public void close() {
        try {
            if (sftp != null && sftp.isConnected()) {
                sftp.disconnect();
            }
            if (sshSession != null && sshSession.isConnected()) {
                sshSession.disconnect();
            }
            isReady = false;
            LOGGER.info("JSCH session close");
        } catch (Exception e) {
            LOGGER.error("sshSession close", e);
        }
    }

    @Override
    public void download(String remotePath, String localPath, String remoteFileName) throws IOException {
        // TODO Auto-generated method stub
        
    }

}