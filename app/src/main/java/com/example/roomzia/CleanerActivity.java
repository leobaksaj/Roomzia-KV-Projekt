package com.example.roomzia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CleanerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references, references1;
    FirebaseAuth mAuth;

    private RecyclerView recyclerViewOglasi;
    private ArrayList<OglasClass> lOglasi;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapterClassOglas adapterClassOglas;
    String UID;
    String idPonudaOglas;
    String idUseraUponudi;
    BottomNavigationView bottomNav;
    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cistac);

        /////////////////////////////////////////
        bottomNav = findViewById(R.id.bottomNavigationCleaner);
        bottomNav.setSelectedItemId(R.id.aktivniOglasiCleaner);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mojePonude:
                        startActivity(new Intent(getApplicationContext(), ActivityMyOffer.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.aktivniOglasiCleaner:
                        return true;
                    case R.id.odradeniPoslovi:
                        startActivity(new Intent(getApplicationContext(), ActivityDoneJobCleaner.class));
                        overridePendingTransition(0, 0);
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

        ///////////////////// PRISTUP BAZI
        firebaseDatabase = FirebaseDatabase.getInstance();
        references = firebaseDatabase.getReference("Oglasi");
        references1 = firebaseDatabase.getReference("Ponude");
        /////////////////////
        recyclerViewOglasi = findViewById(R.id.recycleViewOglasi);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewOglasi.setLayoutManager(mLayoutManager);
        lOglasi = new ArrayList<OglasClass>();
        UID = mAuth.getInstance().getCurrentUser().getUid();
        lOglasi.clear();
        references.orderByChild("datum").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lOglasi.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String idOGlasa = ds.getKey();
                    OglasClass p = ds.getValue(OglasClass.class);
                    p.setIDOglasa(idOGlasa);
                    lOglasi.add(p);
                    lOglasi.removeIf(t -> t.getZauzece().equals("2"));
                    TextView txt = findViewById(R.id.textViewporukaCistac);
                    if (lOglasi.isEmpty()) {
                        txt.setVisibility(View.VISIBLE);
                    } else {
                        txt.setVisibility(View.INVISIBLE);
                    }
                    references1.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                CreateOfferClass offrClas = ds.getValue(CreateOfferClass.class);
                                idPonudaOglas = offrClas.getIdOglasa();
                                idUseraUponudi = offrClas.getClearnerID();
                                if (idUseraUponudi.equals(UID)) {
                                    lOglasi.removeIf(t -> t.getIDOglasa().equals(idPonudaOglas));
                                }
                            }
                            adapterClassOglas = new MyAdapterClassOglas(CleanerActivity.this, lOglasi);
                            recyclerViewOglasi.setAdapter(adapterClassOglas);
                            adapterClassOglas.notifyDataSetChanged();
                            TextView txt = findViewById(R.id.textViewporukaCistac);
                            if (lOglasi.isEmpty())
                            {
                                txt.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                txt.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CleanerActivity.this, "Pogreška!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.equals("Log Out"))
        {
            mAuth.signOut();
            Intent intent = new Intent(CleanerActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}