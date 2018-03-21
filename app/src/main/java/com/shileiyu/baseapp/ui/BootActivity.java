package com.shileiyu.baseapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.util.Constant;
import com.shileiyu.baseapp.ui.greendao.GreenDaoActivity;
import com.shileiyu.baseapp.ui.waterfall.WaterfallActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BootActivity extends BaseActivity {


    @BindView(R.id.boot_grid)
    GridView mBootGrid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_boot;
    }

    @Override
    protected void initView() {

        List<Bean> data = new ArrayList<>();
        data.add(new Bean(Constant.GREEN_DAO));
        data.add(new Bean(Constant.WATER_FALL));

        mBootGrid.setAdapter(new Adapter(data));

        mBootGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getAdapter().getItem(position);
                if (item instanceof Bean) {
                    openActivity(((Bean) item));
                }
            }
        });
    }

    private void openActivity(Bean item) {
        Intent intent = null;
        switch (item.name) {
            case Constant.GREEN_DAO:
                intent = new Intent(this, GreenDaoActivity.class);
                break;
            case Constant.WATER_FALL:
                intent = new Intent(this, WaterfallActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }

    }

    private static final class Bean {
        public final String name;

        public Bean(String name) {
            this.name = name;
        }
    }

    private static class Adapter extends BaseAdapter {
        List<Bean> data;

        Adapter(List<Bean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);
                convertView.setTag(new VH(convertView));
            }
            VH tag = (VH) convertView.getTag();
            tag.tv.setText(data.get(position).name);
            return convertView;
        }

        static final class VH {
            TextView tv;

            VH(View view) {
                tv = (TextView) view.findViewById(R.id.item_bean_tv);
            }
        }
    }
}
