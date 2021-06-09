package com.example.market.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.market.AdminOrderItems;
import com.example.market.AdminPendingOrders;
import com.example.market.R;
import com.example.market.general.DATABASE;
import com.example.market.pojos.Order;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminPendOrdersAdapter extends ArrayAdapter<Order> {

        List<Order> catagoryList;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();




public AdminPendOrdersAdapter(Context context, int layoutResourceId,
                              List<Order> data, MYListnerInter listnerInter1) {
        super(context, layoutResourceId, data);


        this.catagoryList = data;
        this.layoutResourceId = layoutResourceId;
        this.context  = context;
        this.listnerInter1 = listnerInter1;
        this.listnerInter2 = listnerInter2;

        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View rowView=inflater.inflate(R.layout.order_pending_item, parent,false);

        Order order = catagoryList.get(position);
        LinearLayout show = rowView.findViewById(R.id.addtocart);
        LinearLayout approve = rowView.findViewById(R.id.approve);
        show.setOnClickListener(v->{
                Intent intent = new Intent(context, AdminOrderItems.class);
                intent.putExtra("id",order.getId());
                context.startActivity(intent);
        });
        approve.setOnClickListener(v->{
                firebaseFirestore.collection(DATABASE.ORDERS.toString())
                        .document(order.getId())
                        .set(order)
                        .addOnSuccessListener(aVoid -> {
                                context.startActivity(new Intent(context, AdminPendingOrders.class));
                        });

        });

        TextView userRef,date,total;

        userRef = rowView.findViewById(R.id.userRef);
        date = rowView.findViewById(R.id.issueDate);
        total = rowView.findViewById(R.id.price);

        ;
        rowView.setTag(order);

        userRef.setText(order.getUsername()+"");
        date.setText(order.getIssueDate());
        total.setText(order.getTotelCost() +" OMR");

        return rowView;

        };
}
