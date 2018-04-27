package com.shileiyu.baseapp.ui.arouter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.util.Constant;

import butterknife.BindView;


@Route(path = Constant.PATH)
public class RouterTestActivity extends BaseActivity {

    @BindView(R.id.act_router_test_tv)
    TextView mTv;

    @Autowired
    public String name = "test";

    @Autowired(name = "girl")
    public boolean boy = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_router_test;
    }

    @Override
    protected void initView() {
        ARouter.getInstance().inject(this);

        Bundle extras = getIntent().getExtras();
        StringBuilder sb = new StringBuilder();
        if (extras != null) {

            for (String key : extras.keySet()) {
                Object o = extras.get(key);
                sb.append(key).append(" : ").append(o).append("\n");
            }
        }
        sb.append("以下参数来自Uri \n");
        sb.append("boy : ").append(boy).append("\n");
        sb.append("name : ").append(name).append("\n");
        mTv.setText(sb.toString());
    }
}
