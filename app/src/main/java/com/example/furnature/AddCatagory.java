package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.pojos.Catagory;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCatagory extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    EditText title,description;
    TextView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catagory);
        title = findViewById(R.id.title);
        description = findViewById(R.id.desc);
        add = findViewById(R.id.sinnp);
        add.setOnClickListener(this::add);
    }

    private void add(View view) {
        Catagory catagory = new Catagory();
        catagory.setCatagoryName(title.getText().toString());
        catagory.setDescription(description.getText().toString());
        catagory.setDoc(catagory.getCatagoryName()+"_doc");
        firebaseFirestore.collection(DbCons.Catagory.toString())
                .document(catagory.getDoc())
                .set(catagory)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(AddCatagory.this, ManageCatagories.class)));

        Helper.message(this,"Added");
    }
}
