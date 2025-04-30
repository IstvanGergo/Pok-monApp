package com.example.pokemonapp.ui.MoveList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.data.MoveClient;
import com.example.pokemonapp.model.Move.AllMoves;
import com.example.pokemonapp.model.Move.Move;
import com.example.pokemonapp.model.Move.MoveDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoveListFragment extends Fragment {
    private MoveClient moveClient;
    private Retrofit retrofit;
    private List<MoveDetails> moves = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        View view = inflater.inflate(R.layout.fragment_move_list, container, false);
        RecyclerView moveRecyclerView = view.findViewById(R.id.moveRecyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        MoveListAdapter adapter = new MoveListAdapter(moves, this);
        moveRecyclerView.setAdapter(adapter);
        moveRecyclerView.setLayoutManager(layoutManager);
        // API call
        moveClient = retrofit.create(MoveClient.class);
        Call<AllMoves> call = moveClient.getMoves(0,20);
        call.enqueue(new Callback<AllMoves>() {
            @Override
            public void onResponse(Call<AllMoves> call, Response<AllMoves> response) {
                if (!response.isSuccessful()) {
                    Log.w("MainActivity - onStart",
                            "Response code: " + response.code());
                    return;
                }
                List<Move> basicList = response.body().getResults();
                for( Move basicMove : basicList){
                    moveClient.getMoveDetail(basicMove.url).enqueue(new Callback<MoveDetails>() {
                        @Override
                        public void onResponse(Call<MoveDetails> call, Response<MoveDetails> response) {
                            if(!response.isSuccessful())
                            {
                                Log.w("Fetch move",
                                        "Response code: " + response.code());
                            }
                            MoveDetails move = response.body();
                            moves.add(move);
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

            @Override
            public void onFailure(Call<AllMoves> call, Throwable t) {
                Log.e("API", "Failed to load move list", t);
            }
        });
    return view;
    }

}
