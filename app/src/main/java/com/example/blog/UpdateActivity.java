package com.example.blog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.blog.databasehelper.DataBaseHelper;
import com.example.blog.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    String id, title, anchor, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(UpdateActivity.this);
                title = binding.editTitleUp.getText().toString().trim();
                anchor = binding.editAnchorUp.getText().toString().trim();
                pages = binding.editPagesUp.getText().toString().trim();
                dataBaseHelper.updateData(id, title, anchor, pages);
                finish();
            }
        });
        binding.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog();
            }
        });

    }
    public void getIntentData(){
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("anchor") &&
                getIntent().hasExtra("pages") ){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            anchor = getIntent().getStringExtra("anchor");
            pages = getIntent().getStringExtra("pages");

            binding.editTitleUp.setText(title);
            binding.editAnchorUp.setText(anchor);
            binding.editPagesUp.setText(pages);

        }else Toast.makeText(this, "No Data..", Toast.LENGTH_SHORT).show();
    }
    public void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("DELETE " + title + " ?");
                builder.setMessage("確認按下'確定'");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(UpdateActivity.this);
                        dataBaseHelper.deleteData(id);
                        finish();
                    }
                });
                builder.create().show();
    }
}