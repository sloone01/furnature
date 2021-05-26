package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.example.furnature.general.DbCons;
import com.example.furnature.general.Helper;
import com.example.furnature.general.IntentCons;
import com.example.furnature.pojos.User;
import com.example.furnature.pojos.constants.Roles;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class Login extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.sup);
        TextView login = findViewById(R.id.sinnp);

        login.setOnClickListener(this::signIn);
        textView.setOnClickListener(v->startActivity(new Intent(this,Register.class)));

        TextView password = findViewById(R.id.pswd),username = findViewById(R.id.mal);
        username.setText("admin");
        password.setText("ssa");
    }

    private void signIn(View view) {

        TextView password = findViewById(R.id.pswd),username = findViewById(R.id.mal);

        String usernameText = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        firebaseFirestore.collection(DbCons.Users.toString())
                .whereEqualTo("username", username.getText().toString().trim())
                .whereEqualTo("password",password.getText().toString().trim())
                .get().addOnCompleteListener(task -> {
            User user = new User();
            int i = 0;
            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                user = snapshot.toObject(User.class);
                i++;
            }
            if (i == 0) {
                Helper.message(Login.this, "Wrong Credentials");
            } else {
                Helper.message(Login.this, "Loged in Succesfully");
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("username", user.getUsername());
                editor.apply();
                if (user.getRole().equals(Roles.Admin.toString()))
                    startActivity(new Intent(Login.this, AdminMenue.class));
                else
                    startActivity(new Intent(Login.this, UserMenu.class));
            }
        });



    }
}
