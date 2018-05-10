package com.shileiyu.baseapp.common.bean2;

import com.shileiyu.baseapp.common.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/5/10.
 */
public class Compose {
    public int id;
    public int parentId;
    public String name;
    public boolean isSelect;
    public boolean isFocus;
    public boolean isSimple = true;
    public Compose parent;

    public List<Compose> childrenList = new ArrayList<>();

    public Compose() {
    }

    public Compose(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Compose(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(Compose child) {
        child.parentId = id;
        child.parent = this;
        childrenList.add(child);
    }

    public void addChidren(List<Compose> children) {
        for (Compose c : children) {
            addChild(c);
        }

    }

    public boolean hasChildren() {
        return !Util.isEmpty(childrenList);
    }

    public String name() {
        return name;
    }

    public static int acount = 10;
    public static int bcount = 15;
    public static int ccount = 20;

    public static void main(String[] args) {
        System.out.print(Util.toJson(getTotal()));
    }

    public static void select(List<Compose> data) {
        if (Util.isEmpty(data)) {
            return;
        }
        data.get(0).isSelect = true;
        for (Compose c : data) {
            select(c.childrenList);
        }

    }

    public static Compose getTotal() {
        int index = 1;
        int bIndex = 100;
        int cIndex = 10000;
        Compose total = new Compose(1, 0, "total");

        List<Compose> data = new ArrayList<>();
        for (int i = 0; i < acount; i++) {
            int id = ++index;
            data.add(new AItem(id, "a item " + id));
        }
        total.addChidren(data);

        for (int i = 0; i < data.size(); i++) {
            Compose compose = data.get(i);
            List<Compose> b = new ArrayList<>();
            if (i % 2 == 0) {
                for (int j = 0; j < bcount; j++) {
                    int id = ++bIndex;
                    b.add(new BItem(id, "b item " + id));
                }
            } else {
                for (int j = 0; j < ccount; j++) {
                    int id = ++cIndex;
                    String name = "c item " + id;
                    if (j == 0) {
                        name = "不限";
                    }
                    b.add(new CItem(id, name));
                }
            }
            compose.addChidren(b);
        }

        for (Compose a : data) {
            List<Compose> childrenList = a.childrenList;
            if (childrenList.get(0) instanceof BItem) {
                for (int k = 0; k < childrenList.size(); k++) {
                    Compose b = childrenList.get(k);
                    for (int i = 0; i < ccount; i++) {
                        List<Compose> c = new ArrayList<>(30);
                        int id = ++cIndex;
                        String name = "c item " + id;
                        if (i == 0) {
                            name = "不限";
                        }
                        CItem cci = new CItem(id, name);
                        if (i % 2 == 1) {
                            cci.isSimple = false;
                        }
                        c.add(cci);
                        b.addChidren(c);
                    }
                }
            }
        }
        select(total.childrenList);
        return total;
    }
}
