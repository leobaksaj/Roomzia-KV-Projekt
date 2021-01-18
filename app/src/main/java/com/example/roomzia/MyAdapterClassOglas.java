package com.example.roomzia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterClassOglas extends RecyclerView.Adapter<MyAdapterClassOglas.MyViewHolder> {

    Context context;
    ArrayList<OglasClass> lOglasi;

    public MyAdapterClassOglas(Context c, ArrayList<OglasClass> p){
        context = c;
        lOglasi = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mNaslov.setText(lOglasi.get(position).getNaslov());
        holder.mOpis.setText(lOglasi.get(position).getOpis());
        holder.mKvadratura.setText(lOglasi.get(position).getKvadratura());
        holder.mGrad.setText(lOglasi.get(position).getGrad());
        holder.mUlica.setText(lOglasi.get(position).getUlica());
        holder.mKontakt.setText(lOglasi.get(position).getKontakt());
        holder.mDatum.setText(lOglasi.get(position).getDatum());
        holder.mTime.setText(lOglasi.get(position).getVrijeme());
        holder.onClick(position);

    }

    @Override
    public int getItemCount() {
        return lOglasi.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mNaslov,mKvadratura,mOpis,mGrad,mUlica,mKontakt,mDatum, mTime,txt;
        Button buttonPrijavaZaOglas;
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
            buttonPrijavaZaOglas = itemView.findViewById(R.id.buttonPrijaviSeZaOglas);
            txt = itemView.findViewById(R.id.textViewporukaCistac);
        }
        public void onClick(int position ){
            buttonPrijavaZaOglas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreateOfferCleanerActivity.class);
                    intent.putExtra("naslov" , lOglasi.get(position).getNaslov());
                    intent.putExtra("opis", lOglasi.get(position).getOpis());
                    intent.putExtra("grad", lOglasi.get(position).getGrad());
                    intent.putExtra("ulica", lOglasi.get(position).getUlica());
                    intent.putExtra("kvadratura", lOglasi.get(position).getKvadratura());
                    intent.putExtra("vrijeme", lOglasi.get(position).getVrijeme());
                    intent.putExtra("datum", lOglasi.get(position).getDatum());
                    intent.putExtra("kontakt", lOglasi.get(position).getKontakt());
                    intent.putExtra("id", lOglasi.get(position).getIDOglasa());
                    intent.putExtra("providerID", lOglasi.get(position).getUserID());
                    context.startActivity(intent);
                }
            });
        }
    }
}
