package com.shileiyu.baseapp.ui.lagou;

import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/4/2.
 */

public class LaGouModel implements LaGouContract.IModel {
    @Override
    public void loadAd(ICallBack<List<TwoTuple<ADBean, ADBean>>> callBack) {
        List<TwoTuple<ADBean, ADBean>> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new TwoTuple<>(new ADBean("行业", "高德地图上线顺风车 " + i), new ADBean("重磅", "上海也加入抢人大战！13个领域")));
        }
        callBack.call(data);
    }
}
