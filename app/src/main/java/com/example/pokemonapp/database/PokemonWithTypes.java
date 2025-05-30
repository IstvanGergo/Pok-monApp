package com.example.pokemonapp.database;

import androidx.room.Junction;
import androidx.room.Relation;
import androidx.room.Embedded;

import java.util.List;

public class PokemonWithTypes {
    @Embedded
    public PokemonDB team;
    @Relation(
            parentColumn = "PokemonID",
            entityColumn = "TypeID",
            associateBy = @Junction(PokemonTypeJoin.class)
    )
    public List<TypeDB> types;
}
