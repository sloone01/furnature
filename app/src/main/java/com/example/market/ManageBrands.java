package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.market.adapters.CatagoryAdapter;
import com.example.market.general.DATABASE;
import com.example.market.pojos.Brand;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageBrands extends AppCompatActivity {


    List<Brand> brandList;
    private Dialog myDialog;
    GridView list;
    TextView addBrand ;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String collectionPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_brands);
        collectionPath = DATABASE.BRANDS.toString();
        addBrand = findViewById(R.id.addBrand);
        addBrand.setOnClickListener(this::openDialog);
        ImageView exit = findViewById(R.id.exit);
        exit.setOnClickListener(v->startActivity(new Intent(this, AdminMenue.class)));
        list = findViewById(R.id.list);
        fileListt2();
    }

    private void fileListt2() {
        brandList = new ArrayList<>();
        firebaseFirestore.collection(collectionPath)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            brandList.add(document.toObject(Brand.class));


                    CatagoryAdapter adapter = new CatagoryAdapter(this,brandList);

                    list.setAdapter(adapter);
                });
    }

    private void openDialog(View view)
    {
        LayoutInflater inflater= LayoutInflater.from(this);
        View itemView=inflater.inflate(R.layout.add_brand,null);
        Button confirm = itemView.findViewById(R.id.ok);
        Button cancel = itemView.findViewById(R.id.cancel);
        EditText brand = itemView.findViewById(R.id.brand), desc = itemView.findViewById(R.id.description);;
        confirm.setOnClickListener(v->{
            if (brand.getText().toString().trim().equals("") || desc.getText().toString().trim().equals(""))
            {
                Toast.makeText(this,"Give a brand name and description please",Toast.LENGTH_SHORT).show();
                return;
            }
            add(brand.getText().toString().trim(),desc.getText().toString().trim());});

        myDialog = new Dialog(ManageBrands.this);
        myDialog.getWindow();
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(true);
        myDialog.setContentView(itemView);
        myDialog.show();
        cancel.setOnClickListener(v->myDialog.dismiss());
    }
    private void add(String title,String desc) {
        Brand brand = new Brand();
        brand.setName(title);
        brand.setSubTitle(desc);
        brand.setId(brand.getName()+"_doc");
        firebaseFirestore.collection(DATABASE.BRANDS.toString())
                .document(brand.getId())
                .set(brand)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ManageBrands.this, ManageBrands.class)));

        Toast.makeText(this,"Saved Succefully",Toast.LENGTH_SHORT).show();
    }



}
