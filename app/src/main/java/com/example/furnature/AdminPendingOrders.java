package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.furnature.adapters.PendingOrderAdapter;
import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.Order;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.furnature.pojos.constants.Status.Deleivering;
import static com.example.furnature.pojos.constants.Status.Pending;

public class AdminPendingOrders extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<Order> orderList;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_orders);
        fillGridview();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview() {
        orderList = new ArrayList<>();
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("orderStatus", Pending.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orderList.add(document.toObject(Order.class));

                    PendingOrderAdapter ordersAdapter = new PendingOrderAdapter(this, R.layout.order_pending_item, orderList
                            , this::Done,this::showItems);
                    ListView listView = findViewById(R.id.list);
                    listView.setAdapter(ordersAdapter);
                });


    }

    private void showItems(View view) {
        Order tag = (Order) view.getTag();
        Intent intent = new Intent(this, ShowItems.class);
        intent.putExtra("id",tag.getId());
        startActivity(intent);
    }

    private void Done(View view) {
        Order tag = (Order) view.getTag();
        tag.setOrderStatus(Deleivering.toString());

        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .document(tag.getId())
                .set(tag)
                .addOnSuccessListener(aVoid -> {
                    startActivity(
                            new Intent(AdminPendingOrders.this,AdminPendingOrders.class)
                    );
                });




    }
}
