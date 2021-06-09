package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.furnature.adapters.CatagoryAdapter;
import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.Brand;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageCatagories extends AppCompatActivity {


    List<Brand> brandList;
    ListView list;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String collectionPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_catagories);
        collectionPath = DATABASE.BRANDS.toString();
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


}
