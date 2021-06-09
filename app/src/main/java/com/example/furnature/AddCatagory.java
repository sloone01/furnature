package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.Brand;
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
        Brand brand = new Brand();
        brand.setName(title.getText().toString());
        brand.setSubTitle(description.getText().toString());
        brand.setId(brand.getName()+"_doc");
        firebaseFirestore.collection(DATABASE.BRANDS.toString())
                .document(brand.getId())
                .set(brand)
                .addOnSuccessListener(aVoid -> startActivity(new Intent(AddCatagory.this, ManageBrands.class)));

        Toast.makeText(this,"Saved Succefully",Toast.LENGTH_SHORT).show();
    }
}
