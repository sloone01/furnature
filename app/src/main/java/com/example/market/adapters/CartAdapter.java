package com.example.market.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.market.R;
import com.example.market.pojos.OrderItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CartAdapter extends ArrayAdapter<OrderItem> {

        List<OrderItem> orderItems;
        int layoutResourceId;
        Context context;
        MYListnerInter listnerInter1,listnerInter2;

        int icon1,icon2 ;



public CartAdapter(Context context, int layoutResourceId,
                   List<OrderItem> data, MYListnerInter listnerInter1,MYListnerInter listnerInter2) {
        super(context, layoutResourceId, data);


        this.orderItems = data;
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

        TextView title,count,total;
        ImageView edit = rowView.findViewById(R.id.edit),delete = rowView.findViewById(R.id.delete);
        ImageView image = rowView.findViewById(R.id.item_img);

        StorageReference reference = FirebaseStorage.getInstance().getReference().child("images/" + orderItems.get(position).getProduct().getId());
        try {
                final File localFile =  File.createTempFile("images", "");
                reference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        image.setImageBitmap(bitmap);
                }).addOnFailureListener(exception -> Toast.makeText(context,exception.getMessage(),Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
                e.printStackTrace();
        }



        title = rowView.findViewById(R.id.name);
        count = rowView.findViewById(R.id.count);
        total = rowView.findViewById(R.id.total);

        rowView.setTag(position);

        edit.setOnClickListener(v->listnerInter1.execute(rowView));
        delete.setOnClickListener(v->listnerInter2.execute(rowView));


        title.setText(orderItems.get(position).getProduct().getTitle());
        count.setText("Quant: "+orderItems.get(position).getCount()+"");
        total.setText("Price: "+orderItems.get(position).getTotal() +" OMR");


        return rowView;


        };




        }
