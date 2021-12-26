package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.market.general.DATABASE;
import com.example.market.general.SYSTEM;
import com.example.market.pojos.Brand;
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
    }

    private void add(View view) {
        brand.setName(title.getText().toString());

        firebaseFirestore.collection(DATABASE.BRANDS.toString())
                .document(brand.getId())
                .set(brand)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this,"Brand has been Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditBrand.this, ManageBrands.class));

                });

    }
}
