



package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.market.adapters.FurnatureAdapter;
import com.example.market.general.DATABASE;
import com.example.market.general.SYSTEM;
import com.example.market.pojos.Product;
import com.example.market.pojos.Order;
import com.example.market.pojos.OrderItem;
import com.example.market.pojos.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.market.pojos.constants.Status.Cart;

public class ViewItemsByBrand extends AppCompatActivity {

    String catagoryName;
    ListView listView;
    private Dialog myDialog;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<Product> items;
    List<Order> orders;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items_by_catagoey);
        Intent intent = getIntent();
        catagoryName = intent.getStringExtra(SYSTEM.BRAND.toString());
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        username = pref.getString("username","");
        setOrder();
        TextView title = findViewById(R.id.title);
        title.setText(catagoryName);
        fillGridview();
    }

    private void fillGridview() {
        items = new ArrayList<>();

        firebaseFirestore.collection(DATABASE.ITEMS.toString())
                .whereEqualTo("catagory", catagoryName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            items.add(document.toObject(Product.class));


                    FurnatureAdapter furnatureAdapter = new FurnatureAdapter(this,R.layout.furnature_item, items
                            ,this::showDialog);
                    listView = findViewById(R.id.list);
                    listView.setAdapter(furnatureAdapter);
                });

        FurnatureAdapter furnatureAdapter = new FurnatureAdapter(this,R.layout.furnature_item, items
                ,this::showDialog);
        listView = findViewById(R.id.list);
        listView.setAdapter(furnatureAdapter);

    }

    private void showDialog(View view) {
        Product tag = (Product) view.getTag();
        LayoutInflater inflater= LayoutInflater.from(this);
        View itemView=inflater.inflate(R.layout.numer_dialoge,null);
        Button confirm = itemView.findViewById(R.id.ok);
        Button cancel = itemView.findViewById(R.id.cancel);
        EditText count = itemView.findViewById(R.id.count);
        confirm.setOnClickListener(v->addtocart(tag,count.getText().toString().trim()));

        myDialog = new Dialog(ViewItemsByBrand.this);
        myDialog.getWindow();
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(true);
        myDialog.setContentView(itemView);
        myDialog.show();
        cancel.setOnClickListener(v->myDialog.dismiss());

    }

    private void addtocart(Product tag, String trim) {
        OrderItem orderItem = new OrderItem();
        int count = Integer.parseInt(trim);
        orderItem.setCount(count);
        orderItem.setProduct(tag);
        orderItem.setTotal(count* tag.getPrice());
        Order order = orders.get(0);
        order.getItems().add(orderItem);
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ViewItemsByBrand.this, ShowCart.class)));
        myDialog.dismiss();
    }

    private void setOrder() {
        orders = new ArrayList<>();
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("orderStatus", Cart.toString())
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orders.add(document.toObject(Order.class));
                });

        if (orders.size() == 0){
            Order order = new Order();
            order.setId(UUID.randomUUID().toString().substring(1,6));
            order.setOrderStatus(Cart.toString());
            User user = new User();
            user.setUsername(username);
            order.setUser(user);
            order.setUsername(username);
            order.setItems(new ArrayList<>());
            orders.add(order);
        }

    }
}
