package com.example.pokemonapp.data;

import com.example.pokemonapp.model.Type.AllTypes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TypeClient {
    @GET("type")
    Call<AllTypes> getAllTypes();
}
