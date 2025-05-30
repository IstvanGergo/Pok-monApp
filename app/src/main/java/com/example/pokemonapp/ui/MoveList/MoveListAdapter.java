package com.example.pokemonapp.ui.MoveList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Move.MoveDetails;
import com.example.pokemonapp.model.Pokemon.BasicPokemon;

import java.util.List;

public class MoveListAdapter extends RecyclerView.Adapter<MoveHolder>{
    private List<MoveDetails> moveList;

    public MoveListAdapter(List<MoveDetails> _moveList){
        this.moveList = _moveList;
    }
    @NonNull
    @Override
    public MoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.move_list_row, parent, false);
        return new MoveHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MoveHolder holder, int position) {
        MoveDetails move = moveList.get(position);
        List<BasicPokemon> pokemons = move.getCanBeLearntBy();
        PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemons);
        holder.canBeLearntByView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),2));
        holder.canBeLearntByView.setAdapter(pokemonAdapter);
        holder.canBeLearntByView.setNestedScrollingEnabled(false);
        holder.itemView.setOnClickListener(v->{
            if (holder.canBeLearntByView.getVisibility() == View.VISIBLE) {
                holder.canBeLearntByView.setVisibility(View.GONE);
            } else {
                holder.canBeLearntByView.setVisibility(View.VISIBLE);
            }
        });
        String accuracy;
        if(move.getAccuracy() != 0)
        {
            accuracy = Integer.toString(move.getAccuracy());
        }
        else{
            accuracy = "-";
        }
        String power;
        if(move.getPower() != 0)
        {
            power = Integer.toString(move.getPower());
        }
        else{
            power = "-";
        }
        holder.accuracyValue.setText(accuracy);
        String name = move.getName();
        String displayName =  name.substring(0,1).toUpperCase() + name.substring(1);
        displayName = displayName.replaceAll("-", " ");
        holder.moveName.setText(displayName);
        holder.ppValue.setText(Integer.toString(move.getPp()));

        holder.powerValue.setText(power);
        String typeName = move.getType().name;
        String displayType = typeName.substring(0,1).toUpperCase() + typeName.substring(1);
        holder.typeValue.setText(displayType);
    }
    @Override
    public int getItemCount() {
        return moveList.size();
    }
}
