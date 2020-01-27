package com.example.myapplication.view.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.PostsModel;
import com.example.myapplication.Repository.PostsRepository;

import java.util.List;

public class PostsViewModel extends AndroidViewModel {
    PostsRepository postsRepository;
    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsRepository = new PostsRepository();
    }

    public LiveData<List<PostsModel>> getPostsLiveData(){
        return postsRepository.getPostsLiveData();
    }
    public void getPostsFromAPI(int pageno){
        postsRepository.getPostsFromApi(pageno);
    }
}
