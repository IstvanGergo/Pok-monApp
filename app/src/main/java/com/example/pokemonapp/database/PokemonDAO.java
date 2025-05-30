package com.example.pokemonapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
@Dao
public interface PokemonDAO {
    @Transaction
    @Query("Select * FROM TeamDB")
    List<TeamWithPokemons> getAllTeams();
    @Query("Select * FROM TeamDB WHERE TeamID = (:teamid)")
    TeamDB getByID(int[] teamid);
    @Insert
    void insertPokemon(PokemonDB pokemon);

    @Insert
    void insertTeam(TeamDB team);

    @Insert
    void insertType(TypeDB type);

    @Insert
    void insertPokemonTeamCrossRef(PokemonTeamJoin ref);

    @Insert
    void insertPokemonTypeCrossRef(PokemonTypeJoin ref);
    @Delete
    void deleteTeam(TeamDB team);
    @Transaction
    @Query("Select * From TeamDB WHERE TeamID = (:teamid)")
    TeamWithPokemons getTeamWithPokemons(int teamid);
    @Transaction
    @Query("Select * From PokemonDB WHERE PokemonID = (:pokemonid)")
    PokemonWithTypes getPokemonTypes(int pokemonid);
}
