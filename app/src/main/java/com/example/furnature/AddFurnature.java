package com.example.furnature;

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

import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.Brand;
import com.example.furnature.pojos.FItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.furnature.general.SYSTEM.PRODUCT;

public class AddFurnature extends AppCompatActivity {

    private Spinner spinner;
    private List<Brand> brandList;
    private Brand brand;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_furnature);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddFurnature.this,
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
        FItem fItem = new FItem();
        fItem.setCatagory(brand.getName());
        fItem.setDescription(desc.getText().toString().trim());
        fItem.setId(UUID.randomUUID().toString().substring(1,6));
        fItem.setPrice(Float.parseFloat(price.getText().toString().trim()));
        fItem.setTitle(name.getText().toString().trim());
        fItem.setColor(color.getText().toString().trim());
//        firebaseFirestore.collection(DbCons.Furnatures.toString())
//                .document(fItem.getId())
//                .set(fItem)
//                .addOnSuccessListener(aVoid -> startActivity(new Intent(AddFurnature.this, ManageItems.class)));
//

        Intent intent = new Intent(this, AddImages.class);
        intent.putExtra(PRODUCT.toString(),fItem);
        startActivity(intent);

    }
}
