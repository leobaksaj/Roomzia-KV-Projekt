package com.example.roomzia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateAdActivity extends AppCompatActivity{

    private EditText mDatum;
    private EditText mTime;
    TimePickerDialog timePickerDialog;
    private Button spremiOglasButton;
    private  EditText mNaslov,mKvadratura,mOpis,mGrad,mUlica,mKontakt;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiraj_oglas);

        mDatum = findViewById(R.id.editTextDatum);
        Calendar date = Calendar.getInstance();
        int godina = date.get(Calendar.YEAR);
        int mjesec = date.get(Calendar.MONTH);
        int dan = date.get(Calendar.DAY_OF_MONTH);

        mDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        CreateAdActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date =  dayOfMonth + "/" + month + "/" + year;
                        mDatum.setText(date);
                    }
                },godina,mjesec,dan);
                dialog.show();
            }
        });

        mTime = findViewById(R.id.editTextVrijeme);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(CreateAdActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mTime.setText(hourOfDay + ":" + minute);
                                }
                            },0,0,true);
                            timePickerDialog.show();
            }
        });

        mNaslov = findViewById(R.id.editTextNaslov);
        mKvadratura = findViewById(R.id.editTextKvadratura);
        mOpis = findViewById(R.id.editTextOpis);
        mGrad = findViewById(R.id.editTextGrad);
        mUlica = findViewById(R.id.editTextUlica);
        mKontakt = findViewById(R.id.editTextKontakt);
        spremiOglasButton = findViewById(R.id.buttonObjaviOglas);
        spremiOglasButton.setOnClickListener(this::onClick);

        reference = FirebaseDatabase.getInstance().getReference("Oglasi");
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonObjaviOglas:
                spremiOglas();
                break;
        }
    }

    private void spremiOglas() {
        String naslov = mNaslov.getText().toString();
        String kvadratura = mKvadratura.getText().toString();
        String opis = mOpis.getText().toString();
        String grad = mGrad.getText().toString();
        String ulica = mUlica.getText().toString();
        String kontakt = mKontakt.getText().toString();
        String datum = mDatum.getText().toString();
        String vrijeme = mTime.getText().toString();
        String zauzece = "1";
        String UID = mAuth.getInstance().getCurrentUser().getUid();

        if (naslov.isEmpty() ){
            mNaslov.setError("Popunite polje!");
            mNaslov.requestFocus();
            return;
        }
        if(kvadratura.isEmpty()){
            mKvadratura.setError("Popunite polje!");
            mKvadratura.requestFocus();
            return;
        }
        if(opis.isEmpty()){
            mOpis.setError("Popunite polje!");
            mOpis.requestFocus();
            return;
        }
        if(grad.isEmpty()){
            mGrad.setError("Popunite polje!");
            mGrad.requestFocus();
            return;
        }
        if(ulica.isEmpty()){
            mUlica.setError("Popunite polje!");
            mUlica.requestFocus();
            return;
        }
        if(datum.isEmpty()){
            mDatum.setError("Popunite polje!");
            mDatum.requestFocus();
            return;
        }
       if(vrijeme.isEmpty()){
           mTime.setError("Popunite polje!");
           mTime.requestFocus();
           return;
       }
        if(kontakt.isEmpty()){
            mKontakt.setError("Popunite polje!");
            mKontakt.requestFocus();
            return;
        }

        OglasClass oglas = new OglasClass("2", naslov,opis,grad,ulica,kontakt,kvadratura +"m2",datum,vrijeme,zauzece,UID);
        FirebaseDatabase.getInstance().getReference().child("Oglasi").push().setValue(oglas);
        Intent intent = new Intent(CreateAdActivity.this, ProviderAdsActivity.class);
        startActivity(intent);
    }
}