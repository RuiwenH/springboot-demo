package com.reven.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "system_update_history")
public class SystemUpdateHistory extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @Fields version 系统版本
     */
    private String version;

    /**
     * @Fields updateNo 更新序号
     */
    @Column(name = "`update_no`")
    private Integer updateNo;

    /**
     * @Fields updateDate 更新时间
     */
    @Column(name = "`update_date`")
    private Date updateDate;

    /**
     * @Fields context 更新内容（富文本）
     */
    private String context;

    /**
     * @Fields createTime 记录创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getUpdateNo() {
        return updateNo;
    }

    public void setUpdateNo(Integer updateNo) {
        this.updateNo = updateNo;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", version=").append(version);
        sb.append(", updateNo=").append(updateNo);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", context=").append(context);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}