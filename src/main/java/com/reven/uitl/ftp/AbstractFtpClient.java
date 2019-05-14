package com.reven.uitl.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFtpClient implements IFtpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFtpClient.class);

    @Override
    public boolean copyFile(File src, File dst) {
        LOGGER.info("开始复制文件 src = {} ,dst= {}", src.getName(), dst.getName());
        boolean result = true;
        try {
            if (!dst.exists()) {
                boolean isMake = dst.mkdir();
                if (!isMake) {
                    LOGGER.error("创建文件夹失败");
                }
            }
            if (!dst.exists()) { // 路径判断，是路径还是单个的文件
                File[] cf = src.listFiles();
                if (null != cf) {
                    for (File fn : cf) {
                        if (fn.isFile()) {
                            result = this.copy(fn, dst);
                        } else {
                            File fb = new File(dst + File.separator + fn.getName());
                            boolean isMake = fb.mkdir();
                            if (!isMake) {
                                LOGGER.error("创建文建夹失败");
                            }
                            if (fn.listFiles() != null) { // 如果有子目录递归复制子目录！
                                copyFile(fn, fb);
                            }
                        }
                    }
                }
            } else {
                result = this.copy(src, dst);
            }
        } catch (Exception e) {
            LOGGER.error("文件复制失败");
            return false;
        }
        return result;
    }

    private boolean copy(File src, File dst) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dst + File.separator + src.getName());
            byte[] b = new byte[1024];
            int i = fis.read(b);
            while (i != -1) {
                fos.write(b, 0, i);
                i = fis.read(b);
            }
        } catch (RuntimeException r) {
            LOGGER.error("文件复制异常", r);
            return false;
        } catch (Exception e) {
            LOGGER.error("文件复制异常", e);
            return false;
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LOGGER.error("SftpClientCommon.copy fis close error,", e);
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception e) {
                    LOGGER.error("SftpClientCommon.copy fos close error,", e);
                }
            }
        }
        return true;
    }

    @Override
    public void renameTo(File srcFile, File destFile) throws IOException {
        FileUtils.moveFile(srcFile, destFile);
    }

}
