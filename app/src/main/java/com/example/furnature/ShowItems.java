package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.furnature.adapters.FurnatureAdapter;
import com.example.furnature.adapters.FurnatureOrderAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.pojos.FItem;
import com.example.furnature.pojos.Order;
import com.example.furnature.pojos.OrderItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShowItems extends AppCompatActivity {

    List<OrderItem> orderItems;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_items);
        loadItems();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadItems() {
        String id = getIntent().getStringExtra("id");
        firebaseFirestore.collection(DbCons.Orders.toString())
                .whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orderItems = document.toObject(Order.class).getItems();



                    FurnatureOrderAdapter furnatureAdapter = new FurnatureOrderAdapter(this,R.layout.furnature_order, orderItems
                            ,null);
                    listView = findViewById(R.id.list);
                    listView.setAdapter(furnatureAdapter);
                });


    }
}
