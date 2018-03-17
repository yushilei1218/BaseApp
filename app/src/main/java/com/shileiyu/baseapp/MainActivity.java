package com.shileiyu.baseapp;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.bean.DaoSession;
import com.shileiyu.baseapp.common.db.DbClient;
import com.shileiyu.baseapp.common.db.ListResultTask;
import com.shileiyu.baseapp.common.db.ResultTask;
import com.shileiyu.baseapp.common.db.SimpleTask;
import com.shileiyu.baseapp.common.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    private void delete() {
        DbClient.instance().runTask(new SimpleTask() {
            @Override
            protected void call(DaoSession dao) {
                dao.deleteAll(BeanA.class);
            }
        });
    }

    private void add() {
        DbClient instance = DbClient.instance();
        Random random = new Random();
        final List<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new BeanA("item+" + i, random.nextInt(Integer.MAX_VALUE >> 1)));
        }
        instance.runTask(new SimpleTask() {
            @Override
            protected void call(DaoSession dao) {
                dao.insert(new BeanA("text", 11));
            }
        });
        instance.runTask(new SimpleTask() {
            @Override
            protected void call(DaoSession dao) {
                dao.getBeanADao().insertInTx(data);
            }
        });
    }


    @OnClick({R.id.main_btn, R.id.main_delete, R.id.main_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_btn:
                add();
                break;
            case R.id.main_delete:
                delete();
                break;
            case R.id.main_query:
                query();
                break;
            default:
                break;
        }
    }

    private void query() {
        for (int i = 0; i < 5; i++) {
            DbClient.instance().runTask(new ResultTask<List<BeanA>>() {
                @Override
                protected void onResult(List<BeanA> data) {
                    if (!Util.isEmpty(data)) {
                        String s = mMainTv.getText() + "\n\n" + Util.toJson(data);
                        mMainTv.setText(s);

                    } else {
                        mMainTv.setText(null);
                    }
                }

                @Override
                protected List<BeanA> call(DaoSession dao) {
                    return dao.getBeanADao().loadAll();
                }
            });
        }
    }
}
