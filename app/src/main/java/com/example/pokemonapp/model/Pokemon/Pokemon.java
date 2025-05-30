package com.example.pokemonapp.model.Pokemon;

import java.util.List;

public class Pokemon {
    private Integer id;
    private String name;
    private List<MoveEntry> moves;
    private Sprites sprites;
    private List<StatEntry> stats;
    private int weight;
    private int height;
    private List<TypeEntry> types;
    public Integer getId(){ return id; }
    public String getName(){
        return name;
    }
    public List<MoveEntry> getMoves(){
        return moves;
    }
    public Sprites getSprites(){
        return sprites;
    }
    public List<StatEntry> getStats() {
        return stats;
    }
    public int getWeight(){
        return weight;
    }
    public int getHeight(){
        return height;
    }
    public List<TypeEntry> getTypes(){
        return types;
    }

}
