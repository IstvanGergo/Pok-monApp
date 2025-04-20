package com.example.pokemonapp.model.Pokemon;

import com.example.pokemonapp.model.Move.Move;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoveEntry {
    public Move move;
    @SerializedName("version_group_details")
    public List<VersionGroupDetail> versionGroupDetailsList;
}