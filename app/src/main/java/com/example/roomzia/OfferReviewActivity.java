package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OfferReviewActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references,references1;
    FirebaseAuth mAuth;
    private RecyclerView recyclerViewPonude;
    private ArrayList<CreateOfferClass> lPonude;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterClassOffer1 adapterClassPonude;

    TextView mNaslov,mKvadratura,mOpis,mGrad,mUlica,mKontakt,mDatum, mTime, mIDOglasa;
    String UID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_review);

        mNaslov = findViewById(R.id.textViewNaslov);
        mKvadratura = findViewById(R.id.textViewKvadratura);
        mOpis = findViewById(R.id.textViewOpis);
        mGrad = findViewById(R.id.textViewGrad);
        mUlica = findViewById(R.id.textViewUlica);
        mDatum = findViewById(R.id.textViewDatum);
        mTime = findViewById(R.id.textViewVrijeme);
        mKontakt = findViewById(R.id.textViewKontakt);
        mIDOglasa = findViewById(R.id.textViewIdOglasa);
        Bundle extras = getIntent().getExtras();

        mNaslov.setText(extras.getString("naslov"));
        mKvadratura.setText(extras.getString("kvadratura"));
        mOpis.setText(extras.getString("opis"));
        mGrad.setText(extras.getString("grad"));
        mUlica.setText(extras.getString("ulica"));
        //mKontakt.setText(extras.getString("kontakt"));
        mDatum.setText(extras.getString("datum"));
        mTime.setText(extras.getString("vrijeme"));
        mIDOglasa.setText(extras.getString("id"));

        String ogl = mIDOglasa.getText().toString();
        ///////////////////// PRISTUP BAZI
        firebaseDatabase = FirebaseDatabase.getInstance();
        references = firebaseDatabase.getReference("Oglasi");
        references1 = firebaseDatabase.getReference("Ponude");
        /////////////////////
        recyclerViewPonude = findViewById(R.id.recycleViewPonudeZaOglas);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewPonude.setLayoutManager(mLayoutManager);
        lPonude = new ArrayList<CreateOfferClass>();
        UID = mAuth.getInstance().getCurrentUser().getUid();

        references1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String idponude = ds.getKey();
                    CreateOfferClass ponuda = ds.getValue(CreateOfferClass.class);
                    ponuda.setIdPonude(idponude);
                    if (ponuda.getIdOglasa().equals(ogl))
                    {
                        lPonude.add(ponuda);
                    }
                    TextView txt = findViewById(R.id.textViewPorukaPonude);
                    if (lPonude.isEmpty())
                    {
                        txt.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        txt.setVisibility(View.INVISIBLE);
                    }
                    adapterClassPonude = new AdapterClassOffer1(OfferReviewActivity.this, lPonude);
                    recyclerViewPonude.setAdapter(adapterClassPonude);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}