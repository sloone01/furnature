package com.example.market;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.market.general.DATABASE;
import com.example.market.general.SYSTEM;
import com.example.market.pojos.Brand;
import com.example.market.pojos.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EditItem extends AppCompatActivity {
    private Spinner spinner;
    private List<Brand> brandList;
    private Brand brand;
    private Product product;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        product = (Product) getIntent().getSerializableExtra(SYSTEM.PRODUCT.toString());
        TextView add = findViewById(R.id.add);
        TextView delete = findViewById(R.id.delete);
        delete.setOnClickListener(this::delete);
        spinner = findViewById(R.id.catagories);
        getCatagories();
        add.setOnClickListener(this::saveItem);
        init();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand = (Brand) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        EditText name= findViewById(R.id.name),price = findViewById(R.id.Price),
                desc = findViewById(R.id.desc),color = findViewById(R.id.color);


        name.setText(product.getTitle());
        price.setText(product.getPrice()+"");
        desc.setText(product.getDescription());
        color.setText(product.getColor());

    }

    private void delete(View view) {
        firebaseFirestore.collection(DATABASE.ITEMS.toString()).document(product.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditItem.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(EditItem.this,ManageItems.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCatagories() {
        brandList = new ArrayList<>();
            firebaseFirestore.collection(DATABASE.BRANDS.toString())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                brandList.add(document.toObject(Brand.class));

                        List<String> collect = brandList.stream().map(Brand::getName).collect(Collectors.toList());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditItem.this,
                                android.R.layout.simple_spinner_item, collect);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                        spinner.setSelection(dataAdapter.getPosition(this.brandList.get(0).getName()));
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                brand = brandList.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    });


        }


    private void saveItem(View view) {
        EditText name= findViewById(R.id.name),price = findViewById(R.id.Price),
                desc = findViewById(R.id.desc),color = findViewById(R.id.color);
        product.setCatagory(brand.getName());
        product.setDescription(desc.getText().toString().trim());
        product.setPrice(Float.parseFloat(price.getText().toString().trim()));
        product.setTitle( name.getText().toString().trim());
            firebaseFirestore.collection(DATABASE.ITEMS.toString())
                    .document(product.getId())
                    .set(product)
                    .addOnSuccessListener(aVoid -> startActivity(new Intent(EditItem.this, ManageItems.class)));

            Intent intent = new Intent(this, ManageItems.class);
        startActivity(intent);

    }
}

