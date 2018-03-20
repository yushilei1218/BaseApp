package com.shileiyu.baseapp.common.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */
@Entity(nameInDb = "BeanC")
public class BeanC {
    @Id
    public Long id;

    public Long bid;

    public String name;

    public BeanC() {
    }

    public BeanC(String name) {
        this.name = name;
    }

    @Generated(hash = 722371468)
    public BeanC(Long id, Long bid, String name) {
        this.id = id;
        this.bid = bid;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBid() {
        return this.bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
