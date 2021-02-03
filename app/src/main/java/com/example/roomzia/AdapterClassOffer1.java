package com.example.roomzia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterClassOffer1 extends RecyclerView.Adapter<AdapterClassOffer1.MyViewHolder> {

    Context context;
    ArrayList<CreateOfferClass> lOffer;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference references,references1;

    public AdapterClassOffer1(Context context, ArrayList<CreateOfferClass> lOffer) {
        this.context = context;
        this.lOffer = lOffer;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterClassOffer1.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.offer_row, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mPonudenaCijena.setText(lOffer.get(position).getnCijena()+ " kn");
        holder.mIme.setText(lOffer.get(position).getIme() +" "+ lOffer.get(position).getPrezime());
        holder.mKontakt.setText(lOffer.get(position).getKontakt());
        holder.onClick(position);
    }

    @Override
    public int getItemCount() {
        return  lOffer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mPonudenaCijena,mIme,mKontakt,txt;
        Button buttonPrihvatPonudu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mPonudenaCijena = (TextView) itemView.findViewById(R.id.textViewPonCijena);
            mIme = itemView.findViewById(R.id.textViewImeCis);
            mKontakt = itemView.findViewById(R.id.textViewKontaktCistaca);

            buttonPrihvatPonudu = itemView.findViewById(R.id.buttonPrihvatiPonudu);
        }
        public void onClick(int position){
            buttonPrihvatPonudu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogPotvrdiPonudu = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.dialog_potvrdi_ponudu, null);
                    TextView dialog_ime_Cistaca;
                    dialog_ime_Cistaca = dialogView.findViewById(R.id.textViewImeCistacauDialogu);
                    dialog_ime_Cistaca.setText(lOffer.get(position).getIme() + " " + lOffer.get(position).getPrezime());
                    dialogPotvrdiPonudu.setView(dialogView);
                    dialogPotvrdiPonudu.setNegativeButton("Zatvori", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialogPotvrdiPonudu.setPositiveButton("Prihvati", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            references = firebaseDatabase.getReference("Oglasi");
                            references1 = firebaseDatabase.getReference("Ponude");
                            references.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().child(lOffer.get(position).getIdOglasa()).child("zauzece").setValue("2");
                                    Intent intent = new Intent(context, ProviderAdsActivity.class);
                                    context.startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context, "Pogreška", Toast.LENGTH_LONG).show();
                                }
                            });
                            references1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().child(lOffer.get(position).getIdPonude()).child("zauzeto").setValue("2");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context, "Pogreška 2", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    dialogPotvrdiPonudu.show();
                }
            });
        }
    }
}
