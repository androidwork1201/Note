package com.example.blog.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("todos")
    Call<List<JsonObj>> getTodos();
}
