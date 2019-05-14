package com.reven.uitl.ftp;

import java.io.File;
import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/**
 * 
 * FTP 工具类接口定义
 */
public interface IFtpClient {

    /**
     * 创建远程目录
     * 可创建多级目录，同时切换到当前创建的目录下
     * @param directory 需要创建的远程目录
     * @return void
     * @throws IOException 
     * @throws SftpException 
     */
    void mkdirs(String directory) throws IOException, SftpException;

    /**
     * 切换远程目录
     * @param path 切换的远程目录
     * @return boolean 目录不存在或无权限时返回false，切换成功放回 true
     * @throws IOException 
     * @throws SftpException 
     */
    boolean changeWorkingDir(String path) throws IOException, SftpException;

    /**
     * 设置FTP 根目录 
     * @param rootPath 根目录
     * @return void
     * @throws IOException 
     * @throws SftpException 
     */
    void setRootPath(String rootPath) throws IOException, SftpException;

    /**
     * 
     * 下载远程目录文件
     * @param remotePath 远程目录（获取文件的目录）
     * @param localPath  本地目录（下载到本地的目录）
     * @return void
     * @throws IOException 
     * @throws SftpException 
     */
    void download(String remotePath, String localPath) throws IOException, SftpException;

    /**
     * 有待测试 
     * 下载远程目录文件 某一具体文件名的文件
     * @param remotePath 远程目录（获取文件的目录）
     * @param localPath  本地目录（下载到本地的目录）
     * @param remoteFileName 文件名
     * @return void
     * @throws IOException 
     */
    void download(String remotePath, String localPath, String remoteFileName) throws IOException;

    /**
     * 
     * 上传文件：将localPath下的文件上传到remotePath下，上传成功后 将localPath下的文件剪切到 localBakPath 进行备份
     * @param localPath   本地目录       （下载到本地的目录）
     * @param remotePath  远程目录       （获取文件的目录）
     * @param localBakPath本地备份目录（下载到本地的目录）
     * @return
     * @throws Exception
     * @return boolean
     * @throws IOException 
     * @throws SftpException 
     */
    boolean upload(String localPath, String remotePath, String localBakPath) throws IOException, SftpException;

    /**
     * 删除远程文件
     * @param deleteFile  要删除的文件名，文件全路径
     * @return
     * @return boolean
     * @throws IOException 
     * @throws SftpException 
     */
    boolean removeFile(String deleteFile) throws IOException, SftpException;

    /**
     * 
     * 获取远程目录下文件名列表
     * @return
     * @return String[]
     * @throws IOException 
     * @throws JSchException 
     * @throws SftpException 
     */
    String[] listFiles() throws IOException, JSchException, SftpException;

    /**
     * 
     * 关闭FTP
     * @return void
     */
    void close();

    /**
     * 远程文件重命名，即远程文件剪切
     * @param from 原文件
     * @param to   目标文件
     * @return
     * @return boolean
     * @throws IOException 
     * @throws SftpException 
     */
    boolean rename(String from, String to) throws IOException, SftpException;

    /**
     * 本地文件拷贝，支持目录拷贝
     * @param src 原文件
     * @param dst 目标文件
     * @return
     * @throws Exception
     * @return boolean
     */
    @Deprecated
    boolean copyFile(File src, File dst);

    /**
     * 
     * 本地文件重命名 
     * @param srcFile   原文件
     * @param destFile  目标文件
     * @return void
     * @throws IOException 
     */
    @Deprecated
    void renameTo(File srcFile, File destFile) throws IOException;

}
