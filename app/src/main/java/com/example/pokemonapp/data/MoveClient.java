package com.example.pokemonapp.data;

import com.example.pokemonapp.model.Move.AllMoves;
import com.example.pokemonapp.model.Move.MoveDetails;
import com.example.pokemonapp.model.Move.TypeDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MoveClient {
    @GET("move/?limit=100000&offset=0.")
    Call<AllMoves> getAllMoves();
    @GET("move/")
    Call<AllMoves> getMoves(@Query("offset") int offset, @Query("limit") int limit);
    @GET
    Call<MoveDetails> getMoveDetail(@Url String url);
    @GET("move/{name}")
    Call<MoveDetails> getSpecificMove(@Path("name") String moveName);
    @GET
    Call<TypeDetail> getMovesByType(@Url String url);
}
