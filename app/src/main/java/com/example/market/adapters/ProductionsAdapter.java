package com.example.market.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.market.R;
import com.example.market.pojos.Product;

import java.util.List;

public class ProductionsAdapter extends ArrayAdapter<Product> {

        List<Product> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public ProductionsAdapter(Context context, int layoutResourceId,
                          List<Product> data, MYListnerInter listnerInter1) {
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

        TextView title,descr,price;
        LinearLayout linearLayout = rowView.findViewById(R.id.addtocart);



        title = rowView.findViewById(R.id.name);
        descr = rowView.findViewById(R.id.desc);
        price = rowView.findViewById(R.id.price);
        linearLayout.setOnClickListener(v->listnerInter1.execute(rowView));

        rowView.setTag(catagoryList.get(position));


        title.setText(catagoryList.get(position).getTitle());
        descr.setText(catagoryList.get(position).getDescription());
        price.setText(catagoryList.get(position).getPrice() +" OMR");


        return rowView;

        };




        }
