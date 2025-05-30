package com.example.pokemonapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"PokemonID", "TeamID"})

public class PokemonTeamJoin {
    @NonNull
    public Integer PokemonID;
    @NonNull
    public Integer TeamID;
}
