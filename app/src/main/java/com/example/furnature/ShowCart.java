package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.furnature.adapters.CartAdapter;
import com.example.furnature.general.Helper;
import com.example.furnature.general.SYSTEM;
import com.example.furnature.pojos.Order;
import com.example.furnature.pojos.OrderItem;
import com.example.furnature.pojos.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.furnature.general.DATABASE.ORDERS;
import static com.example.furnature.pojos.constants.Status.Cart;

public class ShowCart extends AppCompatActivity {

    public static final String DELETED = "Deleted";
    public static final String CART_IS_EMPTY = "Cart Is Empty";
    public static final String UPDATED_CART_SUCCEFULLY = "Updated Cart Succefully";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String USERNAME = "username";
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
        long longExtra = getIntent().getLongExtra(SYSTEM.USER.toString(),0);
        LinearLayout linearLayout = findViewById(R.id.checkout);
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        username = pref.getString("username","");
       listView = findViewById(R.id.list);
        fillGridview();
        linearLayout.setOnClickListener(this::Checkout);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Checkout(View view) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);


        if (!found)
        {
            Toast.makeText(this, CART_IS_EMPTY,Toast.LENGTH_SHORT).show();
            return;
        }

        order.setTotelCost(items.stream().map(OrderItem::getTotal).reduce(Float::sum).get());
        order.setIssueDate(dateFormat.format(new Date(System.currentTimeMillis())));
        firebaseFirestore.collection(ORDERS.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ShowCart.this, OrderDetails.class)));

    }


    private void fillGridview() {
        orders = new ArrayList<>();
        items = new ArrayList<>();
        firebaseFirestore.collection(ORDERS.toString())
                .whereEqualTo(ORDER_STATUS, Cart.toString())
                .whereEqualTo(USERNAME,username)
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
                    Toast.makeText(this,exce.getMessage(),Toast.LENGTH_SHORT).show();
        });

    }

    private void deleteItem(View view) {
        int index = (int) view.getTag();
        order.getItems().remove(index);
        firebaseFirestore.collection(ORDERS.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ShowCart.this, OrderDetails.class)));

        Toast.makeText(this, DELETED,Toast.LENGTH_SHORT).show();


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
        firebaseFirestore.collection(ORDERS.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid ->Toast.makeText(this, UPDATED_CART_SUCCEFULLY,Toast.LENGTH_SHORT).show());

        myDialog.dismiss();


    }

}
