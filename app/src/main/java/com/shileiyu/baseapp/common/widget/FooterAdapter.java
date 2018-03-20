package com.shileiyu.baseapp.common.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.util.Util;

import java.util.HashMap;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public class FooterAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final HashMap<Class<?>, ItemDelegate> match = new HashMap<>();
    private final SparseArray<ItemDelegate> typeMatch = new SparseArray<>();

    private List data;

    private final Foot mFoot = new Foot();

    public FooterAdapter() {
        setMatch(Foot.class, new FootDelegate());
    }

    public void addRoot(List data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setMatch(Class<?> c, ItemDelegate d) {
        match.put(c, d);
        typeMatch.put(d.layoutId(), d);
    }

    public void changeFootState(FootState state) {
        if (state == null) {
            return;
        }
        if (mFoot.state == state) {
            return;
        }
        FootState pre = mFoot.state;
        mFoot.state = state;
        switch (state) {
            case HIDE:
                int position = data == null ? 0 : data.size();
                notifyItemRemoved(position);
                break;
            default:
                int pos = data == null ? 0 : data.size();
                if (pre == FootState.HIDE) {
                    notifyItemInserted(pos);
                } else {
                    notifyItemChanged(pos);
                }
                break;
        }
    }

    public FootState getFootState() {
        return mFoot.state;
    }

    public Object get(int position) {
        if (Util.isEmpty(data) || (position >= data.size())) {
            return mFoot;
        }
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Class<?> aClass = get(position).getClass();
        return match.get(aClass).layoutId();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDelegate itemDelegate = typeMatch.get(viewType);
        BaseViewHolder holder = itemDelegate.create(parent, viewType);

        //瀑布流加载更多效果
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null) {
            boolean isFoot = itemDelegate == match.get(Foot.class);
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                if (isFoot) {
                    //foot
                    ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
                } else {
                    ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(false);
                }
            }
        }
        return holder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object o = get(position);
        ItemDelegate delegate = match.get(o.getClass());
        delegate.bindView(position, holder, o);
    }

    @Override
    public int getItemCount() {
        int count = mFoot.state == FootState.HIDE ? 0 : 1;
        count = data == null ? count : count + data.size();
        return count;
    }

}
