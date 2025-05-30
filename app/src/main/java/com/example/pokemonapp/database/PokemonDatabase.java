package com.example.pokemonapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                PokemonDB.class, TeamDB.class, TypeDB.class,
                PokemonTeamJoin.class, PokemonTypeJoin.class
        },
        version = 1
)
public abstract class PokemonDatabase extends RoomDatabase {
    public abstract PokemonDAO pokemonDAO();
    private static volatile PokemonDatabase INSTANCE;

    public static PokemonDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PokemonDatabase.class,
                            "pokemon_database"
                    )
                            .createFromAsset("pokemon.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}