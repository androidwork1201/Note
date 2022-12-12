package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.blog.api.Api;
import com.example.blog.api.JsonObj;
import com.example.blog.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        Call<List<JsonObj>> call = api.getTodos();
        call.enqueue(new Callback<List<JsonObj>>() {
            @Override
            public void onResponse(Call<List<JsonObj>> call, Response<List<JsonObj>> response) {
                if (!response.isSuccessful()){
                    binding.textView.setText("Code: " + response.code());
                    return;
                }
                List<JsonObj> objs = response.body();
                for (JsonObj jsonObj: objs){
                    String context = "";
                    context += "User ID: " + jsonObj.getUserId() + "\n";
                    context += "ID: " + jsonObj.getId()+ "\n";
                    context += "Title: " + jsonObj.getTitle()+ "\n";
                    context += "text: " + jsonObj.isCompleted()+ "\n\n";

                    binding.textView.append(context);
                }
            }

            @Override
            public void onFailure(Call<List<JsonObj>> call, Throwable t) {
                binding.textView.setText(t.getMessage());
            }
        });

    }

}