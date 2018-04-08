package com.shileiyu.baseapp.common.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author shilei.yu
 * @since on 2018/4/8.
 */
@Entity(nameInDb = "BeanD")
public class BeanD {
    @Id(autoincrement = true)
    private Long id;

    @Index(name = "haha")
    private String haha;

    public BeanD(String haha) {
        this.haha = haha;
    }

    @Generated(hash = 1436530306)
    public BeanD(Long id, String haha) {
        this.id = id;
        this.haha = haha;
    }

    @Generated(hash = 1404304144)
    public BeanD() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHaha() {
        return this.haha;
    }

    public void setHaha(String haha) {
        this.haha = haha;
    }
}
