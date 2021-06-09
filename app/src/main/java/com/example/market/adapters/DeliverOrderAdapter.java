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

public class DeliverOrderAdapter extends ArrayAdapter<Order> {

        List<Order> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public DeliverOrderAdapter(Context context, int layoutResourceId,
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

        TextView userRef,date,desiredTime,addrss;
        LinearLayout show,done;



        userRef = rowView.findViewById(R.id.userRef);
        date = rowView.findViewById(R.id.deliverOn);
        desiredTime = rowView.findViewById(R.id.desiredTime);
        addrss = rowView.findViewById(R.id.address);
        show = rowView.findViewById(R.id.map);
        done = rowView.findViewById(R.id.done);


        Order order = catagoryList.get(position);
        rowView.setTag(order);


        userRef.setText("Username: "+order.getUsername()+"");
        addrss.setText("Contact: "+order.getContact());
        date.setText("Deliver Date: "+order.getDeliverDate());
        desiredTime.setText(order.getPrefTime());

        show.setOnClickListener(v->listnerInter1.execute(rowView));
        done.setOnClickListener(v->listnerInter2.execute(rowView));
        return rowView;



        };




        }
