package com.shileiyu.baseapp.common.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author shilei.yu
 * @since on 2018/4/8.
 */
@Entity(nameInDb = "BeanE")
public class BeanE {
    @Id(autoincrement = true)
    private Long id;

    @Index(name = "hehe")
    private String hehe;

    @Generated(hash = 490203703)
    public BeanE(Long id, String hehe) {
        this.id = id;
        this.hehe = hehe;
    }

    @Generated(hash = 1993071163)
    public BeanE() {
    }

    public BeanE(String hehe) {
        this.hehe = hehe;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHehe() {
        return this.hehe;
    }

    public void setHehe(String hehe) {
        this.hehe = hehe;
    }
}
