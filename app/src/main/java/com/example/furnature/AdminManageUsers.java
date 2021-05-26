package com.example.furnature;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.furnature.adapters.UsersAdapter;
import com.example.furnature.general.DbCons;
import com.example.furnature.pojos.Order;
import com.example.furnature.pojos.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.furnature.pojos.constants.Status.Deleivering;

public class AdminManageUsers extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<User> usersList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_users);
        fillGridview();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillGridview() {
        usersList = new ArrayList<>();
        firebaseFirestore.collection(DbCons.Users.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            usersList.add(document.toObject(User.class));

                    UsersAdapter ordersAdapter = new UsersAdapter(this, R.layout.user_item, usersList
                            , this::Update,this::delete);
                    ListView listView = findViewById(R.id.list);
                    listView.setAdapter(ordersAdapter);
                });


    }

    private void Update(View view) {
        User user = (User) view.getTag();
        Intent intent = new Intent(this, AdminUpdateUser.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    private void delete(View view) {
        User user = (User) view.getTag();

        firebaseFirestore.collection(DbCons.Users.toString())
                .document(user.getUsername())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    startActivity(
                            new Intent(AdminManageUsers.this,AdminManageUsers.class)
                    );
                });
    }
}