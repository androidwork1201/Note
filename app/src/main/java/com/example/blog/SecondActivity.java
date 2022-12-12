package com.example.blog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.blog.adapter.CustomAdapter;
import com.example.blog.databasehelper.DataBaseHelper;
import com.example.blog.databinding.ActivitySecondBinding;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;
    private DataBaseHelper dataBaseHelper;
    ArrayList<String> id, basicTitle, basicAnchor, basicPages;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        fabClick();
    }

    private void fabClick() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        dataBaseHelper = new DataBaseHelper(SecondActivity.this);
        id = new ArrayList<>();
        basicTitle = new ArrayList<>();
        basicAnchor = new ArrayList<>();
        basicPages = new ArrayList<>();
        storeDataInArray();

        adapter = new CustomAdapter(SecondActivity.this,SecondActivity.this,
                id, basicTitle, basicAnchor, basicPages);
        binding.recycle.setAdapter(adapter);
        binding.recycle.setHasFixedSize(true);
        binding.recycle.setLayoutManager(new LinearLayoutManager(SecondActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor = dataBaseHelper.readAllData();
        if (cursor.getCount() == 0){
            binding.emptyImageView.setVisibility(View.VISIBLE);
            binding.noDataTxt.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No data..", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                basicTitle.add(cursor.getString(1));
                basicAnchor.add(cursor.getString(2));
                basicPages.add(cursor.getString(3));
            }
            binding.emptyImageView.setVisibility(View.GONE);
            binding.noDataTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            ConfirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    public void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE All ?");
        builder.setMessage("確認按下'確定'");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(SecondActivity.this);
                dataBaseHelper.deleteAllData();
                Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.create().show();
    }
}