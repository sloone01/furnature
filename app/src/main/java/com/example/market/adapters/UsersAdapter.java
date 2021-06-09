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
import com.example.market.pojos.User;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

        List<User> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public UsersAdapter(Context context, int layoutResourceId,
                    List<User> data, MYListnerInter listnerInter1, MYListnerInter listnerInter2) {
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

        TextView address,username,total,email,phone;
        LinearLayout update , delete;



        address = rowView.findViewById(R.id.address);
        username = rowView.findViewById(R.id.username);
        email = rowView.findViewById(R.id.email);
        phone = rowView.findViewById(R.id.phone);
        delete = rowView.findViewById(R.id.delete);
        update = rowView.findViewById(R.id.update);

        User user = catagoryList.get(position);
        rowView.setTag(user);


        username.setText(user.getUsername());
        address.setText(user.getAddress()+"");
        email.setText(user.getEmail());
        phone.setText(user.getPhone()+"");

        update.setOnClickListener(v->listnerInter1.execute(rowView));
        delete.setOnClickListener(v->listnerInter2.execute(rowView));
        return rowView;



        };




        }
