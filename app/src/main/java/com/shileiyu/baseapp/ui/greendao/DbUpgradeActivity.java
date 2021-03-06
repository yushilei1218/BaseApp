package com.shileiyu.baseapp.ui.greendao;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.bean.BeanADao;
import com.shileiyu.baseapp.common.bean.BeanD;
import com.shileiyu.baseapp.common.bean.BeanDDao;
import com.shileiyu.baseapp.common.bean.BeanE;
import com.shileiyu.baseapp.common.bean.BeanEDao;
import com.shileiyu.baseapp.common.bean.DaoSession;
import com.shileiyu.baseapp.common.db.DbClient;
import com.shileiyu.baseapp.common.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DbUpgradeActivity extends BaseActivity {


    @BindView(R.id.act_db_upgrade_tv)
    TextView mTv;
    private DaoSession mDaoSession;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_db_upgrade;
    }

    @Override
    protected void initView() {
        mDaoSession = DbClient.instance().getDaoSession();
    }


    @OnClick({R.id.bean_a_class_del,
            R.id.bean_a_class_add,
            R.id.bean_a_class_query,
            R.id.bean_b_class_del,
            R.id.bean_b_class_add,
            R.id.bean_e_class_checker,
            R.id.bean_b_class_query})
    public void onViewClicked(View view) {
        BeanADao aDao = mDaoSession.getBeanADao();
        BeanDDao dDao = mDaoSession.getBeanDDao();

        switch (view.getId()) {
            case R.id.bean_a_class_del:
                aDao.deleteAll();
                break;
            case R.id.bean_a_class_add:
                long count = aDao.count() + 1;
                BeanA a = new BeanA("item " + count, System.currentTimeMillis());
                a.setDbUpgrade2("upgrade " + count);
                aDao.insert(a);
                break;
            case R.id.bean_a_class_query:
                List<BeanA> beanAs = aDao.loadAll();
                mTv.setText(Util.toJson(beanAs));
                break;
            case R.id.bean_b_class_del:
                dDao.deleteAll();
                break;
            case R.id.bean_b_class_add:
                long count1 = dDao.count() + 1;
                dDao.insert(new BeanD("haha+" + count1));
                break;
            case R.id.bean_b_class_query:
                List<BeanD> beanCs = dDao.loadAll();
                mTv.setText(Util.toJson(beanCs));
                break;
            case R.id.bean_e_class_checker:
                final BeanEDao beanEDao = mDaoSession.getBeanEDao();
                beanEDao.insert(new BeanE("item e"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<BeanE> beanEs = beanEDao.loadAll();
                        mTv.setText(Util.toJson(beanEs));
                    }
                }, 500);
                break;
            default:
                break;

        }
    }
}
