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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.market.R;
import com.example.market.ViewItemsByBrand;
import com.example.market.general.SYSTEM;
import com.example.market.pojos.Brand;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ViewCatagoryAdapter extends ArrayAdapter<Brand> {

        List<Brand> brandList;
        Context context;



public ViewCatagoryAdapter(Context context, List<Brand> data) {
        super(context, R.layout.cat_item, data);
        this.brandList = data;
        this.context  = context;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cat_item, parent,false);
        RatingBar ratingBar;

        TextView textrecycler,descr;

        ImageView img = rowView.findViewById(R.id.img);

        StorageReference reference = FirebaseStorage.getInstance().getReference().child("images/" + brandList.get(position).getId());
        try {
                final File localFile =  File.createTempFile("images", "");
                reference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        img.setImageBitmap(bitmap);
                }).addOnFailureListener(exception -> Toast.makeText(context,exception.getMessage(),Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
                e.printStackTrace();
        }



        textrecycler = rowView.findViewById(R.id.name);
        descr = rowView.findViewById(R.id.descr);

        rowView.setOnClickListener(v->
        {
                Brand brand =  brandList.get(position);
                Intent intent = new Intent(context, ViewItemsByBrand.class);
                intent.putExtra(SYSTEM.BRAND.toString(), brand.getName());
                context.startActivity(intent);
        });


        textrecycler.setText(brandList.get(position).getName());


        return rowView;

        };




        }
