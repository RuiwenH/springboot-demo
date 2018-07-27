package com.reven.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_demo")
public class Demo implements Serializable {
    /**
     * @Fields id 自增主键id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @Fields name  名称
     */
    @Column(name = "name")
    private String name;

    /**
     * @Fields date 数据日期
     */
    @Column(name = "date")
    private Date date;

    /**
     * @Fields timestamp 更新记录时刷新当前时间戳记时
     */
    @Column(name = "timestamp")
    private Date timestamp;

    /**
     * @Fields key 测试关键字
     */
    @Column(name = "`KEY`")
    private String key;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", date=").append(date);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", key=").append(key);
        sb.append("]");
        return sb.toString();
    }
}