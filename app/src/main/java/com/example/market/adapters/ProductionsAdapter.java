package com.example.market.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.market.EditItem;
import com.example.market.R;
import com.example.market.general.SYSTEM;
import com.example.market.pojos.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductionsAdapter extends ArrayAdapter<Product> {

        List<Product> catagoryList;
        int layoutResourceId;
        Context context;


public ProductionsAdapter(Context context,
                          List<Product> data) {
        super(context, R.layout.products_item2, data);


        this.catagoryList = data;
        this.context  = context;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View rowView=inflater.inflate(R.layout.products_item2, parent,false);

        ImageView image = rowView.findViewById(R.id.image);

        TextView title,descr,price,old_price;
        LinearLayout linearLayout = rowView.findViewById(R.id.addtocart);


        Product product = catagoryList.get(position);
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("images/" + product.getId());
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
        descr = rowView.findViewById(R.id.desc);
        price = rowView.findViewById(R.id.price);
        old_price = rowView.findViewById(R.id.old_price);
        linearLayout.setOnClickListener(v-> {
                Intent intent = new Intent(context, EditItem.class);
                intent.putExtra(SYSTEM.PRODUCT.toString(),product);
                context.startActivity(intent);
        });



        title.setText(product.getTitle());
        descr.setText(product.getDescription());
        price.setText("Price:"+product.getPrice() +" OMR");
        old_price.setText("Purchase Price:"+product.getOldPrice() +" OMR");


        return rowView;

        };




        }
