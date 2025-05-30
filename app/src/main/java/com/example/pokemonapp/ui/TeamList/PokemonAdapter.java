package com.example.pokemonapp.ui.TeamList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.R;
import com.example.pokemonapp.database.PokemonDB;
import com.example.pokemonapp.ui.PokemonList.PokemonHolder;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonHolder>{
    List<PokemonDB> pokemonList;
    public PokemonAdapter(List<PokemonDB> _pokemonList){
        this.pokemonList = _pokemonList;
    }
    @NonNull
    @Override
    public PokemonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_row, parent, false);
        return new PokemonHolder(itemView);
    }
    @Override
    public void onBindViewHolder(PokemonHolder pokemonHolder, int position){
        PokemonDB pokemon = pokemonList.get(position);
        pokemonHolder.pokemonName.setText(pokemon.name);
        pokemonHolder.healthValue.setText(pokemon.health);
        pokemonHolder.weightValue.setText(pokemon.weight);
        pokemonHolder.healthValue.setText(pokemon.height);
        pokemonHolder.attackValue.setText(pokemon.attack);
        pokemonHolder.defenseValue.setText(pokemon.defense);
        pokemonHolder.spAttackValue.setText(pokemon.spAttack);
        pokemonHolder.spDefenseValue.setText(pokemon.spDef);
        Glide.with(pokemonHolder.itemView)
                .load(pokemon.sprite)
                .into(pokemonHolder.sprite);
    }
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
