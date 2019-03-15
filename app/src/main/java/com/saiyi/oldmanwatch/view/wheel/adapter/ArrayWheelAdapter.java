package com.saiyi.oldmanwatch.view.wheel.adapter;


import java.util.ArrayList;

/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {

    /**
     * The default items length
     */
    public static final int DEFAULT_LENGTH = 4;

    // items
    private ArrayList<T> items;
    // length
    private int length;

    /**
     * Constructor
     *
     * @param items  the items
     * @param length the max items length
     */
    public ArrayWheelAdapter(ArrayList<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     *
     * @param items the items
     */
    public ArrayWheelAdapter(ArrayList<T> items) {
        this(items, DEFAULT_LENGTH);
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < items.size(); i++) {
            if (o.equals(items.get(i)))
                return i;
        }
        return items.indexOf(o);
    }

}
