package com.example.pokemonapp.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Type.AllTypes;
import com.example.pokemonapp.model.Type.BasicType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

public class utility {
    public static void processTypeResponse(Response<AllTypes> response, Spinner spinner, Context context) {
        if(!response.isSuccessful() || response.body() == null ){
            Log.w("Fetching types",
                    "Response code: " + response.code());
            return;
        }
        List<BasicType> types = new ArrayList<>(Collections.singletonList(new BasicType("None")));
        types.addAll(response.body().getResults());
        ArrayAdapter<BasicType> typeAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                types
        );
        spinner.setAdapter(typeAdapter);
    }
}
