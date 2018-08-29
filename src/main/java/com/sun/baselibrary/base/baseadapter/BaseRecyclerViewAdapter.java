package com.sun.baselibrary.base.baseadapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRecyclerViewAdapter
 */
public abstract class BaseRecyclerViewAdapter<T,D extends ViewDataBinding> extends RecyclerView.Adapter {

    protected OnItemClickListener<T> listener;
    protected Context context;
    protected List<T> items;

    public BaseRecyclerViewAdapter(Context context)
    {
        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount()
    {
        return this.items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        D binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), this.getLayoutResId(viewType), parent, false);
        return new BaseRecyclerViewHolder(binding.getRoot()){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        D binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, this.items.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    protected abstract @LayoutRes int getLayoutResId(int viewType);

    protected abstract void onBindItem(D binding, T item);

}
