package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.Catagory;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.channels.SelectableChannel;

public class EditCatagory extends AppCompatActivity {


    EditText title,description;
    TextView add;
    Catagory catagory;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_catagory);
        initialize();
    }

    private void initialize() {
        title = findViewById(R.id.title);
        description = findViewById(R.id.desc);
        add = findViewById(R.id.sinnp);
        add.setOnClickListener(this::add);
        catagory = (Catagory) getIntent().getSerializableExtra(IntentCons.Catagory.toString());
        title.setText(catagory.getCatagoryName());
        description.setText(catagory.getDescription());
    }

    private void add(View view) {
        catagory.setCatagoryName(title.getText().toString());
        catagory.setDescription(description.getText().toString());
        firebaseFirestore.collection(DbCons.Catagory.toString())
                .document(catagory.getDoc())
                .set(catagory)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(EditCatagory.this, ManageCatagories.class)));

        Helper.message(this,"Updated");

    }
}
