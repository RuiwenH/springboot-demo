package com.reven.uitl.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reven.uitl.DateUtil;

/**
 * 
 * FTP 上传下载实现类
 */
public class FtpClientCommon extends AbstractFtpClient{

	private static final Logger LOGGER = LoggerFactory.getLogger(FtpClientCommon.class);
	private FTPClient client;
	private String separator = "/";
	private String directory = "";

	/**
	 * 
	 * Creates a new instance of FtpClient.
	 * @param config
	 * @throws Exception
	 */
	private FtpClientCommon(ServerConfig config) throws Exception {
		if (client == null) {
			int reply = 0;
			try {
				client = new FTPClient();
				client.setDataTimeout(30 * 60 * 1000);
				client.connect(config.getHost(), config.getPort());
				client.login(config.getUsername(), config.getPassword());
				reply = client.getReplyCode();
				
                if (!FTPReply.isPositiveCompletion(reply)) {
                	this.close();   
                    LOGGER.error("FTP 服务拒绝连接！");
                    throw new IOException("Can't connect to server '" + config.getHost() + "'");
                } 
				
				client.enterLocalPassiveMode();
				client.setBufferSize(1024);
				client.setControlEncoding(config.getCharset());
				directory = StringUtils.isEmpty(config.getRootPath()) ? "" : config.getRootPath();
				// 设置文件类型（二进制）
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				this.setRootPath(directory);
			} catch (Exception e) {
				LOGGER.error("FTP 服务连接出错", e);
				throw e;
			}
		}
	}

	/**
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public static FtpClientCommon getInstance(ServerConfig config) throws Exception {
		FtpClientCommon client = new FtpClientCommon(config);
		return client;
	}



	private void mkdir(String dirName) throws IOException {
		client.makeDirectory(dirName);
		client.changeWorkingDirectory(dirName);
	}

	@Override
	public void mkdirs(String directory) throws IOException {
		String directoryTmp = directory;
		if (directoryTmp.contains(separator)) {
			if (!directoryTmp.endsWith(separator)) {
				directoryTmp = directoryTmp + separator;
			}
			String dir = directoryTmp.substring(0, directoryTmp.lastIndexOf(separator));
			String[] dirs = dir.split(separator);
			for (String realDir : dirs) {
				mkdir(realDir);
			}
		} else {
			mkdir(directoryTmp);
		}
	}
	
	/**
	 * @throws IOException 
	 * @see com.aspire.cmop.iodd.ftp.utils.IFtpClient#changeWorkingDir(java.lang.String)
	 */
	@Override
	public boolean changeWorkingDir(String path) throws IOException   {
		this.setRootPath(directory);
		boolean is = client.changeWorkingDirectory(path);
		LOGGER.info("changeWorkingDir {}成功,当前目录{}", path, client.printWorkingDirectory());
		return is;
	}

	@Override
	public void setRootPath(String rootPath) throws IOException {
		if (StringUtils.isNotEmpty(rootPath)) {
			boolean is = client.changeWorkingDirectory(rootPath);
			LOGGER.info(client.printWorkingDirectory() + "  changeWorkingDirectory rootPath:" + is);
		} else {
			boolean is = client.changeWorkingDirectory(client.printWorkingDirectory());
			LOGGER.info(client.printWorkingDirectory() + "  changeWorkingDirectory rootPath:" + is);
		}
	}

