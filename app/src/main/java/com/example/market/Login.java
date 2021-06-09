package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.market.general.DATABASE;
import com.example.market.pojos.User;
import com.example.market.pojos.constants.Roles;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.signup);
        Button login = findViewById(R.id.signin);

        login.setOnClickListener(this::signIn);
        textView.setOnClickListener(v->startActivity(new Intent(this,Register.class)));

        EditText password = findViewById(R.id.password),username = findViewById(R.id.email);
        username.setText("omani");
        password.setText("omani123");
    }

    private void signIn(View view) {

        EditText password = findViewById(R.id.password),username = findViewById(R.id.email);

        String usernameText = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        firebaseFirestore.collection(DATABASE.USERS.toString())
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
                Toast.makeText(this,"Please Enter Correct Username/password",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show();
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
