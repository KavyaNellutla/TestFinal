package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Adapter.PostsAdapter;
import com.example.myapplication.view.Model.PostsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView selectedcounts;
    private int lastPage = 0;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostsViewModel postsViewModel;
    private List<PostsModel> postsModelList;
    private PostsAdapter postsAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        postsViewModel= ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPostsFromAPI(lastPage);
        postsModelList= new ArrayList<>();
        getAdapater();
        getRecycler();
        addOnScroll();
        getObservableLivedata();
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                postsModelList.clear();
                postsViewModel.getPostsFromAPI(lastPage);
                selectedcounts.setText("0");
            }
        });
    }

    private void addOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    progressBar.setVisibility(View.VISIBLE);
                    postsViewModel.getPostsFromAPI(lastPage++);
                }
            }
        });
    }

    private void getObservableLivedata() {
        postsViewModel.getPostsLiveData().observe(this, new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModels) {
                progressBar.setVisibility(View.GONE);
                postsModelList.addAll(postsModels);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getRecycler() {
        DividerItemDecoration decoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(postsAdapter);
    }

    private void getAdapater() {
        postsAdapter = new PostsAdapter(postsModelList, new PostsItemClickListener() {
            @Override
            public void OnitemClick(int position, int selectedcount) {
                selectedcounts.setText(String.valueOf(selectedcount));
            }
        });
    }

    private void initViews() {
        toolbar=findViewById(R.id.toolbar);
        selectedcounts = findViewById(R.id.tv_count);
        recyclerView=findViewById(R.id.recycler);
        swipeRefreshLayout=findViewById(R.id.swipe);
        progressBar=findViewById(R.id.progressbar);
    }
}
