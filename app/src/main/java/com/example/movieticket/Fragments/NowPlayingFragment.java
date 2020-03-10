package com.example.movieticket.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movieticket.Adapters.NowPlayingMovieAdapter;
import com.example.movieticket.ApiClient;
import com.example.movieticket.Model.MovieResponse;
import com.example.movieticket.Model.NowPlaying;
import com.example.movieticket.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {

    NowPlayingMovieAdapter nowPlayingMovieAdapter;
    RecyclerView recyclerView;
    List<NowPlaying> nowPlayings;
    String apiKey = "731e8d4c0b6da8847392e850d9a11371";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing,container,false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));

        getMovieData(apiKey);

        return view;
    }

    public void getMovieData(String apiKeyz){
        Call<MovieResponse> call = ApiClient.getInstance().getApi().getPlayingMovies(apiKey);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    nowPlayings = response.body().getResults();
                    nowPlayingMovieAdapter = new NowPlayingMovieAdapter(getContext(),nowPlayings);
                    recyclerView.setAdapter(nowPlayingMovieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}







