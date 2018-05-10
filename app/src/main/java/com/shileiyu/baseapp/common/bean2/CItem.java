package com.shileiyu.baseapp.common.bean2;

/**
 * @author shilei.yu
 * @since on 2018/5/10.
 */
public class CItem extends Compose {
    public CItem() {
    }

    public CItem(int id, int parentId, String name) {
        super(id, parentId, name);
    }

    public CItem(int id, String name) {
        super(id, name);
    }
}
