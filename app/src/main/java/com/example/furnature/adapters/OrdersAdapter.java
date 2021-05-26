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

import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {

        List<Order> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public OrdersAdapter(Context context, int layoutResourceId,
                     List<Order> data, MYListnerInter listnerInter1) {
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

        TextView address,date,total;



        address = rowView.findViewById(R.id.address);
        date = rowView.findViewById(R.id.date);
        total = rowView.findViewById(R.id.total);

        rowView.setTag(catagoryList.get(position));


        address.setText(catagoryList.get(position).getAddress()+"");
        date.setText(catagoryList.get(position).getIssueDate());
        total.setText(catagoryList.get(position).getTotelCost() +" OMR");


        return rowView;

        };




        }
