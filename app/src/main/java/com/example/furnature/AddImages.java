package com.example.furnature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.furnature.adapters.ManageImagesAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.pojos.FItem;
import com.example.furnature.pojos.Image;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.rpc.Help;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static com.example.furnature.general.IntentCons.Item;

public class AddImages extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ArrayList<Image> gridArray = new ArrayList<>();
    FirebaseStorage storage;
    StorageReference storageReference;
    private Bitmap bitmap;
    private ImageView imageView;
    private int index;
    private Uri path;
    private FItem furnature;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Intent intent = getIntent();
        furnature = (FItem) intent.getSerializableExtra(Item.toString());

        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());
        gridArray.add(new Image());

        GridView gridView = (GridView) findViewById(R.id.gridView1);
        ManageImagesAdapter adapter = new ManageImagesAdapter(this, R.layout.images_grid,
                gridArray, 0,0, this::storeValue,this::storeValue);
        gridView.setAdapter(adapter);

        TextView save = findViewById(R.id.add);
        save.setOnClickListener(this::saveImages);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveImages(View view) {

        Intent intent = getIntent();
        long id  = (long) intent.getLongExtra("fitem",0);

        gridArray.forEach(file -> {
            if (Objects.nonNull(file.getUri())){
                storageReference.child("images/"+file.getRef()).putFile(file.getUri())
                        .addOnSuccessListener(taskSnapshot ->{

                            Helper.message(AddImages.this,"Image Uploded");
                        } ).addOnFailureListener(exceptiom->{
                    Helper.message(AddImages.this,exceptiom.getMessage());
                });
                furnature.getImages().add(file.getRef());
            }

        });

        firebaseFirestore.collection(DbCons.Furnatures.toString())
                .document(furnature.getId())
                .set(furnature)
                .addOnSuccessListener(aVoid -> {
                        startActivity(new Intent(AddImages.this, ManageItems.class));
                        Helper.message(AddImages.this,"Added Succefully");

                });


    }

    private void storeValue(View view) {

        index = (int) view.getTag(R.id.act);
        imageView = (ImageView) view.getTag(R.id.fullname);


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        path = data.getData();
        try {

            imageView.setImageURI(path);
            gridArray.get(index).setUri(path);
            gridArray.get(index).setRef(UUID.randomUUID().toString().substring(0,5));
            //bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
