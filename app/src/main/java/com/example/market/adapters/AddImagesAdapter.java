package com.example.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.market.R;

import java.util.List;


public class AddImagesAdapter extends BaseAdapter {

    private Context mContext;
    List<Integer> list;
    private final View.OnClickListener listener;

    public AddImagesAdapter(Context context, List<Integer> list, View.OnClickListener listener) {
        mContext = context;
        this.list = list;
        this.listener = listener;
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
            gridViewAndroid = inflater.inflate(R.layout.images_grid, null);
            ImageView imageViewAndroid =  gridViewAndroid.findViewById(R.id.android_gridview_image);
            imageViewAndroid.setImageResource(R.drawable.addimag);
            imageViewAndroid.setOnClickListener(listener);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }

}