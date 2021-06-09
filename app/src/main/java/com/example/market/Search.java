package com.example.market;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.market.adapters.FurnatureAdapter;
import com.example.market.general.DATABASE;
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
import java.util.stream.Collectors;

import static com.example.market.pojos.constants.Status.Cart;

public class Search extends AppCompatActivity {

    ListView listView;
    private Dialog myDialog;
    private String user;
    private String username;
    List<Product> furnatures;
    List<Order> orders;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getOrder();
        furnatures = new ArrayList<>();
        orders = new ArrayList<>();
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        username = pref.getString("username","");
        TextChanger();
    }

    private void TextChanger() {
        EditText search = findViewById(R.id.searchkey);
        search.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 0)
                    fillGridview(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview(String v) {
        firebaseFirestore.collection(DATABASE.ITEMS.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            furnatures.add(document.toObject(Product.class));

                        furnatures = furnatures.stream().filter(item->
                                item.getDescription().contains(v) || item.getTitle().contains(v))
                                .collect(Collectors.toList());





                    FurnatureAdapter furnatureAdapter = new FurnatureAdapter(this,R.layout.furnature_item,furnatures
                            ,this::showDialog);

                    listView = findViewById(R.id.list);
                    listView.setAdapter(furnatureAdapter);
                });

    }

    private void showDialog(View view) {
        Product tag = (Product) view.getTag();
        LayoutInflater inflater= LayoutInflater.from(this);
        View itemView=inflater.inflate(R.layout.numer_dialoge,null);
        Button confirm = itemView.findViewById(R.id.ok);
        Button cancel = itemView.findViewById(R.id.cancel);
        EditText count = itemView.findViewById(R.id.count);
        confirm.setOnClickListener(v->addtocart(tag,count.getText().toString().trim()));

        myDialog = new Dialog(Search.this);
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
                .addOnSuccessListener(aVoid -> startActivity(new Intent(Search.this, ShowCart.class)));
        myDialog.dismiss();
    }

    private void getOrder() {
        orders = new ArrayList<>();
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("status", Cart.toString())
                .whereEqualTo("user.username",username)
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
            order.setItems(new ArrayList<>());
            orders.add(order);
        }

    }
}