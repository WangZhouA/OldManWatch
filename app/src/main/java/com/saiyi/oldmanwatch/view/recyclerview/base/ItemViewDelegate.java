package com.saiyi.oldmanwatch.view.recyclerview.base;

/**
 * @Author liwenbo
 * @Date 18/9/14
 * @Describe
 */
public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
