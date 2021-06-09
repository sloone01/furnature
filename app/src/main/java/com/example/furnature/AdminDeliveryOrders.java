package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.furnature.adapters.DeliverOrderAdapter;
import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.Order;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.furnature.pojos.constants.Status.Deleivering;
import static com.example.furnature.pojos.constants.Status.Done;

public class AdminDeliveryOrders extends AppCompatActivity {


    List<Order> orderList;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delevery_orders);
        fillGridview();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview() {
        orderList = new ArrayList<>();
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("orderStatus", Deleivering.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orderList.add(document.toObject(Order.class));

                    DeliverOrderAdapter ordersAdapter = new DeliverOrderAdapter(this, R.layout.delivering_orders, orderList
                            , this::map,this::Done);
                    ListView listView = findViewById(R.id.list);
                    listView.setAdapter(ordersAdapter);
                });


    }

    private void map(View view) {
        Order tag = (Order) view.getTag();
        Intent intent = new Intent(this, ShowMap.class);
        intent.putExtra("lon",tag.getLon());
        intent.putExtra("lat",tag.getLat());
        startActivity(intent);
    }

    private void Done(View view) {
        Order tag = (Order) view.getTag();
        tag.setOrderStatus(Done.toString());
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .document(tag.getId())
                .set(tag)
                .addOnSuccessListener(aVoid -> {
                    startActivity(
                            new Intent(AdminDeliveryOrders.this,AdminDeliveryOrders.class)
                    );

                });

    }
}
