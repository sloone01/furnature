package com.example.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.market.R;
import com.example.market.pojos.Item;


import java.util.List;


public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    List<Item> list;

    public CustomGridViewAdapter(Context context, List<Item> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.row_grid_main, null);
            TextView textViewAndroid = gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid =  gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(list.get(i).getTitle());
            imageViewAndroid.setImageBitmap(list.get(i).getImage());
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}