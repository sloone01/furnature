package com.example.furnature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.example.furnature.adapters.CatagoryAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.Catagory;
import com.example.furnature.pojos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CatagoriesPage extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<Catagory> catagoryList;
    ListView list;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories_page);
        list = findViewById(R.id.list);
        catagoryList = new ArrayList<>();
        fillList();

    }

    private void fillList() {
        firebaseFirestore.collection(DbCons.Catagory.toString()).get().addOnCompleteListener(task -> {
            for(QueryDocumentSnapshot snapshot : task.getResult())
                catagoryList.add(snapshot.toObject(Catagory.class));
            CatagoryAdapter adapter = new CatagoryAdapter(this, R.layout.cat_item,
                    catagoryList, R.drawable.cat,0, v -> Action1(v), v -> Action1(v));
            list.setAdapter(adapter);
        });


    }

    private void Action1(View v) {
        Catagory catagory = (Catagory) v.getTag();
        Intent intent = new Intent(this,ViewItemsByCatagory.class);
        intent.putExtra(IntentCons.Catagory.toString(),catagory.getCatagoryName());
        startActivity(intent);
    }
}
