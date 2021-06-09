package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.furnature.adapters.FurnatureAdapter;
import com.example.furnature.general.DATABASE;
import com.example.furnature.general.SYSTEM;
import com.example.furnature.pojos.Brand;
import com.example.furnature.pojos.FItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageItems extends AppCompatActivity {


    Brand brand;
    ListView listView;
    List<FItem> furnatures;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);
        TextView addProduct = findViewById(R.id.add_product);
        addProduct.setOnClickListener(v -> startActivity(new Intent(this, AddProduct.class)));
        ImageView exit = findViewById(R.id.exit);
        exit.setOnClickListener(v->startActivity(new Intent(this, AdminMenue.class)));
        furnatures = new ArrayList<>();
        fillGridview();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview() {
        firebaseFirestore.collection(DATABASE.ITEMS.toString())
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                    furnatures.add(document.toObject(FItem.class));

                    FurnatureAdapter furnatureAdapter = new FurnatureAdapter(this,R.layout.furnature_item2,furnatures
                            ,this::edit);
                    listView = findViewById(R.id.list);
                    listView.setAdapter(furnatureAdapter);
                        });




    }



    private void edit(View view) {
        FItem tag = (FItem) view.getTag();
        Intent intent = new Intent(this,EditItem.class);
        intent.putExtra(SYSTEM.PRODUCT.toString(), tag);
        startActivity(intent);
    }


}

