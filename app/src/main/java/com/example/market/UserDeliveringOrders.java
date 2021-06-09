package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.market.adapters.UserOrdersAdapter;
import com.example.market.general.DATABASE;
import com.example.market.pojos.Order;
import com.example.market.pojos.constants.Status;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDeliveringOrders extends AppCompatActivity {
    ListView listView;
    private String username;
    private List<Order> orders;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivering_orders);
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        username = pref.getString("username","");
        fileList2();
    }

    private void fileList2() {
        orders = new ArrayList<>();
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("username", username)
                .whereEqualTo("orderStatus", Status.Deleivering.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orders.add(document.toObject(Order.class));

                    UserOrdersAdapter ordersAdapter = new UserOrdersAdapter(this,R.layout.user_orders, orders
                            ,this::edit);
                    listView = findViewById(R.id.list);
                    listView.setAdapter(ordersAdapter);
                });
    }


    private void edit(View view) {
        Order tag = (Order) view.getTag();
        Intent intent = new Intent(this, ShowItems.class);
        intent.putExtra("id",tag.getId());
        startActivity(intent);
    }
}
