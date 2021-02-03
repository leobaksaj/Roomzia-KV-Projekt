package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.renderscript.ScriptIntrinsicYuvToRGB;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ActivityMyOffer extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    BottomNavigationView bottomNav;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references,references1;
    FirebaseAuth mAuth;
    String UID;
    RecyclerView recyclerViewPrihvacenePonude;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterForShowMyOffer adapterClassPonude;
    public ArrayList<ClassShowOffer> lMojePonude;
    ArrayList<OfferClass> lPonude;
    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offer);
        /////////////////////////////////////////
        bottomNav = findViewById(R.id.bottomNavigationCleaner);
        bottomNav.setSelectedItemId(R.id.mojePonude);
        ///////////////////// PRISTUP BAZI
        firebaseDatabase = FirebaseDatabase.getInstance();
        references = firebaseDatabase.getReference("Oglasi");
        references1 = firebaseDatabase.getReference("Ponude");
        /////////////////////
        UID = mAuth.getInstance().getCurrentUser().getUid();
        lMojePonude = new ArrayList<ClassShowOffer>();

        recyclerViewPrihvacenePonude = findViewById(R.id.recycleViewPrihvacenePonude);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewPrihvacenePonude.setLayoutManager(mLayoutManager);
        lMojePonude.clear();
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
                                CreateOfferClass d = ds1.getValue(CreateOfferClass.class);
                                String cijena = d.getnCijena();
                                String idponude = ds1.getKey();
                                ClassShowOffer p = ds.getValue(ClassShowOffer.class);
                                p.setIdOglasa(idOglasa);
                                p.setCijena(cijena);
                                p.setZauzeto(d.getZauzeto());
                                p.setIdPonude(idponude);
                                if (d.getIdOglasa().equals(idOglasa) && d.getClearnerID().equals(UID) && d.getZauzeto().equals("2")) {
                                    lMojePonude.add(p);
                                }
                            }
                            TextView txt = findViewById(R.id.textViewPorukaPrihvacenePonude);
                            if (lMojePonude.isEmpty())
                            {
                                txt.setVisibility(View.VISIBLE);
                            }else
                            {
                                txt.setVisibility(View.INVISIBLE);
                            }
                            adapterClassPonude = new AdapterForShowMyOffer(lMojePonude, ActivityMyOffer.this);
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


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mojePonude:
                        return true;
                    case R.id.aktivniOglasiCleaner:
                        startActivity(new Intent(getApplicationContext(), CleanerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.odradeniPoslovi:
                    startActivity(new Intent(getApplicationContext(), ActivityDoneJobCleaner.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
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
            Intent intent = new Intent(ActivityMyOffer.this, MainActivity.class);
            startActivity(intent);
        }
        else{

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}