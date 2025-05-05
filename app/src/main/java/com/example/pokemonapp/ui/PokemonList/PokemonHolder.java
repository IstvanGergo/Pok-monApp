package com.example.pokemonapp.ui.PokemonList;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
public class PokemonHolder extends RecyclerView.ViewHolder {
    public TextView pokemonName;
    public TextView healthValue;
    public TextView attackValue;
    public TextView defenseValue;
    public TextView heightValue;
    public TextView spAttackValue;
    public TextView spDefenseValue;
    public TextView speedValue;
    public TextView weightValue;
    public ImageView sprite;
    public RecyclerView pokemonMovesRecyclerView;
    public PokemonHolder(@NonNull View itemView) {
        super(itemView);
        pokemonName = itemView.findViewById(R.id.pokemonName);
        healthValue = itemView.findViewById(R.id.healthValue);
        attackValue = itemView.findViewById(R.id.attackValue);
        defenseValue = itemView.findViewById(R.id.defenseValue);
        heightValue = itemView.findViewById(R.id.heightValue);
        spAttackValue = itemView.findViewById(R.id.spAttackValue);
        spDefenseValue = itemView.findViewById(R.id.spDefenseValue);
        speedValue = itemView.findViewById(R.id.speedValue);
        weightValue = itemView.findViewById(R.id.weightValue);
        sprite = itemView.findViewById(R.id.pokemonSprite);
        pokemonMovesRecyclerView = itemView.findViewById(R.id.pokemonMovesRecyclerView);
    }
}