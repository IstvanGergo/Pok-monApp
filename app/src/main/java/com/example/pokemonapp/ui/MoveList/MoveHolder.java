package com.example.pokemonapp.ui.MoveList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
public class MoveHolder extends RecyclerView.ViewHolder {
    public TextView moveName;
    public TextView accuracyValue;
    public TextView powerValue;
    public TextView typeValue;
    public TextView ppValue;
    public MoveHolder(@NonNull View itemView){
        super(itemView);
        moveName = itemView.findViewById(R.id.moveName);
        accuracyValue=itemView.findViewById(R.id.accuracyValue);
        powerValue=itemView.findViewById(R.id.powerValue);
        typeValue=itemView.findViewById(R.id.typeValue);
        ppValue=itemView.findViewById(R.id.ppValue);
    }
}
