package com.example.pokemonapp.ui.MoveList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.data.MoveClient;
import com.example.pokemonapp.data.TypeClient;
import com.example.pokemonapp.model.Move.AllMoves;
import com.example.pokemonapp.model.Move.Move;
import com.example.pokemonapp.model.Move.MoveDetails;
import com.example.pokemonapp.model.Type.AllTypes;
import com.example.pokemonapp.model.Type.BasicType;
import com.example.pokemonapp.model.Move.TypeDetail;
import com.example.pokemonapp.ui.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MoveListFragment extends Fragment {
    private View view;
    private RecyclerView moveRecyclerView;
    private MoveListAdapter adapter;
    private MoveClient moveClient;
    private TypeClient typeClient;
    private EditText searchByNameText;
    private Button searchByNameButton;
    private Button searchByTypeButton;
    private Spinner spinner;
    private List<MoveDetails> moves = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_move_list, container,false);
        initializeView();
        initSearchButton();
        initSearchTypeButton();
        Call<AllTypes> typesCall = typeClient.getAllTypes();
        typesCall.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<AllTypes> call, Response<AllTypes> response) {
                utility.processTypeResponse(response, spinner, requireContext());
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<AllTypes> call, Throwable t) {
                Log.e("Spinner", "Failed to load type list", t);
            }
        });
        // API call
        Call<AllMoves> call = moveClient.getMoves(0,20);
        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<AllMoves> call, Response<AllMoves> response) {
                fetchMoveDetails(response);
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<AllMoves> call, Throwable t) {
                Log.e("API", "Failed to load move list", t);
            }
        });
    return view;
    }
    public void initSearchButton(){
        searchByNameButton.setOnClickListener(view ->
        {
            String name = searchByNameText.getText().toString().toLowerCase().replaceAll(" ", "");
            Call<MoveDetails> call = moveClient.getSpecificMove(name);
            call.enqueue(new Callback<>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<MoveDetails> call, Response<MoveDetails> response) {
                    if(!response.isSuccessful() || response.body().getName() == null){
                        Log.w("Fetch by name",
                                "Response code: " + response.code());
                        return;
                    }
                    moves.clear();
                    MoveDetails newMove = response.body();
                    moves.add(newMove);
                    adapter.notifyDataSetChanged();
                }
                @Override
                @EverythingIsNonNull
                public void onFailure(Call<MoveDetails> call, Throwable t) {
                    Log.e("Search", "failed fetching pokemon", t);
                }
            });
        });
    }
    public void initSearchTypeButton(){
        searchByTypeButton.setOnClickListener(view->
        {
            BasicType type = (BasicType) spinner.getSelectedItem();
            Call<TypeDetail> call = moveClient.getMovesByType(type.getUrl());
            call.enqueue(new Callback<>() {
                @Override
                @EverythingIsNonNull
                public void onResponse( Call<TypeDetail> call, Response<TypeDetail> response ) {
                    if( !response.isSuccessful() || response.body() == null ){
                        Log.w("Fetch by type",
                                "Response code: " + response.code());
                        return;
                    }
                    moves.clear();
                    List<Move> typePokemons = response.body().moves;
                    for(Move move : typePokemons ){
                        moveClient.getMoveDetail(move.url).enqueue(new Callback<>() {
                            @Override
                            @EverythingIsNonNull
                            public void onResponse( Call<MoveDetails> call, Response<MoveDetails> response ) {
                                if(!response.isSuccessful() || response.body() == null){
                                    Log.w("Fetch by type",
                                            "Response code: " + response.code());
                                    return;
                                }
                                MoveDetails newMove = response.body();
                                moves.add(newMove);
                                if(moves.size() == typePokemons.size()){
                                    moves.sort(Comparator.comparingInt(MoveDetails::getId));
                                    adapter.notifyDataSetChanged();
                                    moveRecyclerView.refreshDrawableState();
                                }
                            }
                            @Override
                            public void onFailure( Call<MoveDetails> call, Throwable t ) {
                            }
                        });
                    }
                }
                @Override
                @EverythingIsNonNull
                public void onFailure(Call<TypeDetail> call, Throwable t) {
                }
            });
        });
    }
    public void fetchMoveDetails(Response<AllMoves> response){
        if (!response.isSuccessful() || response.body() == null) {
            Log.w("Get all moves",
                    "Response code: " + response.code());
            return;
        }
        List<Move> basicList = response.body().getResults();
        for( Move basicMove : basicList){
            moveClient.getMoveDetail(basicMove.url).enqueue(new Callback<>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<MoveDetails> call, Response<MoveDetails> response) {
                    if(!response.isSuccessful() || response.body() == null){
                        Log.w("Fetch details",
                                "Response code: " + response.code());
                        return;
                    }
                    MoveDetails newMove = response.body();
                    moves.add(newMove);
                    if(moves.size() == basicList.size()){
                        moves.sort(Comparator.comparingInt(MoveDetails::getId));
                        adapter.notifyItemRangeInserted(0, moves.size());
                    }
                }
                @Override
                public void onFailure(Call<MoveDetails> call, Throwable t) {
                    Log.e("Move", "Failed to load " + basicMove.name, t);
                }
            });
        }
    }
    public void initializeView(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moveClient = retrofit.create(MoveClient.class);
        typeClient = retrofit.create(TypeClient.class);
        moveRecyclerView = view.findViewById(R.id.moveRecyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        adapter = new MoveListAdapter(moves);
        spinner = view.findViewById(R.id.searchByType);
        moveRecyclerView.setAdapter(adapter);
        moveRecyclerView.setLayoutManager(layoutManager);
        searchByNameText = view.findViewById(R.id.searchByNameText);
        searchByNameButton = view.findViewById(R.id.searchByNameButton);
        searchByTypeButton = view.findViewById(R.id.searchByTypeButton);
    }
}
