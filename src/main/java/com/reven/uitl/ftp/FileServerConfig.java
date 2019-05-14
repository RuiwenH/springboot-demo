package com.reven.uitl.ftp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@Component
public class FileServerConfig {
    private List<ServerConfig> external;

    /**
     * 根据节点类型获取配置
     * @param nodeType 节点类型
     * @return 服务配置
     */
    public ServerConfig getExternalConfig(String nodeType) {
        if (external != null && external.size() > 0) {
            return external.stream().filter(serverConfig -> serverConfig.getNodeType().equals(nodeType)).findFirst()
                    .get();
        }
        return null;
    }

    public List<ServerConfig> getExternal() {
        return external;
    }

    public void setExternal(List<ServerConfig> external) {
        this.external = external;
    }

}