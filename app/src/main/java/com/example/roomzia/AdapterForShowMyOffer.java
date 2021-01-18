package com.example.roomzia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterForShowMyOffer extends RecyclerView.Adapter<AdapterForShowMyOffer.MyViewHolder> {

    ArrayList<ClassShowOffer> lMojePonude;
    Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references;

    public AdapterForShowMyOffer(ArrayList<ClassShowOffer> lMojePonude, Context context) {
        this.lMojePonude = lMojePonude;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterForShowMyOffer.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.showoffer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mNaslov.setText(lMojePonude.get(position).getNaslov());
        holder.mOpis.setText(lMojePonude.get(position).getOpis());
        holder.mKvadratura.setText(lMojePonude.get(position).getKvadratura());
        holder.mGrad.setText(lMojePonude.get(position).getGrad());
        holder.mUlica.setText(lMojePonude.get(position).getUlica());
        holder.mKontakt.setText(lMojePonude.get(position).getKontakt());
        holder.mDatum.setText(lMojePonude.get(position).getDatum());
        holder.mTime.setText(lMojePonude.get(position).getVrijeme());
        holder.mPonudenaCijena.setText(lMojePonude.get(position).getCijena());
        holder.mIDPonude.setText(lMojePonude.get(position).getIdPonude());
        if (lMojePonude.get(position).getZauzeto().equals("3"))
        {
            holder.buttonPregledPonuda.setVisibility(View.GONE);
        }else{
            holder.onClick(position);
        }
    }

    @Override
    public int getItemCount() {
        return lMojePonude.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNaslov,mKvadratura,mOpis,mGrad,mUlica,mKontakt,mDatum, mTime, mPonudenaCijena, mIDPonude,txt;
        Button buttonPregledPonuda;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNaslov = itemView.findViewById(R.id.textViewNaslov);
            mOpis = itemView.findViewById(R.id.textViewOpis);
            mKvadratura = itemView.findViewById(R.id.textViewKvadratura);
            mGrad = itemView.findViewById(R.id.textViewGrad);
            mUlica = itemView.findViewById(R.id.textViewUlica);
            mKontakt = itemView.findViewById(R.id.textViewKontakt);
            mDatum = itemView.findViewById(R.id.textViewDatum);
            mTime = itemView.findViewById(R.id.textViewVrijeme);
            mPonudenaCijena = itemView.findViewById(R.id.textViewPonudenaCijena);
            mIDPonude = itemView.findViewById(R.id.textViewidOglasa);

            buttonPregledPonuda = itemView.findViewById(R.id.buttonPotvrdiPonudu);
        }
        public void onClick(int position){
            buttonPregledPonuda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogZaOznaciOdradeno = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.dialog_oznaci_kao_odradeno, null);
                    dialogZaOznaciOdradeno.setView(dialogView);
                    dialogZaOznaciOdradeno.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firebaseDatabase = firebaseDatabase.getInstance();
                            references = firebaseDatabase.getReference("Ponude");
                            references.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().child(lMojePonude.get(position).getIdPonude()).child("zauzeto").setValue("3");
                                    Intent intent = new Intent(context,ActivityMyOffer.class );
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context, "Pogreška", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    dialogZaOznaciOdradeno.setNegativeButton("Zatvori", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialogZaOznaciOdradeno.show();
                }
            });

        }
    }
}
