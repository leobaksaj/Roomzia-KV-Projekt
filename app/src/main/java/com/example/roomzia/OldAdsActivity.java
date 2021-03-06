package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OldAdsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    BottomNavigationView bottomNav;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references, references1;

    public boolean data_changed = false;

    private ArrayList<CreateOfferClass> listaPomocna ;
    private ArrayList<AdsWithOfferClass> listaOglasaSaPonudama;
    RecyclerView recyclerViewOglasiPonude;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterForAdOfferClass adapterZaOglaseSaPonudama;
    FirebaseAuth mAuth;
    String UID ;
    public Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_ads);

        bottomNav = findViewById(R.id.botomNavigationProvider);
        bottomNav.setSelectedItemId(R.id.stariOglasi);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.stariOglasi:
                        return true;
                    case R.id.aktivniOglasi:
                        startActivity(new Intent(getApplicationContext(), ProviderAdsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerViewOglasiPonude = findViewById(R.id.recycleViewStariOglasi);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewOglasiPonude.setLayoutManager(mLayoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        references = firebaseDatabase.getReference("Oglasi");
        references1 = firebaseDatabase.getReference("Ponude");

        UID = mAuth.getInstance().getCurrentUser().getUid();
        listaPomocna = new ArrayList<CreateOfferClass>();
        listaOglasaSaPonudama = new ArrayList<AdsWithOfferClass>();
        listaOglasaSaPonudama.clear();
        references.orderByChild("datum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOglasaSaPonudama.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String idOglasa = ds.getKey();
                    references1.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int brojPonuda = 0;
                            for (DataSnapshot ds1 : snapshot.getChildren()) {
                                CreateOfferClass ponuda = ds1.getValue(CreateOfferClass.class);
                                listaPomocna.add(ponuda);
                                if (idOglasa.equals(ponuda.getIdOglasa())) {
                                    brojPonuda++;
                                }
                            }
                            String broj = String.valueOf(brojPonuda);
                            AdsWithOfferClass oglas = ds.getValue(AdsWithOfferClass.class);
                            oglas.setID(idOglasa);
                            oglas.setPonude(broj);
                            listaOglasaSaPonudama.add(oglas);
                            listaOglasaSaPonudama.removeIf(t -> !t.getUserID().equals(UID));
                            listaOglasaSaPonudama.removeIf(t -> !t.getZauzece().equals("2"));
                            TextView txt = findViewById(R.id.textViewPorukaDavateljStari);
                            if (listaOglasaSaPonudama.isEmpty()) {
                                txt.setVisibility(View.VISIBLE);
                            } else {
                                txt.setVisibility(View.INVISIBLE);
                            }
                            adapterZaOglaseSaPonudama = new AdapterForAdOfferClass(OldAdsActivity.this, listaOglasaSaPonudama);
                            recyclerViewOglasiPonude.setAdapter(adapterZaOglaseSaPonudama);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.logout, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.equals("Log Out"))
        {
            mAuth.signOut();
            Intent intent = new Intent(OldAdsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}