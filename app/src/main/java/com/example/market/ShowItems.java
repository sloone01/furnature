package com.example.market;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.example.market.adapters.ProductsOrderAdapter;
import com.example.market.general.DATABASE;
import com.example.market.pojos.Order;
import com.example.market.pojos.OrderItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Objects;

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
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orderItems = document.toObject(Order.class).getItems();



                    ProductsOrderAdapter productsOrderAdapter = new ProductsOrderAdapter(this,R.layout.furnature_order, orderItems
                            ,null);
                    listView = findViewById(R.id.list);
                    listView.setAdapter(productsOrderAdapter);
                });


    }
}
