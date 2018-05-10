package com.shileiyu.baseapp.ui.filter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.bean2.AItem;
import com.shileiyu.baseapp.common.bean2.BItem;
import com.shileiyu.baseapp.common.bean2.CItem;
import com.shileiyu.baseapp.common.bean2.Compose;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFilterActivity extends BaseActivity {

    @BindView(R.id.filter_lv_first)
    ListView mALv;
    @BindView(R.id.filter_lv_second)
    ListView mBLv;
    @BindView(R.id.filter_lv_third)
    ListView mCLv;
    @BindView(R.id.filter_layout)
    LinearLayout mLayout;
    private ItemAdapter AAdapter;
    private ItemAdapter BAdapter;
    private ItemAdapter CAdapter;
    AItem aCur;
    List<BItem> bCurItem = new ArrayList<>();
    List<CItem> cCurItem = new ArrayList<>();

    private View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag != null && tag instanceof ItemAdapter.VH) {
                Compose c = ((ItemAdapter.VH) tag).c;
                List<Compose> childrenList = c.parent.childrenList;
                for (Compose sub : childrenList) {
                    sub.isSelect = sub.equals(c);
                }
                if (c instanceof AItem) {
                    AAdapter.notifyDataSetChanged();
                } else if (c instanceof BItem) {
                    BAdapter.notifyDataSetChanged();
                } else {
                    CAdapter.notifyDataSetChanged();
                }

                boolean isA = c instanceof AItem;
                if (isA){
                    Compose child = c.childrenList.get(0);
                    if (child instanceof BItem){
                        mBLv.setVisibility(View.VISIBLE);
                    }else {
                        mBLv.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_filter;
    }

    @Override
    protected void initView() {
        AAdapter = new ItemAdapter();
        BAdapter = new ItemAdapter();
        CAdapter = new ItemAdapter();
        mALv.setAdapter(AAdapter);
        mBLv.setAdapter(BAdapter);
        mCLv.setAdapter(CAdapter);
        Compose total = Compose.getTotal();

        AAdapter.update(total.childrenList);

    }

    private void init(Compose total) {
        boolean b = total.hasChildren();
        if (!b) {
            return;
        }
        List<Compose> childrenList = total.childrenList;
        for (int i = 0; i < childrenList.size(); i++) {
            Compose compose = childrenList.get(i);
            init(compose);
            if (compose.isSelect) {
                if (compose instanceof AItem) {
                    aCur = (AItem) compose;
                } else if (compose instanceof BItem) {
                    bCurItem.add((BItem) compose);
                } else {
                    cCurItem.add((CItem) compose);
                }
            }
        }
    }

    class ItemAdapter extends BaseAdapter {
        List<Compose> data;

        public void update(List<Compose> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override

        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                convertView.setTag(new VH(convertView));
            }
            Compose compose = data.get(position);
            VH holder = (VH) convertView.getTag();
            convertView.setBackgroundColor(compose.isSelect ? Color.WHITE : Color.LTGRAY);
            holder.tag.setVisibility(compose.isFocus ? View.VISIBLE : View.GONE);
            holder.tv.setText(compose.name());
            holder.c = compose;


            if (compose.isSelect) {

                boolean b = compose.hasChildren();
                if (b) {
                    Compose sub = compose.childrenList.get(0);
                    boolean isB = sub instanceof BItem;
                    if (isB) {
                        boolean equals = isEquals(compose.childrenList, BAdapter.data);
                        if (!equals) {
                            BAdapter.update(compose.childrenList);
                        }
                    } else {//CItem
                        boolean equals = isEquals(compose.childrenList, CAdapter.data);
                        if (!equals) {
                            CAdapter.update(compose.childrenList);
                        }
                    }
                }
            }
            convertView.setOnClickListener(itemListener);
            return convertView;
        }

        class VH {
            Compose c;
            TextView tv;
            View tag;

            VH(View view) {
                tv = view.findViewById(R.id.item_tv);
                tag = view.findViewById(R.id.item_tg);
            }
        }

    }

    public boolean isEquals(List a, List b) {
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null && a == b) {
            return true;
        }
        return false;
    }

}
