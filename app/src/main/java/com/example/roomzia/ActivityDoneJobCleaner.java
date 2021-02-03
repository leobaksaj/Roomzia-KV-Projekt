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

public class ActivityDoneJobCleaner extends AppCompatActivity {
    BottomNavigationView bottomNav;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references,references1;
    FirebaseAuth mAuth;
    String UID;
    RecyclerView recyclerViewPrihvacenePonude;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterForShowMyOffer adapterClassPonude;
    ArrayList<ClassShowOffer> lMojePonude;
    ArrayList<OfferClass> lPonude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_job_cleaner);

        ///////////////////// PRISTUP BAZI
        firebaseDatabase = FirebaseDatabase.getInstance();
        references = firebaseDatabase.getReference("Oglasi");
        references1 = firebaseDatabase.getReference("Ponude");
        /////////////////////
        UID = mAuth.getInstance().getCurrentUser().getUid();
        lMojePonude = new ArrayList<ClassShowOffer>();

        recyclerViewPrihvacenePonude = findViewById(R.id.recycleViewDoneJob);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewPrihvacenePonude.setLayoutManager(mLayoutManager);

        references.orderByChild("datum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lMojePonude.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String idOglasa = ds.getKey();

                    references1.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds1 :snapshot.getChildren())
                            {
                                String idponude = ds1.getKey();
                                CreateOfferClass d = ds1.getValue(CreateOfferClass.class);
                                String zauzeto = d.getZauzeto();
                                ClassShowOffer p = ds.getValue(ClassShowOffer.class);
                                p.setIdPonude(idponude);
                                p.setIdOglasa(idOglasa);
                                p.setCijena(d.getnCijena());
                                p.setZauzeto(zauzeto);
                                if (d.getIdOglasa().equals(idOglasa) && d.getClearnerID().equals(UID)) {
                                    lMojePonude.add(p);
                                }
                            }
                            lMojePonude.removeIf(t -> !t.getZauzeto().equals("3"));
                            TextView txt = findViewById(R.id.textViewPorukaOdradeniPoslovi);
                            if (lMojePonude.isEmpty())
                            {
                                txt.setVisibility(View.VISIBLE);
                            }else
                            {
                                txt.setVisibility(View.INVISIBLE);
                            }
                            adapterClassPonude = new AdapterForShowMyOffer(lMojePonude, ActivityDoneJobCleaner.this);
                            recyclerViewPrihvacenePonude.setAdapter(adapterClassPonude);
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
        bottomNav = findViewById(R.id.bottomNavigationCleaner);
        bottomNav.setSelectedItemId(R.id.odradeniPoslovi);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mojePonude:
                        startActivity(new Intent(getApplicationContext(), ActivityMyOffer.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.aktivniOglasiCleaner:
                        startActivity(new Intent(getApplicationContext(), CleanerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.odradeniPoslovi:
                        return true;
                }
                return false;
            }
        });



    }
}