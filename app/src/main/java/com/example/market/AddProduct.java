package com.example.market;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.market.general.DATABASE;
import com.example.market.pojos.Brand;
import com.example.market.pojos.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.market.general.SYSTEM.PRODUCT;

public class AddProduct extends AppCompatActivity {

    private Spinner spinner;
    private List<Brand> brandList;
    private Brand brand;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        TextView add = findViewById(R.id.add);
        brandList = new ArrayList<>();
        spinner = findViewById(R.id.catagories);
        addCatagoriesToSpinner();
        add.setOnClickListener(this::saveItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addCatagoriesToSpinner() {

        firebaseFirestore.collection(DATABASE.BRANDS.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            brandList.add(document.toObject(Brand.class));

                    List<String> collect = brandList.stream().map(Brand::getName).collect(Collectors.toList());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddProduct.this,
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
        Product product = new Product();
        product.setCatagory(brand.getName());
        product.setDescription(desc.getText().toString().trim());
        product.setId(UUID.randomUUID().toString().substring(1,6));
        product.setPrice(Float.parseFloat(price.getText().toString().trim()));
        product.setTitle(name.getText().toString().trim());
        product.setColor(color.getText().toString().trim());
//        firebaseFirestore.collection(DbCons.Furnatures.toString())
//                .document(fItem.getId())
//                .set(fItem)
//                .addOnSuccessListener(aVoid -> startActivity(new Intent(AddFurnature.this, ManageItems.class)));
//

        Intent intent = new Intent(this, AddImages.class);
        intent.putExtra(PRODUCT.toString(), product);
        startActivity(intent);

    }
}
