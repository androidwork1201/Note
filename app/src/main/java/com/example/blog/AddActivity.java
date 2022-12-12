package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.blog.databasehelper.DataBaseHelper;
import com.example.blog.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        addLibrary();
    }

    private void addLibrary() {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(AddActivity.this);
                dataBaseHelper.addData(
                        binding.editTitle.getText().toString().trim(),
                        binding.editAnchor.getText().toString().trim(),
                        Integer.parseInt(binding.editPages.getText().toString().trim()));
                finish();
            }
        });
    }


}