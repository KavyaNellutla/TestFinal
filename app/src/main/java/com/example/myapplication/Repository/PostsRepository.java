package com.example.myapplication.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.PostsModel;
import com.example.myapplication.Retrofit.APIInterface;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository {
    private MutableLiveData<List<PostsModel>> mutableLiveData;
    private int lastpage=0;
    private APIInterface apiInterface;
    public PostsRepository(){
        apiInterface= RetrofitClient.getRetrofit().create(APIInterface.class);
    }
    public LiveData<List<PostsModel>> getPostsLiveData(){
        if(mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }
    public void  getPostsFromApi(final int pageno){
        if(lastpage>=pageno){
            apiInterface.getPostsFromAPI("story",pageno).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if(response.isSuccessful()){
                        List<PostsModel> postsModelList = new ArrayList<>();
                        if (response.body() != null) {
                            String title=""; String Createdat="";
                            lastpage = response.body().getAsJsonObject().get("nbPages").getAsInt();
                            JsonArray jsonArray = response.body().getAsJsonObject().get("hits").getAsJsonArray();
                            for (int i=0;i<jsonArray.size();i++){
                                title= jsonArray.get(i).getAsJsonObject().get("title").getAsString();
                                Createdat = jsonArray.get(i).getAsJsonObject().get("created_at").getAsString();
                                final PostsModel postsModel = new PostsModel();
                                postsModel.setTitle(title);
                                postsModel.setCreated_at(Createdat);
                                postsModel.setSwitchChecked(false);
                                postsModelList.add(postsModel);
                            }
                            mutableLiveData.postValue(postsModelList);
                        }

                    }

                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {

                }
            });
        }
    }
}
