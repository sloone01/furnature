package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.furnature.adapters.ViewCatagoryAdapter;
import com.example.furnature.general.DATABASE;
import com.example.furnature.general.SYSTEM;
import com.example.furnature.pojos.Brand;
import com.example.furnature.pojos.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BrandsPage extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<Brand> brandList;
    ListView list;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories_page);
        list = findViewById(R.id.list);
        brandList = new ArrayList<>();
        fillList();

    }

    private void fillList() {
        firebaseFirestore.collection(DATABASE.BRANDS.toString()).get().addOnCompleteListener(task -> {
            for(QueryDocumentSnapshot snapshot : task.getResult())
                brandList.add(snapshot.toObject(Brand.class));
            ViewCatagoryAdapter adapter = new ViewCatagoryAdapter(this,brandList);
            list.setAdapter(adapter);
        });


    }

    private void Action1(View v) {
        Brand brand = (Brand) v.getTag();
        Intent intent = new Intent(this, ViewItemsByBrand.class);
        intent.putExtra(SYSTEM.BRAND.toString(), brand.getName());
        startActivity(intent);
    }
}
