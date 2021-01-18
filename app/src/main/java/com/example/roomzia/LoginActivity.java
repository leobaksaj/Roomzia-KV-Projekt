package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mEmail, mPassword;
    private Button buttonLogin;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseUser user;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.buttonLoginKorisnika);
        buttonLogin.setOnClickListener(this);

        mEmail = findViewById(R.id.inputLayoutEmail);
        mPassword = findViewById(R.id.inputLayoutLozinka);

        mAuth = FirebaseAuth.getInstance();
        }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLoginKorisnika:
                userLogin();
                break;
        }
    }
    private void userLogin() {
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        if(email.isEmpty()){
            mEmail.setError("Prazno polje!");
            mEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Neispravna email adresa!");
            mEmail.requestFocus();
            return;
        }
        if(password.isEmpty() && password.length() < 6 ){
            mPassword.setError("Prazno polje ili prekratka lozinka!");
            mPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    userID = user.getUid();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String uloga = snapshot.child(userID).child("uloga").getValue().toString();
                            if(uloga.matches("1")){
                            Intent intent = new Intent(LoginActivity.this, ProviderAdsActivity.class);
                            startActivity(intent);
                            }
                            if (uloga.matches(String.valueOf(2))) {
                                Intent intent = new Intent(LoginActivity.this, CleanerActivity.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Naispravni podaci pokušajte ponovno", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}