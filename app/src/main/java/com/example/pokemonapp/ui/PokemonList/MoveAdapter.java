package com.example.pokemonapp.ui.PokemonList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Pokemon.MoveEntry;
import com.example.pokemonapp.ui.MoveList.MoveHolder;

import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<MoveHolder> {
    List<MoveEntry> moveList;
    public MoveAdapter(List<MoveEntry> _moveList){
        this.moveList = _moveList;
    }
    @NonNull
    @Override
    public MoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basic_move_list_row, parent, false);
        return new MoveHolder(itemView);
    }
    @NonNull
    @Override
    public void onBindViewHolder(MoveHolder moveHolder, int position){
        moveHolder.moveName.setText(moveList.get(position).move.name);
    }
    @Override
    public int getItemCount() {
        return moveList.size();
    }
}
