package com.example.pokemonapp.ui.TeamList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;

public class TeamHolder extends RecyclerView.ViewHolder {
    public TextView teamName;
    public RecyclerView teamView;
    public TeamHolder(@NonNull View itemView){
        super(itemView);
        teamName = itemView.findViewById(R.id.teamName);
        teamView = itemView.findViewById(R.id.teamView);
    }
}
