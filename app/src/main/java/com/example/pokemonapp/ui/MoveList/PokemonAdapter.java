package com.example.pokemonapp.ui.MoveList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Pokemon.BasicPokemon;
import com.example.pokemonapp.ui.PokemonList.PokemonHolder;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonHolder>{

    List<BasicPokemon> pokemonList;
    public PokemonAdapter(List<BasicPokemon> _pokemonList){
        this.pokemonList = _pokemonList;
    }
    @NonNull
    @Override
    public PokemonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basic_pokemon_list_row, parent, false);
        return new PokemonHolder(itemView);
    }
    @Override
    public void onBindViewHolder(PokemonHolder pokemonHolder, int position){
        BasicPokemon pokemon = pokemonList.get(position);
        pokemonHolder.pokemonName.setText(pokemon.name);
    }
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
