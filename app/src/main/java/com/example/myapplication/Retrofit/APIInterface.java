package com.example.myapplication.Retrofit;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET(APIConstants.API_POSTS_URL)
    Call<JsonElement> getPostsFromAPI(@Query("tags") String tags,@Query("page")int Pageno);
}
