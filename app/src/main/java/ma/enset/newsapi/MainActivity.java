package ma.enset.newsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ma.enset.newsapi.models.ListNewsAdapter;
import ma.enset.newsapi.models.News;
import ma.enset.newsapi.models.NewsResponse;
import ma.enset.newsapi.service.RestfulResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String APIKEY = "b8a9c2d82082472a86b46b5541d58546";
        ListView newsList = findViewById(R.id.newsList);
        List<News> newsData = new ArrayList<>();
        ListNewsAdapter adapter = new ListNewsAdapter(this,R.layout.list_news_item,newsData);
        newsList.setAdapter(adapter);

        EditText search = findViewById(R.id.search);
        EditText searchDate = findViewById(R.id.date);
        Button searchButton = findViewById(R.id.lookfor);
        Retrofit newsApi = new Retrofit.Builder().baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RestfulResponse serviceAPI = newsApi.create(RestfulResponse.class);

        //handle search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hold the button disabled
                searchButton.setEnabled(false);

                String key = search.getText().toString();
                String date = searchDate.getText().toString();
                Call<NewsResponse> callNews = serviceAPI.listNewsByKey(key,date,APIKEY);
                callNews.enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if(response.body()!=null) {
                            NewsResponse newsResponses = response.body();
                            newsData.clear();
                            for (News news : newsResponses.getArticles()) {
                                newsData.add(news);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        searchButton.setEnabled(true);
                    }


                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        System.out.println("--------------err----------------");
                        Log.e("error","Erreur de réseau");
                        newsData.clear();
                        adapter.notifyDataSetChanged();
                        searchButton.setEnabled(true);
                    }
                });
            }
        });

        //Click on each item
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),NewsActivity.class);
                intent.putExtra("news", newsData.get(i));
                startActivity(intent);
            }
        });
        //Typo and search button
        searchButton.setEnabled(false);
        //format date
        searchDate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String[] date = searchDate.getText().toString().split("-");
                if(date.length == 1){
                    if(searchDate.getText().length() == 4){
                        searchDate.append("-");
                    }
                }
                if(date.length == 2){
                    if(searchDate.getText().length() == 7){
                        searchDate.append("-");
                    }
                }
                if(!search.getText().toString().matches("") && !searchDate.getText().toString().matches("") && searchDate.getText().toString().length() == 10){
                    searchButton.setEnabled(true);
                }else{
                    searchButton.setEnabled(false);
                }
                return false;
            }
        });
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(!search.getText().toString().matches("") && !searchDate.getText().toString().matches("") && searchDate.getText().toString().length()==10){
                    searchButton.setEnabled(true);
                }else{
                    searchButton.setEnabled(false);
                }
                return false;
            }
        });



    }
}