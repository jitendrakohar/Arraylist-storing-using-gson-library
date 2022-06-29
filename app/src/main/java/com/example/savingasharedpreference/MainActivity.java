package com.example.savingasharedpreference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.savingasharedpreference.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<ModelClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        setListeners();
    }

    public void setListeners() {
        binding.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(binding.etName.getText().toString(), binding.etAge.getText().toString());
            }
        });

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("student_data", null);
        Type type = new TypeToken<ArrayList<ModelClass>>() {

        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            binding.tvSize.setText(" " + 0);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                binding.tvSize.setText(binding.tvSize.getText().toString() + "\n " + arrayList.get(i).getName() + "\n");
           binding.etAge.setText(null);
           binding.etName.setText(null);
            }
        }

    }

    public void saveData(String name, String age) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        arrayList.add(new ModelClass(name, Integer.parseInt(age)));
        String json = gson.toJson(arrayList);
        editor.putString("student_data", json);
        editor.apply();
        binding.tvSize.setText("List Data\n");
        loadData();
    }
}