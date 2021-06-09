package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furnature.general.DATABASE;
import com.example.furnature.general.SYSTEM;
import com.example.furnature.pojos.Brand;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditBrand extends AppCompatActivity {


    EditText title,description;
    TextView add;
    Brand brand;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_brand);
        initialize();
    }

    private void initialize() {
        title = findViewById(R.id.title);
        description = findViewById(R.id.desc);
        add = findViewById(R.id.sinnp);
        add.setOnClickListener(this::add);
        brand = (Brand) getIntent().getSerializableExtra(SYSTEM.BRAND.toString());
        title.setText(brand.getName());
        description.setText(brand.getSubTitle());
    }

    private void add(View view) {
        brand.setName(title.getText().toString());
        brand.setSubTitle(description.getText().toString());
        firebaseFirestore.collection(DATABASE.BRANDS.toString())
                .document(brand.getId())
                .set(brand)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this,"Brand has been Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditBrand.this, ManageCatagories.class));

                });

    }
}
