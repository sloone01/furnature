package com.example.furnature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.furnature.adapters.FurnatureAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.Catagory;
import com.example.furnature.pojos.FItem;
import com.example.furnature.pojos.Order;
import com.example.furnature.pojos.OrderItem;
import com.example.furnature.pojos.constants.Status;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ManageItems extends AppCompatActivity {


    Catagory catagory;
    ListView listView;
    List<FItem> furnatures;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);
        furnatures = new ArrayList<>();
        fillGridview();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview() {
        firebaseFirestore.collection(DbCons.Furnatures.toString())
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                    furnatures.add(document.toObject(FItem.class));

                    FurnatureAdapter furnatureAdapter = new FurnatureAdapter(this,R.layout.furnature_item2,furnatures
                            ,this::edit);
                    listView = findViewById(R.id.list);
                    listView.setAdapter(furnatureAdapter);
                        });




    }



    private void edit(View view) {
        FItem tag = (FItem) view.getTag();
        Intent intent = new Intent(this,EditItem.class);
        intent.putExtra(IntentCons.Item.toString(), tag);
        startActivity(intent);
    }


}

