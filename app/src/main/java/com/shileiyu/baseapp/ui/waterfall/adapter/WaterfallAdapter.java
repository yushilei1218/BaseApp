package com.shileiyu.baseapp.ui.waterfall.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.VH> {
    private List<WaterfallBean> data;

    public WaterfallAdapter() {
    }

    public void addRoot(List<WaterfallBean> root) {
        data = root;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View child = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_water_fall, parent, false);
        VH tag = new VH(child);
        child.setTag(tag);
        return tag;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.mImg.setImageResource(data.get(position).rid);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static final class VH extends RecyclerView.ViewHolder {

        private final ImageView mImg;

        public VH(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.item_water_img);
        }
    }
}
