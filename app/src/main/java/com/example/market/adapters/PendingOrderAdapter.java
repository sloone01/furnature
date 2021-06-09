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
import com.example.market.pojos.Order;

import java.util.List;

public class PendingOrderAdapter extends ArrayAdapter<Order> {

        List<Order> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public PendingOrderAdapter(Context context, int layoutResourceId,
                           List<Order> data, MYListnerInter listnerInter1,MYListnerInter listnerInter2) {
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

        TextView address,date,total,userRef;
        LinearLayout approve , show;



        address = rowView.findViewById(R.id.address);
        date = rowView.findViewById(R.id.issueDate);
        userRef = rowView.findViewById(R.id.userRef);
        total = rowView.findViewById(R.id.total);
        show = rowView.findViewById(R.id.items);
        approve = rowView.findViewById(R.id.approve);

        Order order = catagoryList.get(position);
        rowView.setTag(order);


        userRef.setText(order.getUsername());
        address.setText(order.getAddress()+"");
        date.setText(order.getIssueDate());
        total.setText(order.getTotelCost() +" OMR");

        approve.setOnClickListener(v->listnerInter1.execute(rowView));
        show.setOnClickListener(v->listnerInter2.execute(rowView));
        return rowView;



        };




        }
