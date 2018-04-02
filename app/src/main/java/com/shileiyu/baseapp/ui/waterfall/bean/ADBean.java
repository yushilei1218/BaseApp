package com.shileiyu.baseapp.ui.waterfall.bean;

/**
 * @author shilei.yu
 * @since on 2018/3/30.
 */

public class ADBean {
    public String tag;

    public String content;

    public ADBean(String content) {
        this.content = content;
    }

    public ADBean(String tag, String content) {
        this.tag = tag;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ADBean{" +
                "tag='" + tag + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
