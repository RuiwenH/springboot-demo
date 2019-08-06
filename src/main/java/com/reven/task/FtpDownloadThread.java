package com.reven.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.reven.uitl.SpringUtil;
import com.reven.uitl.ftp.FileClientFactory;
import com.reven.uitl.ftp.FileServerConfig;
import com.reven.uitl.ftp.IFtpClient;
import com.reven.uitl.ftp.ServerConfig;

/**
 * @author reven
 */
//@Component
public class FtpDownloadThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(FtpDownloadThread.class);
    private String threadName;

    public FtpDownloadThread(String threadName) {
        this.threadName = threadName;
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("启动Ftp下载线程  " + threadName);
        }
        boolean isRunning = true;
        try {
            while (isRunning) {
                try {
                    download();
                    Thread.sleep(300000);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            logger.error("退出Ftp下载线程！" + threadName);
        }
    }

    private void download() throws Exception {
        FileServerConfig fileServerConfig = SpringUtil.getBean(FileServerConfig.class);
        ServerConfig ftpConfig = fileServerConfig.getExternalConfig("CENTER_FTP");

        IFtpClient ftpClient = FileClientFactory.getClient(ftpConfig);
        // 检查远程文件是否存在
        ftpClient.setRootPath(ftpConfig.getRootPath());
        ftpClient.changeWorkingDir(ftpConfig.getDownloadPath());
        String[] listFiles = ftpClient.listFiles();
        // 有文件要下载
        if (listFiles != null && listFiles.length > 0) {
            for (String remoteFileName : listFiles) {
                ftpClient.download(ftpConfig.getDownloadPath(), ftpConfig.getLocalDownloadPath(), remoteFileName);
            }
        }
    }

}
