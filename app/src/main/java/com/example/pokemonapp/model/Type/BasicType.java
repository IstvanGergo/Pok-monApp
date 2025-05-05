package com.example.pokemonapp.model.Type;

import androidx.annotation.NonNull;

public class BasicType {
    private String name;
    private String url;

    public BasicType(String _name) {
        this.name= _name;
    }
    public String getUrl() {
        return url;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
