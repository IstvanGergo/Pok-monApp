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
import java.util.List;

public class PokemonListFragment extends Fragment {
    private PokemonClient pokemonClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        RecyclerView pokemonRecyclerView = view.findViewById(R.id.pokemonRecyclerView);
        // API call
        pokemonClient = retrofit.create(PokemonClient.class);
        Call<AllPokemons> call = pokemonClient.getAllPokemons();
        List<Pokemon> pokemons = new ArrayList<>();
        PokemonListAdapter adapter = new PokemonListAdapter(pokemons, this);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        pokemonRecyclerView.setAdapter(adapter);
        pokemonRecyclerView.setLayoutManager(layoutManager);
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
                            Log.w("Fetching pokemon",
                                    "pokken: "+ basic.name);
                            Pokemon newPokemon = response.body();

                            Log.w("Pokemons health",
                                    "height: " + response.body().getHeight());
                            requireActivity().runOnUiThread(() -> {
                                pokemons.add(newPokemon);
                                adapter.notifyItemInserted(pokemons.size() - 1);
                            });
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
