package com.example.furnature.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.furnature.EditBrand;
import com.example.furnature.ManageBrands;
import com.example.furnature.R;
import com.example.furnature.general.DATABASE;
import com.example.furnature.general.SYSTEM;
import com.example.furnature.pojos.Brand;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CatagoryAdapter extends ArrayAdapter<Brand> {

        List<Brand> brandList;
        Context context;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();



public CatagoryAdapter(Context context,                       List<Brand> data) {
        super(context, R.layout.cat_item2, data);
        this.brandList = data;
        this.context  = context;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cat_item2, parent,false);
        RatingBar ratingBar;

        TextView textrecycler,descr;
        ImageView delete = rowView.findViewById(R.id.edit),edit =rowView.findViewById(R.id.delete);


        textrecycler = rowView.findViewById(R.id.name);
        descr = rowView.findViewById(R.id.descr);

        Brand brand = brandList.get(position);

        delete.setOnClickListener(v->
        {
                firebaseFirestore.collection(DATABASE.BRANDS.toString()).document(brand.getId())
                        .delete()
                        .addOnSuccessListener(aVoid -> context.startActivity(new Intent(context, ManageBrands.class)))
                        .addOnFailureListener(e -> {
                        });

                Toast.makeText(context,"Deleted succefully",Toast.LENGTH_SHORT).show();
        });
        edit.setOnClickListener(v-> {
                Intent intent = new Intent(context, EditBrand.class);
                intent.putExtra(SYSTEM.BRAND.toString(), brand);
                context.startActivity(intent);
        });


        textrecycler.setText(brand.getName());
        descr.setText(brand.getSubTitle());


        return rowView;

        };




        }
