package com.example.furnature.adapters;

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

import com.example.furnature.R;
import com.example.furnature.pojos.FItem;
import com.example.furnature.pojos.Order;
import com.example.furnature.pojos.OrderItem;

import java.util.List;

public class FurnatureOrderAdapter extends ArrayAdapter<OrderItem> {

        List<OrderItem> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public FurnatureOrderAdapter(Context context, int layoutResourceId,
                             List<OrderItem> data, MYListnerInter listnerInter1) {
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

        TextView title,descr,price,count ,total;
        LinearLayout linearLayout = rowView.findViewById(R.id.addtocart);



        title = rowView.findViewById(R.id.name);
        descr = rowView.findViewById(R.id.desc);
        price = rowView.findViewById(R.id.price);
        count = rowView.findViewById(R.id.count);
        total = rowView.findViewById(R.id.total);

        OrderItem orderItem = catagoryList.get(position);
        FItem item = orderItem.getfItem();
        rowView.setTag(orderItem);


        title.setText("Item: "+item.getTitle());
        descr.setText("Description: "+item.getDescription());
        price.setText(item.getPrice() +" OMR");
        count.setText(orderItem.getCount()+"");
        total.setText(orderItem.getTotal()+"OMR");


        return rowView;

        };




        }
