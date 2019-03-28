package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class PortModuleInfo implements Serializable {
    /**
     * 
     */
    private Long pid;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Long parent_id;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String create_user;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private String update_user;

    /**
     * 
     */
    private Date update_time;

    /**
     * 
     */
    private String url;

    /**
     * port_moduleinfo
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return pid 
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 
     * @param pid 
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return parent_id 
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 
     * @param parent_id 
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 
     * @return type 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 
     * @return create_user 
     */
    public String getCreate_user() {
        return create_user;
    }

    /**
     * 
     * @param create_user 
     */
    public void setCreate_user(String create_user) {
        this.create_user = create_user == null ? null : create_user.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_user 
     */
    public String getUpdate_user() {
        return update_user;
    }

    /**
     * 
     * @param update_user 
     */
    public void setUpdate_user(String update_user) {
        this.update_user = update_user == null ? null : update_user.trim();
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 
     * @return url 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     *
     * @mbggenerated 2018-01-29
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
        PortModuleInfo other = (PortModuleInfo) that;
        return (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getParent_id() == null ? other.getParent_id() == null : this.getParent_id().equals(other.getParent_id()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCreate_user() == null ? other.getCreate_user() == null : this.getCreate_user().equals(other.getCreate_user()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_user() == null ? other.getUpdate_user() == null : this.getUpdate_user().equals(other.getUpdate_user()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()));
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getParent_id() == null) ? 0 : getParent_id().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCreate_user() == null) ? 0 : getCreate_user().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_user() == null) ? 0 : getUpdate_user().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", parent_id=").append(parent_id);
        sb.append(", type=").append(type);
        sb.append(", create_user=").append(create_user);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_user=").append(update_user);
        sb.append(", update_time=").append(update_time);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}