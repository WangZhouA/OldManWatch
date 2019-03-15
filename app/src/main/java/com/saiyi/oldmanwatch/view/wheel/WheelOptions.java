package com.saiyi.oldmanwatch.view.wheel;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.view.wheel.adapter.ArrayWheelAdapter;
import com.saiyi.oldmanwatch.view.wheel.adapter.ArrayWheelAdapter2;
import com.saiyi.oldmanwatch.view.wheel.adapter.ArrayWheelAdapter3;


public class WheelOptions<T> {
    private static final String TAG = "WheelOptions";
    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;
    private ArrayList<T> mOptions1Items;
    private ArrayList<T> mOptions2Items;
    private ArrayList<T> mOptions3Items;

    private boolean linkage = false;
    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;
    private String parentid;
    private Context mContext;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelOptions(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void setPicker(ArrayList<T> optionsItems) {
        setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<T> options2Items, boolean linkage) {
        setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<T> options2Items,
                          ArrayList<T> options3Items,
                          boolean linkage) {
        this.linkage = linkage;
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        int len = ArrayWheelAdapter.DEFAULT_LENGTH;
        if (this.mOptions3Items == null)
            len = 8;
        if (this.mOptions2Items == null)
            len = 12;
        // 选项1
        wv_option1 = (WheelView) view.findViewById(R.id.options1);
        wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items, len));// 设置显示数据
        wv_option1.setCurrentItem(0);// 初始化时显示的数据
        // 选项2
        wv_option2 = (WheelView) view.findViewById(R.id.options2);
        if (mOptions2Items != null && mOptions2Items.size() != 0)
            wv_option2.setAdapter(new ArrayWheelAdapter2(mOptions2Items));// 设置显示数据
        wv_option2.setCurrentItem(wv_option1.getCurrentItem());// 初始化时显示的数据
        // 选项3
        wv_option3 = (WheelView) view.findViewById(R.id.options3);
        if (mOptions3Items != null && mOptions3Items.size() != 0)
            wv_option3.setAdapter(new ArrayWheelAdapter3(mOptions3Items));// 设置显示数据
        wv_option3.setCurrentItem(wv_option3.getCurrentItem());// 初始化时显示的数据

        int textSize = 18;

        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
        wv_option3.setTextSize(textSize);

        if (this.mOptions2Items == null || this.mOptions2Items.size() == 0)
            wv_option2.setVisibility(View.GONE);
        if (this.mOptions3Items == null || this.mOptions3Items.size() == 0)
            wv_option3.setVisibility(View.GONE);

        // 联动监听器
        wheelListener_option1 = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
            }
        };
        wheelListener_option2 = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
            }
        };

//		// 添加联动监听
        if (options2Items != null && linkage)
            wv_option1.setOnItemSelectedListener(wheelListener_option1);
        if (options3Items != null && linkage)
            wv_option2.setOnItemSelectedListener(wheelListener_option2);
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null)
            wv_option1.setLabel(label1);
        if (label2 != null)
            wv_option2.setLabel(label2);
        if (label3 != null)
            wv_option3.setLabel(label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3 是否循环
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wv_option1.setCyclic(cyclic1);
        wv_option2.setCyclic(cyclic2);
        wv_option3.setCyclic(cyclic3);
    }

    /**
     * 设置第二级是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setOption2Cyclic(boolean cyclic) {
        wv_option2.setCyclic(cyclic);
    }

    /**
     * 设置第三级是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setOption3Cyclic(boolean cyclic) {
        wv_option3.setCyclic(cyclic);
    }

    public void setItemsVisible(int itemsVisible) {
        wv_option1.setItemsVisible(itemsVisible);
        wv_option2.setItemsVisible(itemsVisible);
        wv_option3.setItemsVisible(itemsVisible);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
     *
     * @return 索引数组
     */
    public String[] getCurrentItems() {
        String[] currentItems = new String[3];
        currentItems[0] = (String) mOptions1Items.get(wv_option1.getCurrentItem());
        if (mOptions2Items != null)
            currentItems[1] = (String) mOptions2Items.get(wv_option2.getCurrentItem());
        if (mOptions3Items != null)
            currentItems[2] = (String) mOptions3Items.get(wv_option3.getCurrentItem());
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        }
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
        wv_option3.setCurrentItem(option3);
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (mOptions2Items != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter2(mOptions2Items));
            wv_option2.setCurrentItem(opt2Select);
        }
        if (mOptions3Items != null) {
            wv_option3.setAdapter(new ArrayWheelAdapter3(mOptions3Items));
            wv_option3.setCurrentItem(opt3Select);
        }
    }

}
