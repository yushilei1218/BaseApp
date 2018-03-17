package com.shileiyu.baseapp.common.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */
@Entity(nameInDb = "BeanA")
public class BeanA {
    @Id(autoincrement = true)
    private Long id;

    @Index(name = "name")
    private String name;

    @Transient
    private long time;

    @Generated(hash = 992908234)
    public BeanA(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1374877907)
    public BeanA() {
    }

    public BeanA(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
