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
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OldAdsActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references, references1;

    private ArrayList<CreateOfferClass> listaPomocna ;
    private ArrayList<AdsWithOfferClass> listaOglasaSaPonudama;
    RecyclerView recyclerViewOglasiPonude;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterForAdOfferClass adapterZaOglaseSaPonudama;
    FirebaseAuth mAuth;
    String UID ;
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

        references.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String idOglasa = ds.getKey();
                    references1.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int brojPonuda = 0;
                            for (DataSnapshot ds1 : snapshot.getChildren())
                            {
                                CreateOfferClass ponuda = ds1.getValue(CreateOfferClass.class);
                                listaPomocna.add(ponuda);
                                if (idOglasa.equals(ponuda.getIdOglasa()))
                                {
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
                            TextView txt =  findViewById(R.id.textViewPorukaDavateljStari);
                            if (listaOglasaSaPonudama.isEmpty())
                            {
                                txt.setVisibility(View.VISIBLE);
                            }else
                            {
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
        TextView txt = findViewById(R.id.textViewPorukaDavateljStari);
        if (listaOglasaSaPonudama.isEmpty())
        {
            txt.setVisibility(View.VISIBLE);
        }


    }
}