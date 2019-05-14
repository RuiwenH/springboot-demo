package com.reven.uitl.ftp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ServerConfig {

    /**   
     * @Fields nodeType : ftp节点名称（系统会配置多个ftp账号）   
     */
    private String nodeType;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String rootPath;
    private String uploadPath;
    private String downloadPath;
    private String protocol;
    private String localUploadPath;
    private String localBakUploadPath;
    private String localDownloadPath;
    private String localBakDownloadPath;
    private String charset;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getLocalUploadPath() {
        return localUploadPath;
    }

    public void setLocalUploadPath(String localUploadPath) {
        this.localUploadPath = localUploadPath;
    }

    public String getLocalBakUploadPath() {
        return localBakUploadPath;
    }

    public void setLocalBakUploadPath(String localBakUploadPath) {
        this.localBakUploadPath = localBakUploadPath;
    }

    public String getLocalDownloadPath() {
        return localDownloadPath;
    }

    public void setLocalDownloadPath(String localDownloadPath) {
        this.localDownloadPath = localDownloadPath;
    }

    public String getLocalBakDownloadPath() {
        return localBakDownloadPath;
    }

    public void setLocalBakDownloadPath(String localBakDownloadPath) {
        this.localBakDownloadPath = localBakDownloadPath;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}