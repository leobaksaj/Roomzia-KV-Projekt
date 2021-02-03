package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateOfferCleanerActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    TextView mNaslov,mKvadratura,mOpis,mGrad,mUlica,mKontakt,mDatum, mTime,mID,providerID;
    EditText mCijena;
    Button butonKreirajPonudu;
    public String UID;
    public FirebaseUser Current;
    public String ime;
    public String prezime;
    public String kontakt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiraj_ponudu_cistac);

        mNaslov = findViewById(R.id.textViewNaslov);
        mKvadratura = findViewById(R.id.textViewKvadratura);
        mOpis = findViewById(R.id.textViewOpis);
        mGrad = findViewById(R.id.textViewGrad);
        mUlica = findViewById(R.id.textViewUlica);
        mDatum = findViewById(R.id.textViewDatum);
        mTime = findViewById(R.id.textViewVrijeme);
        mKontakt = findViewById(R.id.textViewKontakt);
        mID = findViewById(R.id.textVieID);
        providerID = findViewById(R.id.textViewProviderID);
        mCijena = findViewById(R.id.editTextCijena);
        Bundle extras = getIntent().getExtras();

        mNaslov.setText(extras.getString("naslov"));
        mKvadratura.setText(extras.getString("kvadratura"));
        mOpis.setText(extras.getString("opis"));
        mGrad.setText(extras.getString("grad"));
        mUlica.setText(extras.getString("ulica"));
        mKontakt.setText(extras.getString("kontakt"));
        mDatum.setText(extras.getString("datum"));
        mTime.setText(extras.getString("vrijeme"));
        mID.setText(extras.getString("id"));
        providerID.setText(extras.getString("providerID"));
        butonKreirajPonudu = findViewById(R.id.buttonPošaljiPonudu);
        butonKreirajPonudu.setOnClickListener(this);

        UID = mAuth.getInstance().getCurrentUser().getUid();
        Current = mAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference  = firebaseDatabase.getReference("Users");

        reference.orderByKey().equalTo(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ime = (String) ds.child("ime").getValue();
                    prezime = (String) ds.child("prezime").getValue();
                    kontakt = (String) ds.child("kontakt").getValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPošaljiPonudu:
                spremiOglas();
                break;
        }
    }

    private void spremiOglas() {
        String cijena = mCijena.getText().toString();
        String idOglasa = mID.getText().toString();
        String idCistaca = providerID.getText().toString();
        if (cijena.isEmpty() ){
            mCijena.setError("Obavezno polje!");
            mCijena.requestFocus();
            return;
        }
        CreateOfferClass ponuda = new CreateOfferClass(idOglasa,cijena,idCistaca , ime, prezime, UID, "1",kontakt, "1" );
        FirebaseDatabase.getInstance().getReference().child("Ponude").push().setValue(ponuda);
        Intent intent = new Intent(CreateOfferCleanerActivity.this, CleanerActivity.class);
        startActivity(intent);
    }
}