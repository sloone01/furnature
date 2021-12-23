package com.example.market;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.market.general.DATABASE;
import com.example.market.pojos.Order;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.market.pojos.constants.Status.Cart;
import static com.example.market.pojos.constants.Status.Pending;

public class OrderDetails extends AppCompatActivity{

    final Calendar myCalendar = Calendar.getInstance();
    EditText address,langlat,prefTime,contactInfo,date;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String username;
    List<Order> orders;
    TextView add;
    FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        username = pref.getString("username","");
        orders = new ArrayList<>();
        loadOrder();
        init();
    }



    private void loadOrder() {
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .whereEqualTo("orderStatus", Cart.toString())
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            orders.add(document.toObject(Order.class));
                });



        }
    private void init() {
        date = findViewById(R.id.deliveryDate);
        address = findViewById(R.id.Address);
        prefTime = findViewById(R.id.preferedTime);
        contactInfo = findViewById(R.id.contact);
        add = findViewById(R.id.sinnp);
        add.setOnClickListener(this::addDetails);
        initDatTimePicker(date);

    }

    private void initDatTimePicker(final EditText textView) {
        DatePickerDialog.OnDateSetListener date = (view , year, monthOfYear,
                                                   dayOfMonth) ->{
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(textView,myCalendar);
        };


        textView.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR) , myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
    private void updateLabel(EditText editText,Calendar calender) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(calender.getTime()));
    }
    private void addDetails(View view) {
        Order order = orders.get(0);
        order.setContact(Long.parseLong(contactInfo.getText().toString()));
        order.setAddress(address.getText().toString());
        order.setPreferdTime(prefTime.getText().toString());
        order.setDesiredDeliveryDate(date.getText().toString());
        order.setOrderStatus(Pending.toString());
        firebaseFirestore.collection(DATABASE.ORDERS.toString())
                .document(order.getId())
                .set(order)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(this, UserPendingOrders.class)));

    }

    }

