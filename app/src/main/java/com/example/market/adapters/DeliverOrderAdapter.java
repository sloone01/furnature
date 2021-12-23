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
                           List<Order> data, MYListnerInter listnerInter1) {
        super(context, layoutResourceId, data);


        this.catagoryList = data;
        this.layoutResourceId = layoutResourceId;
        this.context  = context;
        this.listnerInter1 = listnerInter1;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View rowView=inflater.inflate(layoutResourceId, parent,false);

        TextView userRef,address,deliveron;
        LinearLayout show,done;



        userRef = rowView.findViewById(R.id.userRef);
        address = rowView.findViewById(R.id.address);
        deliveron = rowView.findViewById(R.id.deliverOn);
        done = rowView.findViewById(R.id.done);


        Order order = catagoryList.get(position);
        rowView.setTag(order);


        userRef.setText("Username: "+order.getUsername()+"");
        deliveron.setText("Contact: "+order.getContact());
        address.setText("Address =: "+order.getAddress());
        done.setOnClickListener(v->listnerInter2.execute(rowView));
        return rowView;



        };




        }
