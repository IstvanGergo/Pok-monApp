package com.example.pokemonapp.model.Pokemon;

import com.example.pokemonapp.model.Move.Move;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {
    public String name;
    public List<MoveEntry> moves;
    public Sprites sprites;
    public List<StatEntry> stats;
    public int weight;
    public int height;
    public List<TypeEntry> types;

    public static class MoveEntry {
        public Move move;
        @SerializedName("version_group_details")
        public List<VersionGroupDetail> versionGroupDetailsList;
    }

    public static class VersionGroupDetail {
        public int level_learned_at;
        @SerializedName("move_learn_method")
        public MoveLearnMethod moveLearnMethod;
        @SerializedName("version_group")
        public VersionGroup versionGroup;
    }

    public static class MoveLearnMethod {
        public String name;
        public String url;
    }

    public static class VersionGroup {
        public String name;
        public String url;
    }

    public static class Sprites {
        @SerializedName("front_default")
        public String frontSprite;
    }

}
