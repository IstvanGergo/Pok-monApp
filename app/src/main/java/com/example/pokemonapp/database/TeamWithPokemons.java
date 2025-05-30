package com.example.pokemonapp.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TeamWithPokemons {
    @Embedded
    public TeamDB team;
    @Relation(
            parentColumn = "TeamID",
            entityColumn = "PokemonID",
            associateBy = @Junction(PokemonTeamJoin.class)
    )
    public List<PokemonDB> pokemons;
}
