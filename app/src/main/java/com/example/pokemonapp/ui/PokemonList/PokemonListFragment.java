package com.example.pokemonapp.ui.PokemonList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.data.PokemonClient;
import com.example.pokemonapp.model.Pokemon.AllPokemons;
import com.example.pokemonapp.model.Pokemon.BasicPokemon;
import com.example.pokemonapp.model.Pokemon.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokemonListFragment extends Fragment {
    private Retrofit retrofit;
    private PokemonClient pokemonClient;
    private List<Pokemon> pokemons = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        RecyclerView pokemonRecyclerView = view.findViewById(R.id.pokemonRecyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        PokemonListAdapter adapter = new PokemonListAdapter(pokemons, this);
        pokemonRecyclerView.setAdapter(adapter);
        pokemonRecyclerView.setLayoutManager(layoutManager);
        // API call
        pokemonClient = retrofit.create(PokemonClient.class);
        Call<AllPokemons> call = pokemonClient.getAllPokemons();
        call.enqueue(new Callback<AllPokemons>() {
            @Override
            public void onResponse(Call<AllPokemons> call, Response<AllPokemons> response) {
                if (!response.isSuccessful()) {
                    Log.w("MainActivity - onStart",
                            "Response code: " + response.code());
                    return;
                }
                List<BasicPokemon> basicList = response.body().getResults();
                for( BasicPokemon basic : basicList){
                    pokemonClient.getPokemonDetail(basic.url).enqueue(new Callback<Pokemon>() {
                        @Override
                        public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                            if(!response.isSuccessful())
                            {
                                Log.w("Fetch pokemon",
                                        "Response code: " + response.code());
                            }
                            Pokemon newPokemon = response.body();
                            pokemons.add(newPokemon);
                            if(pokemons.size() == basicList.size()){
                                // TODO: somehow the last pokemon is missing from the display..
                                pokemons.sort(Comparator.comparingInt(Pokemon::getId));
                                adapter.notifyItemRangeInserted(0, pokemons.size());
                            }
                        }
                        @Override
                        public void onFailure(Call<Pokemon> call, Throwable t) {
                            Log.e("POKEMON", "Failed to load " + basic.name, t);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<AllPokemons> call, Throwable t) {
                Log.e("API", "Failed to load pokémon list", t);
            }
        });

        return view;
    }

}
