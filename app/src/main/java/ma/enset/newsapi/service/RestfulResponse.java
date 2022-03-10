package ma.enset.newsapi.service;

import ma.enset.newsapi.models.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestfulResponse {
    @GET("/v2/everything")
    Call<NewsResponse> listNewsByKey(@Query("q") String key,@Query("from") String date,@Query("apiKey") String apikey);

}
