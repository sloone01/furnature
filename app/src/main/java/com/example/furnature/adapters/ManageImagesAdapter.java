package com.example.furnature.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.furnature.R;
import com.example.furnature.pojos.Image;

import java.util.List;


public class ManageImagesAdapter extends ArrayAdapter<Image> {

        List<Image> servicesList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;
        int icon1,icon2 ;



public ManageImagesAdapter(Context context, int layoutResourceId,
                           List<Image> data, int icon1 , int icon2, MYListnerInter listnerInter1, MYListnerInter listnerInter2) {
        super(context, layoutResourceId, data);


        this.servicesList = data;
        this.layoutResourceId = layoutResourceId;
        this.context  = context;
        this.listnerInter1 = listnerInter1;
        this.listnerInter2 = listnerInter2;
        this.icon1= icon1;
        this.icon2= icon2;
        }

@Override
public View getView(int position1, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View itemView=inflater.inflate(layoutResourceId, parent,false);




        ImageView imageView = itemView.findViewById(R.id.android_gridview_image);



        itemView.setTag(R.id.act,position1);
        itemView.setTag(R.id.fullname,imageView);

        imageView.setOnClickListener(v->{
        listnerInter1.execute(itemView);
        });

        return itemView;

        };

public void fillSpinner(Spinner spinner, int array)
{
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
               array , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
}





        }
