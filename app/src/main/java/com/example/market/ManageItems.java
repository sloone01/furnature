package com.example.market;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.market.adapters.ProductionsAdapter;
import com.example.market.general.DATABASE;
import com.example.market.general.SYSTEM;
import com.example.market.pojos.Brand;
import com.example.market.pojos.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageItems extends AppCompatActivity {


    Brand brand;
    GridView gridView;
    List<Product> products;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);
        TextView addProduct = findViewById(R.id.add_product);
        addProduct.setOnClickListener(v -> startActivity(new Intent(this, AddProduct.class)));
        ImageView exit = findViewById(R.id.exit);
        exit.setOnClickListener(v->startActivity(new Intent(this, AdminMenue.class)));
        products = new ArrayList<>();
        fillGridview();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview() {
        firebaseFirestore.collection(DATABASE.ITEMS.toString())
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                    products.add(document.toObject(Product.class));

                    ProductionsAdapter productionsAdapter = new ProductionsAdapter(this, products);
                    gridView = findViewById(R.id.list);
                    gridView.setAdapter(productionsAdapter);
                        });




    }



    private void edit(View view) {
        Product tag = (Product) view.getTag();
        Intent intent = new Intent(this,EditItem.class);
        intent.putExtra(SYSTEM.PRODUCT.toString(), tag);
        startActivity(intent);
    }


}