	@Override
	public void download(String remotePath, String localPath) throws IOException {
		
		LOGGER.info("download.remotePath   = {}", remotePath);
		LOGGER.info("download.localPath    = {}", localPath);

		boolean isWorkingDir = this.changeWorkingDir(remotePath);
		if (isWorkingDir) {
			client.enterLocalPassiveMode();
			LOGGER.info("读取远程目录开始:{}", remotePath);
			FTPFile[] ftpFiles = client.listFiles();
			LOGGER.info("读取远程目录结束");

			if (ftpFiles != null && ftpFiles.length > 0) {
				File locaFilePath = new File(localPath);
				if (!locaFilePath.exists()) {
					boolean isCreate = locaFilePath.mkdirs();
					LOGGER.info("locaFilePath Create = {}", isCreate);
				} else {
					LOGGER.info("locaFilePath exists = {}", true);
				}

				OutputStream outputStream = null;
				File locaFile = null;
				for (FTPFile ftpFile : ftpFiles) {
					
					String fileName = ftpFile.getName();
					
					if (ftpFile.isDirectory()) {
						LOGGER.error("目录文件不予处理：{}", fileName );
						continue;
					}
					try {

						locaFile = new File(localPath + fileName);

						Boolean ftpFileisDel = true;

						try {
							outputStream = new FileOutputStream(locaFile);
							boolean isretrieveFile = client.retrieveFile(ftpFile.getName(), outputStream);
							if (isretrieveFile) {
								ftpFileisDel = this.removeFile(ftpFile.getName());
							}
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
	public void download(String remotePath, String localPath,String remoteFileName) throws IOException {
		
		LOGGER.info("download.remotePath   = {}", remotePath);
		LOGGER.info("download.localPath    = {}", localPath);

		boolean isWorkingDir = this.changeWorkingDir(remotePath);
		
		if (isWorkingDir) {
			
			client.enterLocalPassiveMode();
			LOGGER.info("读取远程目录开始:{}", remotePath);
			FTPFile[] ftpFiles = client.listFiles();
			LOGGER.info("读取远程目录结束");

			if (ftpFiles != null && ftpFiles.length > 0) {
				File locaFilePath = new File(localPath);
				if (!locaFilePath.exists()) {
					boolean isCreate = locaFilePath.mkdirs();
					LOGGER.info("locaFilePath Create = {}", isCreate);
				} else {
					LOGGER.info("locaFilePath exists = {}", true);
				}

				OutputStream outputStream = null;
				File locaFile = null;
				int tag = 0 ;
				for (FTPFile ftpFile : ftpFiles) {
					
					String fileName = ftpFile.getName();
					
					if (ftpFile.isDirectory()) {
						LOGGER.error("目录文件不予处理：{}", fileName );
						continue;
					}
					
					if(!fileName.equals(remoteFileName)){
						continue;
					}else{
						tag = 1;
						LOGGER.info("文件 {} 已经找到开始下载",remoteFileName);
					}
					
					try {
						locaFile = new File(localPath + fileName);

						Boolean ftpFileisDel = true;

						try {
							outputStream = new FileOutputStream(locaFile);
							boolean isretrieveFile = client.retrieveFile(ftpFile.getName(), outputStream);
							if (isretrieveFile) {
								ftpFileisDel = this.removeFile(ftpFile.getName());
							}
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
				if(tag == 0){
					LOGGER.error("下载文件：{} 失败,文件未找到", remoteFileName);
				}
			}
		} else {
			LOGGER.error("远程目录:{}不存在", remotePath);
		}
	}	

	@Override
	public boolean upload(String localPath, String remotePath, String localBakPath) throws IOException {

		LOGGER.info("upload.remotePath   = {}", remotePath);
		LOGGER.info("upload.localPath    = {}", localPath);
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
				
				client.enterLocalPassiveMode();
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				
				InputStream inputStream = null;
				for (File file : files) {
					if(file.isDirectory()) {
						continue;
					}
					String fileName = file.getName();
					try {
						inputStream = new FileInputStream(file);
						rst = client.storeFile(file.getName(), inputStream);
						LOGGER.error("上传文件：{}成功", fileName);
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
							LOGGER.error("FileUtils.moveFile:{}失败,失败原因:{}", fileName, e.getMessage());
							try {
								FileUtils.moveFile(file,
										new File(localBakPath + fileName + "_" + DateUtil.dateToString(new Date(), "yyyymmsshhMMss")));
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
	public boolean removeFile(String deleteFile) throws IOException  {
		return client.deleteFile(deleteFile);
	}

	@Override
	public String[] listFiles() throws IOException {
		FTPFile[] ftpFiles = client.listFiles();
		String[] fileNames = new String[ftpFiles.length];
		for (int i = 0; i < ftpFiles.length; i++) {
			fileNames[i] = ftpFiles[i].getName();
		}
		return fileNames;
	}

	@Override
	public void close() {
		try {
			client.disconnect();
		} catch (IOException e) {
			LOGGER.error("", e);
		}
	}

	@Override
	public boolean rename(String from, String to) throws IOException  {
		return client.rename(from, to);
	}

}