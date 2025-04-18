package com.example.pokemonapp.model.Move;

import java.util.List;

import com.example.pokemonapp.model.Pokemon.Pokemon;
import com.example.pokemonapp.model.Type;
import com.google.gson.annotations.SerializedName;
public class MoveDetails {
    public Integer accuracy;
    public Integer power;
    public Integer pp;
    public String name;
    @SerializedName("learned_by_pokemon")
    public List<Pokemon> canBeLearntBy;
    public Type type;
    @SerializedName("damage_class")
    public DamageClass damageClass;

    // statChanges can be null
    @SerializedName("stat_changes")
    public StatChange statChanges;
}
