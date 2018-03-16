package com.shileiyu.baseapp.common.mvp;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BaseModel implements IBaseModel {
    private int taskId;

    public BaseModel(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void onDestroy() {

    }
}
