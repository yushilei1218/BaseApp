package com.shileiyu.baseapp.common.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

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

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date date = new Date();

    @Transient
    private long time;

    @Index(name = "dbUpgrade")
    private String dbUpgrade;

    @Index(name = "dbUpgrade2")
    private String dbUpgrade2;


    @Generated(hash = 1374877907)
    public BeanA() {
    }

    public BeanA(String name, long time) {
        this.name = name;
        this.time = time;
    }

    @Generated(hash = 1194402477)
    public BeanA(Long id, String name, Date date, String dbUpgrade,
            String dbUpgrade2) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.dbUpgrade = dbUpgrade;
        this.dbUpgrade2 = dbUpgrade2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getDbUpgrade() {
        return this.dbUpgrade;
    }

    public void setDbUpgrade(String dbUpgrade) {
        this.dbUpgrade = dbUpgrade;
    }

    public String getDbUpgrade2() {
        return this.dbUpgrade2;
    }

    public void setDbUpgrade2(String dbUpgrade2) {
        this.dbUpgrade2 = dbUpgrade2;
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", dbUpgrade='" + dbUpgrade + '\'' +
                ", dbUpgrade2='" + dbUpgrade2 + '\'' +
                '}';
    }
}
