package com.sun.baselibrary.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.classic.common.MultipleStatusView;
import com.sun.baselibrary.R;
import com.sun.baselibrary.view.loadingview.LoadingDialog;


/**
 * 是没有title的Fragment
 */
public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {

    // 布局view
    protected SV bindingView;
    // fragment是否显示了
    protected boolean mIsVisible = false;
    // 内容布局
    protected RelativeLayout mContainer;
    private MultipleStatusView mMultipleStatusView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null);
        bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        mContainer = (RelativeLayout) view.findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        return view;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {
    }

    protected void onVisible() {
        loadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMultipleStatusView = getView(R.id.multipleStatusView);

    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    /**
     * 布局
     */
    public abstract int setContent();

    protected MultipleStatusView getmMultipleStatusView(){
        return mMultipleStatusView;
    }

    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        mMultipleStatusView.showLoading();
    }

    /**
     * 加载完成状态
     */
    protected void showContent() {
        mMultipleStatusView.showContent();
    }

    /**
     * 加载失败状态
     */
    protected void showError() {
        mMultipleStatusView.showError();
    }

    /**
     *  网络错误状态
     */
    protected void showNoNetwork() {
        mMultipleStatusView.showNoNetwork();
    }

    /**
     *  网络错误状态
     */
    protected void showEmpty() {
        mMultipleStatusView.showEmpty();
    }

    private LoadingDialog progress;
    protected void dismissProgres() {
        if (progress == null) {
            return;
        }
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    protected void showProgress() {
        if (progress == null) {
            progress = LoadingDialog.createProgressDialog(getActivity());
        }
        if (!progress.isShowing()) {
            progress.show();
        }
    }
}
