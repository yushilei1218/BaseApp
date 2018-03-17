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
    @Index(name = "id")
    private long id;

    @Index(name = "name")
    private String name;

    @Transient
    private long time;

    @Generated(hash = 1661877803)
    public BeanA(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1374877907)
    public BeanA() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
