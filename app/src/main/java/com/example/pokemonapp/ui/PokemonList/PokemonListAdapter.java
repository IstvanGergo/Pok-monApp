package com.example.pokemonapp.ui.PokemonList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Pokemon.Pokemon;
import com.example.pokemonapp.model.Pokemon.StatEntry;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonHolder> {
    private List<Pokemon> pokemonList;
    public PokemonListAdapter(List<Pokemon> _pokemonList){
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
    public void onBindViewHolder(@NonNull PokemonHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        MoveAdapter moveAdapter = new MoveAdapter(pokemon.getMoves());
        holder.pokemonMovesRecyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),2));
        holder.pokemonMovesRecyclerView.setAdapter(moveAdapter);
        holder.pokemonMovesRecyclerView.setNestedScrollingEnabled(false);
        holder.itemView.setOnClickListener(v->{
            if (holder.pokemonMovesRecyclerView.getVisibility() == View.VISIBLE) {
                holder.pokemonMovesRecyclerView.setVisibility(View.GONE);
            } else {
                holder.pokemonMovesRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        displayPokemonStats(holder,pokemon.getStats());
        Glide.with(holder.itemView)
                .load(pokemon.getSprites().frontSpriteUrl)
                .into(holder.sprite);
        String name = pokemon.getName();
        String nameDisplay = name.substring(0,1).toUpperCase() + name.substring(1, name.length());
        holder.pokemonName.setText(nameDisplay);
        String heightDisplay = ((double)pokemon.getHeight() / 10 )  + " m";
        holder.heightValue.setText(heightDisplay);
        String weightDisplay = ((double)pokemon.getWeight() / 10) + " kg";
        holder.weightValue.setText(weightDisplay);
    }
    public void displayPokemonStats(PokemonHolder holder, List<StatEntry> stats){
        for(StatEntry entry : stats){
            switch(entry.stat.name){
                case "hp":
                    String health = Integer.toString(entry.base_stat);
                    holder.healthValue.setText(health);
                    break;
                case "defense":
                    String defense = Integer.toString(entry.base_stat);
                    holder.defenseValue.setText(defense);
                    break;
                case "attack":
                    holder.attackValue.setText(Integer.toString(entry.base_stat));
                    break;
                case "special-attack":
                    holder.spAttackValue.setText(Integer.toString(entry.base_stat));
                    break;
                case "special-defense":
                    holder.spDefenseValue.setText(Integer.toString(entry.base_stat));
                    break;
                case "speed":
                    holder.speedValue.setText(Integer.toString(entry.base_stat));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
