package com.chakoujmed.demoretrofit;



import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofitJsonPlaceHolder {
    @GET("posts")
    Call<List<Publication>> getAllPosts();
    @GET("posts/{id}")
    Call<Publication> getPostById(@Path("id") int postId);
    @GET("posts")
    Call<List<Publication>> getPostsOfUser(@Query("userId") int userId);

}
