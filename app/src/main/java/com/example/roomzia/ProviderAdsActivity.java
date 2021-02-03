package com.example.roomzia;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ProviderAdsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references, references1;
    FirebaseAuth mAuth;
    BottomNavigationView bottomNav;

    private Button buttonKreirajOglas;
    RecyclerView recyclerViewOglasiPonude;
    RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CreateOfferClass> listaPomocna ;
    private ArrayList<AdsWithOfferClass> listaOglasaSaPonudama;
    AdapterForAdOfferClass adapterZaOglaseSaPonudama;
    String UID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davatelj_oglasa);

        bottomNav = findViewById(R.id.botomNavigationProvider);
        bottomNav.setSelectedItemId(R.id.aktivniOglasi);

         bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch(item.getItemId()){
                     case R.id.stariOglasi:
                         startActivity(new Intent(getApplicationContext(), OldAdsActivity.class));
                         overridePendingTransition(0,0);
                         return true;
                     case R.id.aktivniOglasi:
                         return true;
                 }
                 return false;
             }
         });

        buttonKreirajOglas = findViewById(R.id.buttonKreirajOglas);
        buttonKreirajOglas.setOnClickListener(this);

        recyclerViewOglasiPonude = findViewById(R.id.recycleViewOglasiPonude);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewOglasiPonude.setLayoutManager(mLayoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        references = firebaseDatabase.getReference("Oglasi");
        references1 = firebaseDatabase.getReference("Ponude");
        listaPomocna = new ArrayList<CreateOfferClass>();
        listaOglasaSaPonudama = new ArrayList<AdsWithOfferClass>();
        UID = mAuth.getInstance().getCurrentUser().getUid();
        listaOglasaSaPonudama.clear();
        references.orderByChild("datum").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOglasaSaPonudama.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String idOglasa = ds.getKey();
                    references1.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            listaOglasaSaPonudama.removeIf(t -> !t.getZauzece().equals("1"));
                            TextView txt =  findViewById(R.id.textViewPorukaDavatelj);
                            if (listaOglasaSaPonudama.isEmpty())
                            {
                                txt.setVisibility(View.VISIBLE);
                            }else
                            {
                                txt.setVisibility(View.INVISIBLE);
                            }
                            adapterZaOglaseSaPonudama = new AdapterForAdOfferClass(ProviderAdsActivity.this, listaOglasaSaPonudama);
                            //adapterZaOglaseSaPonudama.notifyDataSetChanged();
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
                Toast.makeText(ProviderAdsActivity.this, "Pogreška!",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonKreirajOglas:
                Intent intent = new Intent(ProviderAdsActivity.this, CreateAdActivity.class);
                startActivity(intent);
        }
    }
    public static Date toDate(String value) throws ParseException {
        DateFormat format = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
        return format.parse(value);
    }
}
