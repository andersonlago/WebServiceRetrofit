package com.example.webserviceretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitService {

    @GET ("users/{login}")
    Call<GitResponse> getUsuario(@Path ("login") String login  );
}
