package com.example.pokemonapp.data;

import com.example.pokemonapp.model.Pokemon.AllPokemons;
import com.example.pokemonapp.model.Pokemon.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokemonClient {
    @GET("pokemon")
    Call<AllPokemons> getAllPokemons();
    @GET("pokemon/")
    Call<AllPokemons> getPokemons(@Query("offset") int offset, @Query("limit") int limit);
    @GET
    Call<Pokemon> getPokemonDetail(@Url String url);
    @GET("pokemon/")
    Call<MoveClient> getSpecificPokemon(@Query("") String pokemonName);
}
