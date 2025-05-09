package com.example.pokemonapp.ui.PokemonList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;

public class PokemonMoveHolder extends RecyclerView.ViewHolder {
    public TextView moveName;
    public PokemonMoveHolder(@NonNull View itemView){
        super(itemView);
        moveName = itemView.findViewById(R.id.moveName);
    }
}
