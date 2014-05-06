package com.gamesonsteroids.angelsanddemons.widgets;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ListAdapter<T> extends BaseAdapter {
    private List<T> list;
    private ListViewProvider<T> listViewProvider;

    public interface ListViewProvider<T> {

        View getView(final int position, final T item, View convertView);
    }

    public ListAdapter(List<T> list, ListViewProvider<T> listViewProvider) {
        this.list = list;
        this.listViewProvider = listViewProvider;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return listViewProvider.getView(position, (T)getItem(position), convertView);
    }
}
