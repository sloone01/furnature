package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminUpdateUser extends AppCompatActivity {

    private EditText username;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText address;
    private User user;
    private TextView signup;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user);
        ini();
        fill();
    }

    private void fill() {
        username.setText(user.getUsername());
        name.setText(user.getFullname());
        email.setText(user.getEmail());
        phone.setText(user.getPhone()+"");
        address.setText(user.getAddress());


    }

    private void ini() {
        user = (User) getIntent().getSerializableExtra("user");
        this.username = findViewById(R.id.usrusr);
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.mal);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        signup = findViewById(R.id.act);
        signup.setOnClickListener(this::Update);
    }

    private void Update(View view) {
        updateUser();
        firebaseFirestore.collection(DATABASE.USERS.toString())
                .document(user.getUsername())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    startActivity(
                            new Intent(AdminUpdateUser.this,AdminManageUsers.class)
                    );

                });

    }

    private void updateUser() {
        user.setFullname(name.getText().toString().trim());
        user.setEmail(email.getText().toString().trim());
        user.setPhone(Long.parseLong(phone.getText().toString().trim()));
        user.setAddress(address.getText().toString().trim());
    }
}
