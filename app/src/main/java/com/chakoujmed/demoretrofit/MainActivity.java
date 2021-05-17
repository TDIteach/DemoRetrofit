package com.chakoujmed.demoretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    IRetrofitJsonPlaceHolder api;
    TextView tvErr;
    ListView lvPub;
    EditText etId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvErr=findViewById(R.id.tvError);
        lvPub=findViewById(R.id.lvPublications);
        etId=findViewById(R.id.etId);
        Retrofit retrofit=new  Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         api=retrofit.create(IRetrofitJsonPlaceHolder.class);
    }

    public void getAllPosts(View view) {
        Call<List<Publication>> requete=api.getAllPosts();
        requete.enqueue(new Callback<List<Publication>>() {
            @Override
            public void onResponse(Call<List<Publication>> call, Response<List<Publication>> response) {
              if(!response.isSuccessful()){
                  tvErr.setText(response.code());
                  return;
              }
              List<Publication> listePub=response.body();
              List<String> ls=new ArrayList<>();
              for (int i=0;i<listePub.size();i++){
                  ls.add(listePub.get(i).getTitle());
              }
              ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,ls);
              lvPub.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Publication>> call, Throwable t) {
             tvErr.setText(t.getMessage());
            }
        });

    }

    public void getPostById(View view) {
        int id=Integer.parseInt(etId.getText().toString());
        Call<Publication>  requete=api.getPostById(id);
        requete.enqueue(new Callback<Publication>() {
            @Override
            public void onResponse(Call<Publication> call, Response<Publication> response) {
                Publication pub=response.body();
                tvErr.setText("ID: "+pub.getId()+"\nTitle: "+pub.getTitle()+"\n Contenu: "+pub.getBody());
            }

            @Override
            public void onFailure(Call<Publication> call, Throwable t) {
                tvErr.setText(t.getMessage());
            }
        });
    }

    public void getPostByUserId(View view) {
        int id=Integer.parseInt(etId.getText().toString());
        Call<List<Publication>> requete=api.getPostsOfUser(id);
        requete.enqueue(new Callback<List<Publication>>() {
            @Override
            public void onResponse(Call<List<Publication>> call, Response<List<Publication>> response) {
                if(!response.isSuccessful()){
                    tvErr.setText(response.code());
                    return;
                }
                List<Publication> listePub=response.body();
                List<String> ls=new ArrayList<>();
                for (int i=0;i<listePub.size();i++){
                    ls.add("ID: "+listePub.get(i).getId()+"\n Title :"+listePub.get(i).getTitle());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,ls);
                lvPub.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Publication>> call, Throwable t) {
                tvErr.setText(t.getMessage());
            }
        });

    }
}