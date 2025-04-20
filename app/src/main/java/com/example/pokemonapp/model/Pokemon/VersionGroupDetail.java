package com.example.pokemonapp.model.Pokemon;

import com.google.gson.annotations.SerializedName;

public class VersionGroupDetail {
    public int level_learned_at;
    @SerializedName("move_learn_method")
    public MoveLearnMethod moveLearnMethod;
    @SerializedName("version_group")
    public VersionGroup versionGroup;
}