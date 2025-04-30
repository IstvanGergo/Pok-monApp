package com.example.pokemonapp.model.Move;

import java.util.List;

import com.example.pokemonapp.model.Pokemon.Pokemon;
import com.example.pokemonapp.model.Type;
import com.google.gson.annotations.SerializedName;
public class MoveDetails {
    private Integer id;
    private Integer accuracy;
    private Integer power;
    private Integer pp;
    private String name;
    @SerializedName("learned_by_pokemon")
    private List<Pokemon> canBeLearntBy;
    private Type type;
    @SerializedName("damage_class")
    private DamageClass damageClass;
    // statChanges can be null
    @SerializedName("stat_changes")
    private List<StatChange> statChanges;

    public Integer getId() { return id; }
    public Type getType() { return type; }
    public DamageClass getDamageClass() { return damageClass; }
    public List<StatChange> getStatChanges() { return statChanges; }
    public List<Pokemon> getCanBeLearntBy() { return canBeLearntBy; }
    public String getName() { return name; }
    public Integer getPp() { return pp; }
    public Integer getPower() {
        if(power == null)
        {
            return 0;
        }
        return power; }
    public Integer getAccuracy() {
        if(accuracy == null)
        {
            return 0;
        }
        return accuracy; }
}
