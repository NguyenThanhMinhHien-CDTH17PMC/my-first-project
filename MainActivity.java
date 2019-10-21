package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final LinkedList<String> mWordList=new LinkedList<>();
    private final String SHARED_PREFERENCES_NAME = "SHARED PREFERENCES";
    private final String TEN = "Word";
    private RecyclerView mRecyclerView;
    private  WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=1;i<20;i++){
            mWordList.addLast("Word "+i);
        }
        Load(mRecyclerView);

        Spinner spinner=findViewById(R.id.spinnerKM);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.labels_array,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(spinner!=null){
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
        }

        mRecyclerView=findViewById(R.id.recyclerview);

        mAdapter=new WordListAdapter(mWordList,this);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String s=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void Searchbyname(View view){
        LinkedList<String> result=new LinkedList<>();
        int count=mWordList.size();
        EditText et=(EditText)findViewById(R.id.editText2);
        for(int i=0;i<count;i++){
            if(mWordList.get(i).indexOf(et.getText().toString())>=0){
                result.add(mWordList.get(i));
            }
        }
        mAdapter=new WordListAdapter(result,this);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void Save( View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEN,"Word Save Save");
        editor.apply();
    }
    public void Load(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        mWordList.addLast(sharedPreferences.getString(TEN,"Word 0"));
    }
    /*public void Save1()
    {
        String fileName = "INTERNAL STORAGE";
        String content = "Data data data";
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}