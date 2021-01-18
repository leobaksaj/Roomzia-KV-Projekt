package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mIme, mPrezime, mEmail, mPassword, nKontakt;
    private Button buttonRegistration;
    private CheckBox checkBox1, checkBox2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ///////////////////////////////////////////
        mIme = findViewById(R.id.textInputLayoutIme);
        mPrezime = findViewById(R.id.textInputLayoutPrezime);
        mEmail = findViewById(R.id.inputLayoutEmail);
        mPassword = findViewById(R.id.inputLayoutLozinka);
        nKontakt = findViewById(R.id.inputKontaktBroj);
        checkBox1 = findViewById(R.id.checkBoxTrebamciscenje);
        checkBox2 = findViewById(R.id.checkboxZelimCistiti);
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        buttonRegistration = findViewById(R.id.buttonRegistrirajKorisnika);
        buttonRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegistrirajKorisnika:
                registerUser();
                break;
            case R.id.checkBoxTrebamciscenje:
                setCheckBox1();
                break;
            case  R.id.checkboxZelimCistiti:
                setCheckBox2();
                break;
        }
    }
    private void  setCheckBox1() {
        checkBox2.setChecked(false);
    }

    private void setCheckBox2(){
        checkBox1.setChecked(false);
    }

    private void registerUser() {
        String imeInput = mIme.getEditText().getText().toString().trim();
        String prezimeInput = mPrezime.getEditText().getText().toString().trim();
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();
        String kontakt = nKontakt.getEditText().getText().toString().trim();
        Number Uloga =0;
        boolean ch1 = checkBox1.isChecked();
        boolean ch2 = checkBox2.isChecked();

        if (imeInput.isEmpty() ){
            mIme.setError("Popunite polje!");
            mIme.requestFocus();
            return;
        }
        if(prezimeInput.isEmpty()){
            mPrezime.setError("Popunite polje!");
            mPrezime.requestFocus();
            return;
        }
        if(email.isEmpty()){
            mEmail.setError("Popunite polje!");
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
        if(kontakt.isEmpty()){
            nKontakt.setError("Popunite polje!");
            nKontakt.requestFocus();
            return;
        }
        if(ch1 == false && ch2 == false){
            checkBox1.setError("Odaberite jednu opciju");
            checkBox1.requestFocus();
            return;
        }
        if(checkBox1.isChecked()){
            Uloga = 1;
        }
        if(checkBox2.isChecked()){
            Uloga = 2;
        }

        Number finalUloga = Uloga;
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UserHelperClass user = new UserHelperClass(imeInput, prezimeInput,kontakt, finalUloga, email);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "Uspješna registracija!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Neuspješna registracija, pokušajte ponovno!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Neuspješna registracija1, pokušajte ponovno!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}