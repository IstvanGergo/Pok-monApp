package com.example.pokemonapp.ui.TeamList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.database.PokemonDAO;
import com.example.pokemonapp.database.PokemonDB;
import com.example.pokemonapp.database.PokemonDatabase;
import com.example.pokemonapp.database.TeamDB;
import com.example.pokemonapp.database.TeamWithPokemons;
import com.example.pokemonapp.ui.PokemonList.PokemonListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeamListFragment extends Fragment {
    private View view;
    private TeamListAdapter adapter;
    private RecyclerView teamRecyclerView;
    private EditText newTeamName;
    private Button addNewTeam;
    private PokemonDAO dao;
    private List<TeamWithPokemons> teamList = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);
        teamRecyclerView = view.findViewById(R.id.teamRecyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        adapter = new TeamListAdapter(teamList);
        teamRecyclerView.setAdapter(adapter);
        teamRecyclerView.setLayoutManager(layoutManager);
        addNewTeam = view.findViewById(R.id.addNewTeam);
        newTeamName = view.findViewById(R.id.newTeamName);

        PokemonDatabase db = PokemonDatabase.getInstance(requireContext());
        dao = db.pokemonDAO();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<TeamWithPokemons> teams = PokemonDatabase.getInstance(requireContext())
                    .pokemonDAO()
                    .getAllTeams();
            requireActivity().runOnUiThread(() -> {
                teamList.clear();
                teamList.addAll(teams);
                adapter.notifyDataSetChanged();
            });
        });
        InitAddTeamButton();
        return view;
    }
    void InitAddTeamButton(){
        addNewTeam.setOnClickListener(view->{
            String name = newTeamName.getText().toString();
            TeamDB newTeam = new TeamDB();
            newTeam.TeamName = name;
            new Thread(()->{
                dao.insertTeam(newTeam);
            }).start();
        });
    }
}
