package com.example.furnature;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.Catagory;
import com.example.furnature.pojos.FItem;
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
    private List<Catagory> catagoryList;
    private Catagory catagory;
    private FItem furnature;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        furnature = (FItem) getIntent().getSerializableExtra(IntentCons.Item.toString());
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
                catagory = (Catagory) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        EditText name= findViewById(R.id.name),price = findViewById(R.id.Price),
                desc = findViewById(R.id.desc),color = findViewById(R.id.color);


        name.setText(furnature.getTitle());
        price.setText(furnature.getPrice()+"");
        desc.setText(furnature.getDescription());
        color.setText(furnature.getColor());

    }

    private void delete(View view) {
        firebaseFirestore.collection(DbCons.Furnatures.toString()).document(furnature.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Helper.message(EditItem.this,"Deleted");
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
        catagoryList = new ArrayList<>();
            firebaseFirestore.collection(DbCons.Catagory.toString())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                catagoryList.add(document.toObject(Catagory.class));

                        List<String> collect = catagoryList.stream().map(Catagory::getCatagoryName).collect(Collectors.toList());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditItem.this,
                                android.R.layout.simple_spinner_item, collect);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                        spinner.setSelection(dataAdapter.getPosition(this.catagoryList.get(0).getCatagoryName()));
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                catagory = catagoryList.get(position);
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
        furnature.setCatagory(catagory.getCatagoryName());
        furnature.setDescription(desc.getText().toString().trim());
        furnature.setPrice(Float.parseFloat(price.getText().toString().trim()));
        furnature.setTitle( name.getText().toString().trim());
            firebaseFirestore.collection(DbCons.Furnatures.toString())
                    .document(furnature.getId())
                    .set(furnature)
                    .addOnSuccessListener(aVoid -> startActivity(new Intent(EditItem.this, ManageItems.class)));

            Intent intent = new Intent(this, ManageItems.class);
        startActivity(intent);

    }
}

