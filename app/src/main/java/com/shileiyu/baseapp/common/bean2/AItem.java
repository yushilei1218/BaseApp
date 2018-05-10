package com.shileiyu.baseapp.common.bean2;

/**
 * @author shilei.yu
 * @since on 2018/5/10.
 */
public class AItem extends Compose {
    public AItem() {
    }

    public AItem(int id, int parentId, String name) {
        super(id, parentId, name);
    }

    public AItem(int id, String name) {
        super(id, name);
    }
}
