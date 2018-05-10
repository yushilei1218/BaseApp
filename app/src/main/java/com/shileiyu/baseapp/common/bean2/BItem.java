package com.shileiyu.baseapp.common.bean2;

/**
 * @author shilei.yu
 * @since on 2018/5/10.
 */
public class BItem extends Compose{
    public BItem() {
    }

    public BItem(int id, int parentId, String name) {
        super(id, parentId, name);
    }

    public BItem(int id, String name) {
        super(id, name);
    }
}
