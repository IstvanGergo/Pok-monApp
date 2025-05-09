package com.example.pokemonapp.ui.PokemonList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.pokemonapp.model.Pokemon.TypeDetail;
import com.example.pokemonapp.model.Type.TypePokemonSlot;

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
    private View view;
    private RecyclerView pokemonRecyclerView;
    private PokemonListAdapter adapter;
    private Retrofit retrofit;
    private PokemonClient pokemonClient;
    private TypeClient typeClient;
    private EditText searchByNameText;
    private Button searchByNameButton;
    private Button searchByTypeButton;
    private Spinner spinner;
    private List<Pokemon> pokemons = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        initializeView();
        initSearchButton();
        initSearchTypeButton();
        // Fill type Spinner with data
        Call<AllTypes> typesCall = typeClient.getAllTypes();
        typesCall.enqueue(new Callback<AllTypes>() {
            @Override
            public void onResponse(Call<AllTypes> call, Response<AllTypes> response) {
                processTypeResponse(response);
            }
            @Override
            public void onFailure(Call<AllTypes> call, Throwable t) {
                Log.e("Spinner", "Failed to load type list", t);
            }
        });
        // API call for pokemons
        Call<AllPokemons> call = pokemonClient.getAllPokemons();
        call.enqueue(new Callback<AllPokemons>() {
            @Override
            public void onResponse(Call<AllPokemons> call, Response<AllPokemons> response) {
                if (!response.isSuccessful()) {
                    Log.w("Get all pokemons",
                            "Response code: " + response.code());
                    return;
                }
                List<BasicPokemon>  basicList = response.body().getResults();
                for( BasicPokemon basic : basicList){
                    pokemonClient.getPokemonDetail(basic.url).enqueue(new Callback<Pokemon>() {
                        @Override
                        public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                            processPokemonResponse(response);
                            if(pokemons.size() == basicList.size()){
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
    public void initSearchButton(){
        searchByNameButton.setOnClickListener(view ->
        {
            String name = searchByNameText.getText().toString().toLowerCase().replaceAll(" ", "");
            Call<Pokemon> call = pokemonClient.getSpecificPokemon(name);
            call.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    if(response.isSuccessful()){
                    Log.w("Search By Name: ", name);
                    pokemons.clear();
                    processPokemonResponse(response);
                    adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Pokemon> call, Throwable t) {
                    Log.e("Search", "failed fetching pokemon", t);
                }
            });
        });
    }
    public void initSearchTypeButton(){
        searchByTypeButton.setOnClickListener(view->
        {
            BasicType type = (BasicType) spinner.getSelectedItem();
            Call<TypeDetail> call = pokemonClient.getPokemonsByType(type.getUrl());
            call.enqueue(new Callback<TypeDetail>() {
                @Override
                public void onResponse(Call<TypeDetail> call, Response<TypeDetail> response) {
                    if(!response.isSuccessful()){
                        Log.w("Fetch by type",
                                "Response code: " + response.code());
                        return;
                    }
                    pokemons.clear();
                    List<TypePokemonSlot> typePokemons = response.body().pokemon;
                    for(TypePokemonSlot pokemon : typePokemons ){
                        pokemonClient.getPokemonDetail(pokemon.pokemon.url).enqueue(new Callback<Pokemon>() {
                            @Override
                            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                processPokemonResponse(response);
                                if(pokemons.size() == typePokemons.size()){
                                    pokemons.sort(Comparator.comparingInt(Pokemon::getId));
                                    adapter.notifyDataSetChanged();
                                    pokemonRecyclerView.refreshDrawableState();
                                }
                            }
                            @Override
                            public void onFailure(Call<Pokemon> call, Throwable t) {
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<TypeDetail> call, Throwable t) {
                }
            });
        });
    }

    public void processTypeResponse(Response<AllTypes> response) {
        if(response.isSuccessful()){
            List<BasicType> types = new ArrayList<>(Collections.singletonList(new BasicType("None")));
            types.addAll(response.body().getResults());
            spinner = view.findViewById(R.id.searchByType);
            ArrayAdapter<BasicType> typeAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    types
            );
            spinner.setAdapter(typeAdapter);
        }
    }
    public void processPokemonResponse(Response<Pokemon> response){
        if(!response.isSuccessful())
        {
            Log.w("Fetch pokemon",
                    "Response code: " + response.code());
        }
        Pokemon newPokemon = response.body();
        pokemons.add(newPokemon);
    }
    public void initializeView(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pokemonClient = retrofit.create(PokemonClient.class);
        typeClient = retrofit.create(TypeClient.class);
        pokemonRecyclerView = view.findViewById(R.id.pokemonRecyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        adapter = new PokemonListAdapter(pokemons, this);
        pokemonRecyclerView.setAdapter(adapter);
        pokemonRecyclerView.setLayoutManager(layoutManager);
        searchByNameText = view.findViewById(R.id.searchByNameText);
        searchByNameButton = view.findViewById(R.id.searchByNameButton);
        searchByTypeButton = view.findViewById(R.id.searchByTypeButton);
    }
}
