package com.example.pokemonapp.ui.TeamList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.pokemonapp.R;
import com.example.pokemonapp.database.PokemonDAO;
import com.example.pokemonapp.database.PokemonDB;
import com.example.pokemonapp.database.PokemonDatabase;
import com.example.pokemonapp.database.TeamDB;
import com.example.pokemonapp.database.TeamWithPokemons;

import java.util.List;

public class TeamListFragment extends Fragment {
    private View view;
    private EditText newTeamName;
    private Button addNewTeam;
    private PokemonDAO dao;
    private List<TeamWithPokemons> teamList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);
        addNewTeam = view.findViewById(R.id.addNewTeam);
        newTeamName = view.findViewById(R.id.newTeamName);
        PokemonDatabase db = PokemonDatabase.getInstance(requireContext());
        dao = db.pokemonDAO();
        new Thread(() -> {
            List<TeamWithPokemons> teams = dao.getAllTeams();
            for (TeamWithPokemons team : teams) {
                Log.d("Team", "Team: " + team.team.TeamName);
                for (PokemonDB pokemon : team.pokemons) {
                    Log.d("Team", "- " + pokemon.name);
                }
            }
        }).start();

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
