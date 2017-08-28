package com.chh.yinbao.presenter;

import android.content.Context;

import com.chh.yinbao.view.IView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by potoyang on 2017/8/7.
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected final String TAG = this.getClass().getSimpleName();
    public List objReferenceList = new ArrayList();
    protected Context context;

    private WeakReference<V> viewRef;

    @Override
    public void attachView(IView view) {
        viewRef = new WeakReference<V>((V) view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
        if (objReferenceList.size() > 0) {
            for (Object object : objReferenceList) {
                object = null;
            }
            objReferenceList.clear();
        }
    }

    /**
     * 获取 presenter 对应的view
     *
     * @return 如果对应, 返回对应实例, 否则返回 null
     */
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * 检查 presenter 是否存在对应View
     *
     * @return 如果存在返回true, 否则返回false
     */
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
