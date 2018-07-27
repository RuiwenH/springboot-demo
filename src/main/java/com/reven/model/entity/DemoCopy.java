package com.reven.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created By MBG-GUI-EXTENSION https://github.com/spawpaw/mybatis-generator-gui-extension
 * Description:
 * 
 *
 * @author 
 */
@Table(name="`t_demo copy`")
public class DemoCopy implements Serializable {
    /**
     *   自增主键id
     *
     * Corresponding to the database column t_demo copy.id
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    private Integer id;

    /**
     *    名称
     *
     * Corresponding to the database column t_demo copy.name
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    private String name;

    /**
     *   数据日期
     *
     * Corresponding to the database column t_demo copy.date
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    private Date date;

    /**
     *   更新记录时刷新当前时间戳记时
     *
     * Corresponding to the database column t_demo copy.timestamp
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    private Date timestamp;

    /**
     *   测试关键字
     *
     * Corresponding to the database column t_demo copy.KEY
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    private String key;

    /**
     * Corresponding to the database table t_demo copy
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column t_demo copy.id
     *
     * @return the value of t_demo copy.id
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public DemoCopy withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column t_demo copy.id
     *
     * @param id the value for t_demo copy.id
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column t_demo copy.name
     *
     * @return the value of t_demo copy.name
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public DemoCopy withName(String name) {
        this.setName(name);
        return this;
    }

    /**
     * This method sets the value of the database column t_demo copy.name
     *
     * @param name the value for t_demo copy.name
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method returns the value of the database column t_demo copy.date
     *
     * @return the value of t_demo copy.date
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public DemoCopy withDate(Date date) {
        this.setDate(date);
        return this;
    }

    /**
     * This method sets the value of the database column t_demo copy.date
     *
     * @param date the value for t_demo copy.date
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method returns the value of the database column t_demo copy.timestamp
     *
     * @return the value of t_demo copy.timestamp
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public DemoCopy withTimestamp(Date timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    /**
     * This method sets the value of the database column t_demo copy.timestamp
     *
     * @param timestamp the value for t_demo copy.timestamp
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * This method returns the value of the database column t_demo copy.KEY
     *
     * @return the value of t_demo copy.KEY
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public DemoCopy withKey(String key) {
        this.setKey(key);
        return this;
    }

    /**
     * This method sets the value of the database column t_demo copy.KEY
     *
     * @param key the value for t_demo copy.KEY
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
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

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DemoCopy other = (DemoCopy) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getTimestamp() == null ? other.getTimestamp() == null : this.getTimestamp().equals(other.getTimestamp()))
            && (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()));
    }

    /**
     *
     * @mbg.generated Fri Jul 27 11:16:41 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getTimestamp() == null) ? 0 : getTimestamp().hashCode());
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        return result;
    }
}