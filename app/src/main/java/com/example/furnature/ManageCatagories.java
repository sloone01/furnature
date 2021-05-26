package com.example.furnature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.furnature.adapters.CatagoryAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.Catagory;
import com.example.furnature.pojos.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageCatagories extends AppCompatActivity {


    List<Catagory> catagoryList;
    ListView list;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String collectionPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_catagories);
        collectionPath = DbCons.Catagory.toString();
        list = findViewById(R.id.list);
        fileListt2();
    }

    private void fileListt2() {
        catagoryList = new ArrayList<>();
        firebaseFirestore.collection(collectionPath)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            catagoryList.add(document.toObject(Catagory.class));


                    CatagoryAdapter adapter = new CatagoryAdapter(this, R.layout.cat_item2,
                            catagoryList, R.drawable.cat,0, v -> Action2(v), v -> Action1(v));

                    list.setAdapter(adapter);
                });


    }

    private void Action2(View v) {
        Catagory tag = (Catagory) v.getTag();
        firebaseFirestore.collection(collectionPath).document(tag.getDoc())
                .delete()
                .addOnSuccessListener(aVoid -> startActivity(new Intent(ManageCatagories.this,ManageCatagories.class)))
                .addOnFailureListener(e -> {
                });

        Helper.message(this,"Deleted");

    }

    private void Action1(View v) {
        Catagory tag = (Catagory) v.getTag();
        Intent intent = new Intent(this, EditCatagory.class);
        intent.putExtra(IntentCons.Catagory.toString(),tag);
        startActivity(intent);

    }
}
