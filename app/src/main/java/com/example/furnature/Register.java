package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furnature.general.DATABASE;
import com.example.furnature.pojos.User;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.furnature.pojos.constants.Roles.Admin;
import static com.example.furnature.pojos.constants.Roles.Customer;
import static com.example.furnature.pojos.constants.Status.Approved;

public class Register extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView textView = findViewById(R.id.sin),signup = findViewById(R.id.act);
        textView.setOnClickListener(v->startActivity(new Intent(this,Login.class)));
        signup.setOnClickListener(this::signUp);
    }

    private void signUp(View view) {
        EditText username = findViewById(R.id.usrusr),name = findViewById(R.id.fullname),
                email = findViewById(R.id.mal),password = findViewById(R.id.pswd),
                password2 = findViewById(R.id.repswd),phone = findViewById(R.id.phone),
                address = findViewById(R.id.address);

        User user = new User();
        user.setFullname(name.getText().toString());
        user.setUsername(username.getText().toString());
        user.setEmail(email.getText().toString().trim());
        user.setPhone(Long.parseLong(phone.getText().toString().trim()));
        user.setPassword(password.getText().toString().trim());
        user.setAddress(address.getText().toString().trim());
        user.setRole(getRole(name.getText().toString()));
        user.setAdmin(Approved.toString());
        firebaseFirestore
                .collection(DATABASE.USERS.toString())
                .document(user.getUsername())
                .set(user)
                .addOnSuccessListener(avoid->{
                    Toast.makeText(Register.this,"Registerd",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this,Login.class));

                });


        startActivity(new Intent(this,Login.class));
    }

    private String getRole(String s) {
        return s.contains("admin") ? Admin.toString() : Customer.toString();
    }
}
