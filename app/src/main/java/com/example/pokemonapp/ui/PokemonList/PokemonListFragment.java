package com.example.pokemonapp.ui.PokemonList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.data.PokemonClient;
import com.example.pokemonapp.data.TypeClient;
import com.example.pokemonapp.model.Pokemon.AllPokemons;
import com.example.pokemonapp.model.Pokemon.BasicPokemon;
import com.example.pokemonapp.model.Pokemon.Pokemon;
import com.example.pokemonapp.model.Type.AllTypes;
import com.example.pokemonapp.model.Type.BasicType;
import com.example.pokemonapp.model.Type.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PokemonListFragment extends Fragment {
    private Retrofit retrofit;
    private PokemonClient pokemonClient;
    private TypeClient typeClient;
    PokemonListAdapter adapter;
    private List<BasicPokemon> basicList= new ArrayList<>();
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
        adapter = new PokemonListAdapter(pokemons, this);
        pokemonRecyclerView.setAdapter(adapter);
        pokemonRecyclerView.setLayoutManager(layoutManager);

        typeClient = retrofit.create(TypeClient.class);
        Call<AllTypes> typesCall = typeClient.getAllTypes();
        typesCall.enqueue(new Callback<AllTypes>() {
            @Override
            public void onResponse(Call<AllTypes> call, Response<AllTypes> response) {
                if(response.isSuccessful()){
                    List<BasicType> types= response.body().getResults();
                    Spinner spinner = view.findViewById(R.id.searchPokemonByType);
                    ArrayAdapter<BasicType> typeAdapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            types
                    );
                    spinner.setAdapter(typeAdapter);
                }
            }
            @Override
            public void onFailure(Call<AllTypes> call, Throwable t) {
                Log.e("Spinner", "Failed to load type list", t);
            }
        });

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
                basicList = response.body().getResults();
                for( BasicPokemon basic : basicList){
                    pokemonClient.getPokemonDetail(basic.url).enqueue(new Callback<Pokemon>() {
                        @Override
                        public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                            processPokemonResponse(response);
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
    public void processPokemonResponse(Response<Pokemon> response){
        if(!response.isSuccessful())
        {
            Log.w("Fetch pokemon",
                    "Response code: " + response.code());
        }
        Pokemon newPokemon = response.body();
        pokemons.add(newPokemon);
        if(pokemons.size() == basicList.size()){
            pokemons.sort(Comparator.comparingInt(Pokemon::getId));
            adapter.notifyItemRangeInserted(0, pokemons.size());
        }
    }
}
