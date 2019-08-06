package com.reven.uitl.ftp;

import java.io.IOException;

import com.reven.core.ServiceException;

/**
 * @ClassName:  FileClientFactory   
 * @Description: FTP客户端工厂类
 */
public class FileClientFactory {

    private static final String PROTOCOL_FTP = "ftp";
    private static final String PROTOCOL_SFTP = "sftp";

    private FileClientFactory() {
    }

    public static IFtpClient getClient(ServerConfig config) throws ServiceException {
        if (config == null) {
            throw new ServiceException("配置信息为空,请检查配置yml文件");
        }
        IFtpClient client = null;
        if (PROTOCOL_FTP.equals(config.getProtocol())) {
            try {
                client = FtpClientCommon.getInstance(config);
            } catch (IOException e) {
                throw new ServiceException("创建ftp客户端失败：" + e.getMessage(), e);
            }
        } else if (PROTOCOL_SFTP.equals(config.getProtocol())) {
            try {
                client = new SftpClientCommon(config);
            } catch (Exception e) {
                throw new ServiceException("创建sftp客户端失败：" + e.getMessage(), e);
            }
        } else {
            throw new ServiceException("协议配置错误,不为ftp或sftp,当前配置为" + config.getProtocol());
        }
        return client;
    }
}