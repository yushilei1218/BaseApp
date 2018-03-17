package com.shileiyu.baseapp;


import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.db.DbClient;
import com.shileiyu.baseapp.common.db.ListResultTask;
import com.shileiyu.baseapp.common.db.ResultTask;
import com.shileiyu.baseapp.common.db.SimpleTask;
import com.shileiyu.baseapp.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author shilei
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.main_btn)
    Button mMainBtn;
    @BindView(R.id.main_tv)
    TextView mMainTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {


    }

    @OnClick(R.id.main_btn)
    public void onViewClicked() {
        final List<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new BeanA(i, "item+" + i));
        }
        DbClient.instance().runTask(new SimpleTask() {
            @Override
            public void run() {
                super.run();
                dao.getBeanADao().insertInTx(data);
            }
        });
        Runnable r = new Runnable() {
            @Override
            public void run() {
                DbClient.instance().runTask(new ListResultTask<BeanA>() {
                    @Override
                    protected void onResult(List<BeanA> data) {

                    }

                    @Override
                    protected List<BeanA> call() {
                        return dao.getBeanADao().loadAll();
                    }
                });
            }
        };
        new Handler().postDelayed(r, 10000);
        new Handler().postDelayed(r, 3000);
        new Handler().postDelayed(r, 6000);
        new Handler().postDelayed(r, 16000);

        for (int i = 0; i < 5; i++) {
            DbClient.instance().runTask(new ResultTask<List<BeanA>>() {
                @Override
                protected void onResult(List<BeanA> data) {
                    if (!Util.isEmpty(data)) {
                        String s = mMainTv.getText() + "\n\n" + Util.toJson(data);
                        mMainTv.setText(s);

                    }
                }

                @Override
                protected List<BeanA> call() {
                    return dao.getBeanADao().loadAll();
                }
            });
        }
    }
}
