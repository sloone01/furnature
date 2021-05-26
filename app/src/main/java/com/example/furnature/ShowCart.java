package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.furnature.adapters.CartAdapter;
import com.example.furnature.adapters.FurnatureAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.Catagory;
import com.example.furnature.pojos.FItem;
import com.example.furnature.pojos.Order;
import com.example.furnature.pojos.OrderItem;
import com.example.furnature.pojos.User;
import com.example.furnature.pojos.constants.Status;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.furnature.general.DbCons.Orders;
import static com.example.furnature.pojos.constants.Status.Cart;
import static com.example.furnature.pojos.constants.Status.Pending;

public class ShowCart extends AppCompatActivity {

    ListView listView;
    private Dialog myDialog;
    List<OrderItem> items;
    Order order;
    User user;
    private String username;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private boolean found = false;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        long longExtra = getIntent().getLongExtra(IntentCons.User.toString(),0);
        LinearLayout linearLayout = findViewById(R.id.checkout);
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        username = pref.getString("username","");
       listView = findViewById(R.id.list);
        fillGridview();
        linearLayout.setOnClickListener(this::Checkout);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Checkout(View view) {
        if (!found)
        {
            Helper.message(this,"Cart Is Empty");
            return;
        }

        order.setTotelCost(items.stream().map(OrderItem::getTotal).reduce(Float::sum).get());
        order.setIssueDate(Helper.dateFormat.format(new Date(System.currentTimeMillis())));
        firebaseFirestore.collection(Orders.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ShowCart.this, OrderDetails.class)));

    }


    private void fillGridview() {
        orders = new ArrayList<>();
        items = new ArrayList<>();
        firebaseFirestore.collection(Orders.toString())
                .whereEqualTo("orderStatus", Cart.toString())
                .whereEqualTo("username",username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orders.add(document.toObject(Order.class));

                        if(orders.size() > 0)
                        {

                            found = true;
                            order = orders.get(0);
                            items = order.getItems();
                        }

                    CartAdapter cartAdapter = new CartAdapter(this,R.layout.cart_item,items
                   ,this::showDialog,this::deleteItem);
                    listView.setAdapter(cartAdapter);

                }).addOnFailureListener(exce->{
                    Helper.message(ShowCart.this,exce.getMessage());
        });

    }

    private void deleteItem(View view) {
        int index = (int) view.getTag();
        order.getItems().remove(index);
        firebaseFirestore.collection(Orders.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ShowCart.this, OrderDetails.class)));

        Helper.message(this,"Deleted");

    }

    private void showDialog(View view) {
        int index = (int) view.getTag();
        OrderItem orderItem = order.getItems().get(index);
        LayoutInflater inflater= LayoutInflater.from(this);
        View itemView=inflater.inflate(R.layout.numer_dialoge,null);
        Button confirm = itemView.findViewById(R.id.ok);
        Button cancel = itemView.findViewById(R.id.cancel);
        EditText count = itemView.findViewById(R.id.count);
        count.setText(orderItem.getCount()+"");
        confirm.setOnClickListener(v->addtocart(index,count.getText().toString().trim()));

        myDialog = new Dialog(ShowCart.this);
        myDialog.getWindow();
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(true);
        myDialog.setContentView(itemView);
        myDialog.show();
        cancel.setOnClickListener(v->myDialog.dismiss());
    }

    private void addtocart(int index, String trim) {
        int count = Integer.parseInt(trim);
        order.getItems().get(index).setCount(count);
        order.getItems().get(index).setTotal(order.getItems().get(index).getfItem().getPrice()*count);
        firebaseFirestore.collection(Orders.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> Helper.message(ShowCart.this,"Updated"));

        myDialog.dismiss();


    }

}
