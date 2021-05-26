package com.example.furnature.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import com.example.furnature.R;
import com.example.furnature.pojos.Catagory;

import java.util.List;
import java.util.Objects;

public class CatagoryAdapter extends ArrayAdapter<Catagory> {

        List<Catagory> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public CatagoryAdapter(Context context, int layoutResourceId,
                       List<Catagory> data, int icon1 , int icon2, MYListnerInter listnerInter1, MYListnerInter listnerInter2) {
        super(context, layoutResourceId, data);


        this.catagoryList = data;
        this.layoutResourceId = layoutResourceId;
        this.context  = context;
        this.listnerInter1 = listnerInter1;
        this.listnerInter2 = listnerInter2;
        this.icon1= icon1;
        this.icon2= icon2;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View rowView=inflater.inflate(layoutResourceId, parent,false);
        RatingBar ratingBar;

        TextView textrecycler,descr;
        ImageView delete = rowView.findViewById(R.id.edit),edit =rowView.findViewById(R.id.delete);


        textrecycler = rowView.findViewById(R.id.name);
        descr = rowView.findViewById(R.id.descr);


        rowView.setTag(catagoryList.get(position));
        if (Objects.isNull(edit))
                rowView.setOnClickListener(v->listnerInter1.execute(rowView));
        else
        {
                delete.setOnClickListener(v->listnerInter2.execute(rowView));
                edit.setOnClickListener(v->listnerInter1.execute(rowView));
        }

        textrecycler.setText(catagoryList.get(position).getCatagoryName());
        descr.setText(catagoryList.get(position).getDescription());


        return rowView;

        };




        }
