package com.example.pokemonapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PokemonDB {
    @NonNull
    @PrimaryKey
    public int PokemonID;
    public String name;
    public Integer health;
    public String sprite;
    public Integer weight;
    public Integer height;
    public Integer attack;
    public Integer defense;
    public Integer spAttack;
    public Integer spDef;
}
