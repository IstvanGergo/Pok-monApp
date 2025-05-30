package com.example.pokemonapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TypeDB {
    @NonNull
    @PrimaryKey
    public Integer TypeID;
    public String Name;
}
