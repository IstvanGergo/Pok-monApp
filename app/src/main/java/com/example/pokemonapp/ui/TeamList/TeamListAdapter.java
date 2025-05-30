package com.example.pokemonapp.ui.TeamList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.database.PokemonDB;
import com.example.pokemonapp.database.TeamWithPokemons;

import java.util.List;

public class TeamListAdapter extends RecyclerView.Adapter<TeamHolder>{
    private List<TeamWithPokemons> teamList;
    public TeamListAdapter(List<TeamWithPokemons> _teamList){
        this.teamList = _teamList;
    }
    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_list_row, parent, false);
        return new TeamHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {
        TeamWithPokemons team = teamList.get(position);
        List<PokemonDB> pokemons = team.pokemons;
        PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemons);
        holder.teamView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),2));
        holder.teamView.setAdapter(pokemonAdapter);
        holder.teamView.setNestedScrollingEnabled(false);
        holder.teamName.setText(team.team.TeamName);
    }
    @Override
    public int getItemCount() {
        return teamList.size();
    }
}
