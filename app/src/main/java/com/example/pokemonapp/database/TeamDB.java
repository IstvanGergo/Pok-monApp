package com.example.pokemonapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class TeamDB {
    @PrimaryKey
    @NonNull
    public Integer TeamID;
    public String TeamName;
}
