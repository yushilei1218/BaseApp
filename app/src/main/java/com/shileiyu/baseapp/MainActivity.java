package com.shileiyu.baseapp;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.bean.DaoSession;
import com.shileiyu.baseapp.common.db.DbClient;
import com.shileiyu.baseapp.common.db.normal.ResultTask;
import com.shileiyu.baseapp.common.db.normal.SimpleTask;
import com.shileiyu.baseapp.common.db.rx.DbCallable;
import com.shileiyu.baseapp.common.db.rx.DbListCallable;
import com.shileiyu.baseapp.common.util.Util;
import com.shileiyu.baseapp.ui.waterfall.WaterfallActivity;

import org.greenrobot.greendao.rx.RxDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import rx.Subscriber;

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
        RxDao<BeanA, Long> rx = DbClient.instance().getDaoSession().getBeanADao().rx();
        rx.loadAll().subscribe(new Subscriber<List<BeanA>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<BeanA> beanAs) {
                threadInfo("RxDao onNext ");
                mMainTv.setText(Util.toJson(beanAs));
            }
        });
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
        startActivity(new Intent(this, WaterfallActivity.class));

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


    @OnClick({R.id.main_btn, R.id.main_delete, R.id.main_query, R.id.main_rx_add, R.id.main_rx_delete, R.id.main_rx_query})
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
            case R.id.main_rx_add:
                rxAdd();
                break;
            case R.id.main_rx_delete:
                rxDelete();
                break;
            case R.id.main_rx_query:
                rxQuery();
                break;
            default:
                break;
        }
    }

    private void rxQuery() {

        DbClient client = DbClient.instance();

        client.flowable(new DbCallable<BeanA>() {
            @Override
            protected BeanA get(DaoSession dao) {
                return dao.getBeanADao().load(1L);
            }
        }).subscribe(new Consumer<BeanA>() {
            @Override
            public void accept(BeanA beanA) throws Exception {
                threadInfo("rxQuery accept");
                mMainTv.append(Util.toJson(beanA));
            }
        });
        client.listFlowable(new DbListCallable<BeanA>() {

            @Override
            protected List<BeanA> get(DaoSession dao) {
                return dao.getBeanADao().loadAll();
            }
        }).subscribe(new Consumer<List<BeanA>>() {
            @Override
            public void accept(List<BeanA> beanAs) throws Exception {
                threadInfo("rxQuery accept");
                mMainTv.append(Util.toJson(beanAs));
            }
        });
    }

    private void rxDelete() {
        DbClient.instance().runTask(new SimpleTask() {
            @Override
            protected void call(DaoSession dao) {
                dao.deleteAll(BeanA.class);
            }
        });
    }

    private void rxAdd() {
        DbClient.instance().runTask(new SimpleTask() {
            @Override
            protected void call(DaoSession dao) {
                dao.getBeanADao().save(new BeanA("Rx add", 1212));
            }
        });
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

    private void threadInfo(String tag) {
        String name = Thread.currentThread().getName();
        Log.d(getTAG(), tag + " :thread=" + name);
    }
}
