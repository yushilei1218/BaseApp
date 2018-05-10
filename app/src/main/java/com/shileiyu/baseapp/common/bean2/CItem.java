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

    @Override
    public String name() {
        String b;
        if (name.equals("不限")) {
            b = name;
        } else {
            b = "C";
        }
        String pre;
        if (parent instanceof AItem) {
            pre = "A=" + parent.id;
        } else {
            pre = "B=" + parent.id;
        }
        return b+" " + id + ": " + pre;
    }
}
